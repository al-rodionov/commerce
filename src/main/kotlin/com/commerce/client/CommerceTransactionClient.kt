package com.commerce.client

import com.commerce.grpc.CommerceGrpc
import com.commerce.grpc.TransactionReportRequest
import com.commerce.grpc.TransactionRequest
import com.commerce.grpc.TransactionResponse
import com.commerce.util.formatDate
import io.grpc.ManagedChannelBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime
import javax.annotation.PostConstruct
import kotlin.random.Random

@Component
@Profile("transaction-client")
class CommerceTransactionClient : AbstractCommerceClient<TransactionRequest, TransactionResponse>() {

    override fun generateRequest(): TransactionRequest {
        return TransactionRequest.newBuilder()
            .setCustomerId(1)
            .setPrice(generatePrice())
            .setPriceModifier(generatePriceModifier())
            .setPaymentMethod("CASH")
            .setDateTime(formatDate(LocalDateTime.now()))
            .build()
    }

    override fun getResponse(stub: CommerceGrpc.CommerceBlockingStub,
                             request: TransactionRequest): TransactionResponse {
        return stub.transaction(request)
    }

    private fun generatePrice(): Double {
        return generateDouble(100.0, 3000.0)
    }

    private fun generatePriceModifier(): Double {
        return generateDouble(0.85, 1.05)
    }

    private fun generateDouble(from: Double,
                               to: Double): Double {
        val random = Random.nextDouble(from, to)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        return df.format(random).toDouble()
    }
}
