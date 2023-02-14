package com.commerce

import com.commerce.grpc.CommerceGrpc
import com.commerce.grpc.TransactionRequest
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("server")
class CommerceServerTests @Autowired constructor(val com: CommerceServer) {

    @Test
    fun getResponseGrpc() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15002)
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
            .setDateTime("2022-09-01T00:00:00Z")
            .build()
    }
}
