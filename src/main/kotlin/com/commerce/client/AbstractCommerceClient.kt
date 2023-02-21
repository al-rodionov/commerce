package com.commerce.client

import com.commerce.CommerceClient
import com.commerce.grpc.CommerceGrpc
import com.commerce.grpc.CommerceGrpc.CommerceBlockingStub
import com.commerce.util.addIndentation
import io.grpc.ManagedChannelBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import javax.annotation.PostConstruct

abstract class AbstractCommerceClient<Request: Any, Response: Any>: CommerceClient<Request, Response> {

    private val logger: Logger = LoggerFactory.getLogger(AbstractCommerceClient::class.java)

    @Value("\${commerce.server.host}")
    lateinit var serverHost: String
    @Value("\${commerce.server.port}")
    lateinit var serverPort: String
    @Value("\${commerce.client.request.count}")
    lateinit var requestCount: String
    @Value("\${commerce.client.request.delay.between}")
    lateinit var requestDelay: String

    lateinit var stub: CommerceBlockingStub

    @PostConstruct
    fun startClient() {
        val channel = ManagedChannelBuilder.forAddress(serverHost, serverPort.toInt())
            .usePlaintext()
            .build()
        stub = CommerceGrpc.newBlockingStub(channel)

        var counter = requestCount.toInt()
        while (counter > 0) {
            counter--
            try {
                sendRequest()
            } catch (e: Exception) {
                logger.error("Exception while sending request: {}", e.message)
            }
        }
    }

    private fun sendRequest() {
        Thread.sleep(requestDelay.toLong())

        val request = generateRequest()
        logger.info("Send request {}", addIndentation(request))
        val response = getResponse(request)
        logger.info("Receive response {}", addIndentation(response))
    }

    abstract fun generateRequest(): Request

    abstract fun getResponse(request: Request): Response

}