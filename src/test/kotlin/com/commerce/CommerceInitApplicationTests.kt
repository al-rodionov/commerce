package com.commerce

import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CommerceInitApplicationTests {

    @Test
    fun helloWorld() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15002)
            .usePlaintext()
            .build()
        val stub = HelloGrpc.newBlockingStub(channel)
        val response = stub.hello(HelloRequest.newBuilder().setMessage("world").build())
        assert(response.message.equals("Hello world"))
    }
}
