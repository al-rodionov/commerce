package com.commerce.server

import com.commerce.CommerceServer
import com.commerce.exception.ValidationException
import com.commerce.grpc.*
import com.commerce.mapper.toContainer
import com.commerce.mapper.toReportItem
import com.commerce.model.container.TransactionContainer
import com.commerce.model.container.TransactionReportContainer
import com.commerce.service.*
import com.commerce.util.addIndentation
import com.commerce.util.parseDate
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.Status.INVALID_ARGUMENT
import io.grpc.StatusException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import javax.annotation.PostConstruct

@Component
@Profile("server")
class CommerceServerImpl @Autowired constructor(
    val transactionGrpcService: TransactionGrpcService
) : CommerceServer {

    private val logger: Logger = LoggerFactory.getLogger(CommerceServerImpl::class.java)
    @Value("\${commerce.server.available.time}")
    lateinit var serverAvailableTime: String
    @Value("\${commerce.server.port}")
    lateinit var serverPort: String

    lateinit var server: Server

    @PostConstruct
    fun start() {
        server = ServerBuilder
            .forPort(serverPort.toInt())
            .addService(transactionGrpcService)
            .build()

        planningShutdown()

        server.start()
    }

    fun planningShutdown() {
        Runtime.getRuntime().addShutdownHook(Thread {
            server.shutdown()
            server.awaitTermination()
        })

        val executor = Executors.newSingleThreadScheduledExecutor()
        executor.schedule({
            server.shutdown()
            logger.info("Turn down the server")
            executor.shutdown()
        }, serverAvailableTime.toLong(), TimeUnit.SECONDS)
    }

}
