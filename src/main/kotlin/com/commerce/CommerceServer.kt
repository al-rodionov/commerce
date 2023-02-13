package com.commerce

import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class CommerceServer {
        val server: Server = ServerBuilder
            .forPort(15002)
            .addService(HelloWorldService())
            .build()

        @PostConstruct
        fun start() {
            server.start()
        }

        private class HelloWorldService : HelloGrpcKt.HelloCoroutineImplBase() {
            override suspend fun hello(request: HelloRequest) = helloResponse {
                message = "Hello ${request.message}"
            }
        }
}
