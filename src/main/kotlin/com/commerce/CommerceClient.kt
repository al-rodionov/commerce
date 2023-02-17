package com.commerce

import com.commerce.grpc.CommerceGrpc
import com.commerce.grpc.TransactionRequest
import com.commerce.util.print
import io.grpc.ManagedChannelBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.annotation.PostConstruct
import kotlin.random.Random

@Component
@Profile("client")
class CommerceClient {

    private val logger: Logger = LoggerFactory.getLogger(CommerceClient::class.java)

    @Value("\${commerce.server.host}")
    lateinit var serverHost: String
    @Value("\${commerce.server.port}")
    lateinit var serverPort: String

    @PostConstruct
    fun sendRequest() {
        var counter = 100
        while (counter > 0) {
            counter--
            try {
                Thread.sleep(5000)
                sendMessage()
            } catch (e: Exception) {
                logger.error("Exception while sending request: {}", e.message)
            }
        }
    }

    private fun sendMessage() {
        val channel = ManagedChannelBuilder.forAddress(serverHost, serverPort.toInt())
            .usePlaintext()
            .build()
        val stub = CommerceGrpc.newBlockingStub(channel)
        val request = generateRequest()
        logger.info("Send request {}", request.print())
        val response = stub.transaction(request)
        logger.info("Receive response {}", response.print())
    }

    fun generateRequest(): TransactionRequest {
        return TransactionRequest.newBuilder()
            .setCustomerId(1)
            .setPrice(generatePrice())
            .setPriceModifier(generatePriceModifier())
            .setPaymentMethod("CASH")
            .setDateTime("2022-09-01T00:00:00Z")
            .build()
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
