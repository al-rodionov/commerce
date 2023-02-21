package com.commerce

import com.commerce.grpc.CommerceGrpc
import com.commerce.grpc.TransactionReportRequest
import com.commerce.util.formatDate
import com.commerce.util.print
import io.grpc.ManagedChannelBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Component
@Profile("report-client")
class CommerceReportClient {

    private val logger: Logger = LoggerFactory.getLogger(CommerceReportClient::class.java)

    @Value("\${commerce.server.host}")
    lateinit var serverHost: String
    @Value("\${commerce.server.port}")
    lateinit var serverPort: String
    @Value("\${commerce.client.request.count}")
    lateinit var requestCount: String
    @Value("\${commerce.client.request.delay.between}")
    lateinit var requestDelay: String
    @Value("\${commerce.client.request.report.duration}")
    lateinit var reportDuration: String

    @PostConstruct
    fun sendRequest() {
        var counter = requestCount.toInt()
        while (counter > 0) {
            counter--
            try {
                Thread.sleep(requestDelay.toLong())
                sendMessage()
            } catch (e: Exception) {
                logger.error("Exception while sending request: {}", e.message)
            }
        }
    }

    private fun sendMessage() {
        val channel = ManagedChannelBuilder.forAddress(serverHost, serverPort.toInt())
            .usePlaintext()
            .build()
        val stub = CommerceGrpc.newBlockingStub(channel)
        val request = generateRequest()
        logger.info("Send request {}", request.print())
        val response = stub.transactionReport(request)
        logger.info("Receive response {}", response.print())
    }

    fun generateRequest(): TransactionReportRequest {
        return TransactionReportRequest.newBuilder()
            .setStartDateTime(formatDate(LocalDateTime.now().minusSeconds(reportDuration.toLong())))
            .setEndDateTime(formatDate(LocalDateTime.now()))
            .build()
    }
}
