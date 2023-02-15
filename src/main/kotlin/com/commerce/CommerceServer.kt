package com.commerce

import com.commerce.exception.ValidationException
import com.commerce.grpc.*
import com.commerce.mapper.mapDateTime
import com.commerce.mapper.toContainer
import com.commerce.mapper.toReportItem
import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Payment
import com.commerce.service.PointsCalcService
import com.commerce.service.PriceCalcService
import com.commerce.service.TransactionStoreService
import com.commerce.service.ValidatorService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.Status.INVALID_ARGUMENT
import io.grpc.StatusException
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
            println("Turn down the server")
            executor.shutdown()
        }, serverAvailableTime.toLong(), TimeUnit.SECONDS)
    }

    private class TransactionGrpcService @Autowired constructor(
        val validatorService: ValidatorService,
        val storeService: TransactionStoreService,
        val priceCalcService: PriceCalcService,
        val pointsCalcService: PointsCalcService
    ) : CommerceGrpcKt.CommerceCoroutineImplBase() {

        override suspend fun transaction(request: TransactionRequest) : TransactionResponse {
            try {
                val container = toContainer(request)
                validatorService.validate(container)

                val finalPrice = priceCalcService.calculate(container)
                val points = pointsCalcService.calculate(container)

                container.sales = finalPrice
                container.points = points

                storeService.store(container)

                return createResponse(container)
            } catch (e: ValidationException) {
                throw StatusException(INVALID_ARGUMENT.withDescription(e.message))
            }
        }

        private fun createResponse(container: TransactionContainer) = transactionResponse {
            finalPrice = container.sales
            points = container.points
        }

        override suspend fun transactionReport(request: TransactionReportRequest): TransactionReportResponse {
            val payments: List<TransactionReportItem>  =
                storeService.findAllBetweenDates(
                    mapDateTime(request.startDateTime),
                    mapDateTime(request.endDateTime)
                ).stream()
                    .map { toReportItem(it) }
                    .collect(Collectors.toList())
            return TransactionReportResponse.newBuilder()
                .addAllSales(payments)
                .build()
        }
    }
}
