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
class CommerceInitApplicationTests @Autowired constructor(val com: CommerceServer) {

    @Test
    fun helloWorld() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15002)
            .usePlaintext()
            .build()
        val stub = CommerceGrpc.newBlockingStub(channel)
        val response = stub.transaction(generateTransaction())
        assert(response.finalPrice.equals(1000.0))
        assert(response.points.equals(20.0))
    }

    fun generateTransaction(): TransactionRequest {
        return TransactionRequest.newBuilder()
            .setCustomerId(1)
            .setPrice(100.0)
            .setPriceModifier(0.95)
            .setDateTime("2022-09-01T00:00:00Z")
            .build()
    }
}
