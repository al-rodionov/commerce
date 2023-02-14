package com.commerce

import com.commerce.grpc.CommerceGrpc
import com.commerce.grpc.TransactionRequest
import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
@Profile("client")
class CommerceClient {

    @PostConstruct
    fun sendRequest() {
        var counter = 100
        while (counter > 0) {
            counter--
            try {
                Thread.sleep(5000)
                sendMessage()
            } catch (e: Exception) {
                println("Still not ready...")
                println(e.message)
            }
        }
    }

    private fun sendMessage() {
        val channel = ManagedChannelBuilder.forAddress("server", 15002)
            .usePlaintext()
            .build()
        val stub = CommerceGrpc.newBlockingStub(channel)
        val response = stub.transaction(generateTransaction())
        println(response.finalPrice)
        println(response.points)
    }

//    todo utils? also in tests
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
