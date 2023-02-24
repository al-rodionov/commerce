package com.commerce.server

import com.commerce.grpc.AdditionalItem
import com.commerce.grpc.CommerceGrpc
import com.commerce.grpc.CommerceGrpc.CommerceBlockingStub
import com.commerce.grpc.TransactionRequest
import com.commerce.util.builder.TestTransactionRequestBuilder
import io.grpc.ManagedChannelBuilder
import io.grpc.StatusRuntimeException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS
import org.springframework.test.context.ActiveProfiles
import javax.annotation.PostConstruct


@SpringBootTest(properties = [
    "commerce.server.port: 15015"
])
@ActiveProfiles("server")
@DirtiesContext(classMode = AFTER_CLASS)
class CommerceServerImplTests {

    companion object {
        private const val SERVER_HOST: String = "localhost"
    }
    @Value("\${commerce.server.port}")
    lateinit var serverPort: String
    lateinit var stub: CommerceBlockingStub

    @PostConstruct
    fun createStub() {
        val channel = ManagedChannelBuilder.forAddress(SERVER_HOST, serverPort.toInt())
            .usePlaintext()
            .build()
        this.stub = CommerceGrpc.newBlockingStub(channel)
    }

    @Test
    fun successfulDefaultRequest() {
        val response = stub.transaction(
            TestTransactionRequestBuilder().build()
        )

        assertEquals(950.0, response.finalPrice)
        assertEquals(50.01, response.points)
    }

    @Test
    fun successfulVisaRequest() {
        val additionalItem = AdditionalItem
            .newBuilder()
            .setLast4(7654)
            .build()
        val response = stub.transaction(
            TestTransactionRequestBuilder()
                .withAdditionalItem(additionalItem)
                .withPrice(1350.15)

                .withPaymentMethod("VISA")
                .build()
        )

        assertEquals(1282.65, response.finalPrice)
        assertEquals(40.51, response.points)
    }

    @Test
    fun exceptionVisaRequestLast4() {
        assertThrow(
            TestTransactionRequestBuilder()
                .withPaymentMethod("VISA")
                .build(),
            "INVALID_ARGUMENT: Require last 4 card digits in additional items"
        )
    }

    @Test
    fun exceptionVisaRequestInvalidLast4() {
        val additionalItem = AdditionalItem
            .newBuilder()
            .setLast4(321)
            .build()
        assertThrow(
            TestTransactionRequestBuilder()
                .withAdditionalItem(additionalItem)
                .withPaymentMethod("VISA")
                .build(),
            "INVALID_ARGUMENT: Invalid count of card digits: require last 4 digits"
        )
    }

    private fun assertThrow(request: TransactionRequest,
                            errorMsg: String) {
        val ex = assertThrows<StatusRuntimeException> {
            stub.transaction(request)
        }
        assertEquals(errorMsg, ex.message)
    }

}
