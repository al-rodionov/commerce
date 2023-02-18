package com.commerce

import com.commerce.grpc.CommerceGrpc
import com.commerce.grpc.TransactionRequest
import com.commerce.util.DATE_TIME
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("test", "server")
class CommerceServerImplTests {

    @Value("\${commerce.server.port}")
    lateinit var serverPort: String
    @Value("\${commerce.server.host}")
    lateinit var serverHost: String

    @Test
    fun getResponseGrpc() {
        val channel = ManagedChannelBuilder.forAddress(serverHost, serverPort.toInt())
            .usePlaintext()
            .build()
        val stub = CommerceGrpc.newBlockingStub(channel)
        val response = stub.transaction(generateTransaction())
        assert(response.finalPrice.compareTo(95.0) == 0)
        assert(response.points.compareTo(5.01) == 0)
    }

    fun generateTransaction(): TransactionRequest {
        return TransactionRequest.newBuilder()
            .setCustomerId(1)
            .setPrice(100.0)
            .setPriceModifier(0.95)
            .setPaymentMethod("CASH")
            .setDateTime(DATE_TIME)
            .build()
    }
}