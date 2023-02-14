package com.commerce

import com.commerce.grpc.*
import com.commerce.model.mapper.toContainer
import com.commerce.service.TransactionStoreService
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Component
@Profile("server")
class CommerceServer @Autowired constructor(
    storeService: TransactionStoreService
){

    @Value("\${commerce.server.available.time}")
    lateinit var serverAvailableTime: String

    val server: Server = ServerBuilder
        .forPort(15002)
        .addService(TransactionGrpcService(storeService))
        .build()

    @PostConstruct
    fun start() {
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
            println("Turn down the server")
            executor.shutdown()
        }, serverAvailableTime.toLong(), TimeUnit.SECONDS)
    }

    private class TransactionGrpcService(
        @Autowired
        val storeService: TransactionStoreService
    ) : CommerceGrpcKt.CommerceCoroutineImplBase() {

        override suspend fun transaction(request: TransactionRequest) = transactionResponse {
            var container = toContainer(request)

            storeService.store(container)

//            mapFromGrpc-contaainer
//            validate - need list1
//            store
//            calculate response - need list1

            finalPrice = 1000.0
            points = 20.0
        }

        override suspend fun transactionReport(request: TransactionReportRequest): TransactionReportResponse {
            return super.transactionReport(request)
        }
    }
}
