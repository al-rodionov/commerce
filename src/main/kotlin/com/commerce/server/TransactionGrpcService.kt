package com.commerce.server

import com.commerce.grpc.*
import com.commerce.server.exception.ValidationException
import com.commerce.server.mapper.toContainer
import com.commerce.server.mapper.toGrpcPayment
import com.commerce.server.model.container.TransactionContainer
import com.commerce.server.service.*
import com.commerce.util.addIndentation
import io.grpc.Status
import io.grpc.StatusException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.stream.Collectors


@Service
@Profile("server")
class TransactionGrpcService @Autowired constructor(
    val validatorService: ValidatorService,
    val priceCalcService: PriceCalcService,
    val pointsCalcService: PointsCalcService,
    val storeService: StoreService
) : CommerceGrpcKt.CommerceCoroutineImplBase() {

    private val logger: Logger = LoggerFactory.getLogger(TransactionGrpcService::class.java)

    override suspend fun transaction(request: TransactionRequest) : TransactionResponse {
        try {
            logger.info("Receive request {}", addIndentation(request))
            val response = operateTransaction(request)
            logger.info("Send response {}", addIndentation(response))
            return response
        } catch (e: ValidationException) {
            val message = e.message
            logger.error("Exception while executing request: {}", message)
            throw StatusException(Status.INVALID_ARGUMENT.withDescription(message))
        }
    }

    private fun operateTransaction(request: TransactionRequest): TransactionResponse {
        val container = toContainer(request)
        validatorService.validate(container)

        logger.info("Successfully validate, calculating price and points")
        calcPriceAndPoints(container)

        logger.info("Store transaction and payment")
        storeService.store(container)

        return createResponse(container)
    }

    private fun calcPriceAndPoints(container: TransactionContainer) {
        val finalPrice = priceCalcService.calculate(container)
        val points = pointsCalcService.calculate(container)

        container.sales = finalPrice
        container.points = points
    }

    fun createResponse(container: TransactionContainer) = transactionResponse {
        finalPrice = container.sales
        points = container.points
    }

    override suspend fun paymentsReport(request: PaymentsReportRequest): PaymentsReportResponse {
        logger.info("Receive request {}", addIndentation(request))

        val container = toContainer(request)
        val payments: List<Payment>  =
            storeService.findReports(container).stream()
                .map { toGrpcPayment(it) }
                .collect(Collectors.toList())

        val response = PaymentsReportResponse.newBuilder()
            .addAllPayments(payments)
            .build()

        logger.info("Send response {}", addIndentation(response))
        return response
    }
}