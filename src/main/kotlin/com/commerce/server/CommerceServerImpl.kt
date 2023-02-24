package com.commerce.server

import com.commerce.CommerceServer
import com.commerce.grpc.*
import com.commerce.server.service.*
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import javax.annotation.PostConstruct

@Component
@Profile("server")
class CommerceServerImpl @Autowired constructor(
    val transactionGrpcService: TransactionGrpcService
) : CommerceServer {

    @Value("\${commerce.server.port}")
    lateinit var serverPort: String

    lateinit var server: Server

    @PostConstruct
    fun start() {
        server = ServerBuilder
            .forPort(serverPort.toInt())
            .addService(transactionGrpcService)
            .build()

        Runtime.getRuntime().addShutdownHook(Thread {
            server.shutdown()
            server.awaitTermination()
        })

        server.start()

        Executors.newSingleThreadExecutor().submit({
            server.awaitTermination()
        })
    }
}
