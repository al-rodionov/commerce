package com.commerce.server

import com.commerce.grpc.CommerceGrpc
import com.commerce.util.builder.TransactionRequestBuilder
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
import org.springframework.test.context.ActiveProfiles


@SpringBootTest(properties = [
    "commerce.server.port: 15015"
])
@ActiveProfiles("server")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class CommerceServerImplTests {

    companion object {
        private const val SERVER_HOST: String = "localhost"
    }
    @Value("\${commerce.server.port}")
    lateinit var serverPort: String

    @Test
    fun getResponseGrpc() {
        val channel = ManagedChannelBuilder.forAddress(Companion.SERVER_HOST, serverPort.toInt())
            .usePlaintext()
            .build()
        val stub = CommerceGrpc.newBlockingStub(channel)
        val response = stub.transaction(
            TransactionRequestBuilder()
                .withPrice(100.0)
                .build()
        )

        Assertions.assertEquals(95.0, response.finalPrice)
        Assertions.assertEquals(5.01, response.points)
    }

}
