package com.commerce

import com.commerce.exception.ValidationException
import com.commerce.grpc.*
import com.commerce.mapper.mapDateTime
import com.commerce.mapper.toContainer
import com.commerce.mapper.toReportItem
import com.commerce.model.container.TransactionContainer
import com.commerce.service.PointsCalcService
import com.commerce.service.PriceCalcService
import com.commerce.service.TransactionStoreService
import com.commerce.service.ValidatorService
import com.commerce.util.print
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.Status.INVALID_ARGUMENT
import io.grpc.StatusException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import javax.annotation.PostConstruct

@Component
@Profile("server")
class CommerceServer @Autowired constructor(
    validatorService: ValidatorService,
    storeService: TransactionStoreService,
    priceCalcService: PriceCalcService,
    pointsCalcService: PointsCalcService,
){

    private val logger: Logger = LoggerFactory.getLogger(CommerceServer::class.java)
    @Value("\${commerce.server.available.time}")
    lateinit var serverAvailableTime: String

    val server: Server = ServerBuilder
        .forPort(15002)
        .addService(TransactionGrpcService(validatorService,
            storeService, priceCalcService, pointsCalcService))
        .build()

    @PostConstruct
    fun start() {
        planningShutdown()

        server.start()
    }

    fun planningShutdown() {
        Runtime.getRuntime().addShutdownHook(Thread {
            server.shutdown()
            server.awaitTermination()
        })

        val executor = Executors.newSingleThreadScheduledExecutor()
        executor.schedule({
            server.shutdown()
            logger.info("Turn down the server")
            executor.shutdown()
        }, serverAvailableTime.toLong(), TimeUnit.SECONDS)
    }

    private class TransactionGrpcService @Autowired constructor(
        val validatorService: ValidatorService,
        val storeService: TransactionStoreService,
        val priceCalcService: PriceCalcService,
        val pointsCalcService: PointsCalcService
    ) : CommerceGrpcKt.CommerceCoroutineImplBase() {

        private val logger: Logger = LoggerFactory.getLogger(TransactionGrpcService::class.java)

        override suspend fun transaction(request: TransactionRequest) : TransactionResponse {
            try {
                logger.info("Receive request {}", request.print())
                val container = toContainer(request)
                validatorService.validate(container)

                logger.info("Successfully validate, calculating price and points")
                val finalPrice = priceCalcService.calculate(container)
                val points = pointsCalcService.calculate(container)

                container.sales = finalPrice
                container.points = points

                logger.info("Store transaction and payment")
                storeService.store(container)

                val response = createResponse(container)
                logger.info("Send response {}", response.print())
                return response
            } catch (e: ValidationException) {
                throw StatusException(INVALID_ARGUMENT.withDescription(e.message))
            }
        }

        private fun createResponse(container: TransactionContainer) = transactionResponse {
            finalPrice = container.sales
            points = container.points
        }

        override suspend fun transactionReport(request: TransactionReportRequest): TransactionReportResponse {
            logger.info("Receive request {}", request.print())
            val payments: List<TransactionReportItem>  =
                storeService.findAllBetweenDates(
                    mapDateTime(request.startDateTime),
                    mapDateTime(request.endDateTime)
                ).stream()
                    .map { toReportItem(it) }
                    .collect(Collectors.toList())
            val response = TransactionReportResponse.newBuilder()
                .addAllSales(payments)
                .build()
            logger.info("Send response {}", response.print())
            return response
        }
    }
}
