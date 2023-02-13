package com.commerce

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
            } catch (_: Exception) {
                println("Still not ready...")
            }
        }
    }

    private fun sendMessage() {
        val channel = ManagedChannelBuilder.forAddress("server", 15002)
            .usePlaintext()
            .build()
        val stub = HelloGrpc.newBlockingStub(channel)
        val response = stub.hello(HelloRequest.newBuilder().setMessage("world").build())
        println(response.message)
    }
}
