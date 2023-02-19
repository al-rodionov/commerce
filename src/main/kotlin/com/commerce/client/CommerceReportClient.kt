package com.commerce.client

import com.commerce.grpc.TransactionReportRequest
import com.commerce.grpc.TransactionReportResponse
import com.commerce.util.formatDate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Profile("report-client")
class CommerceReportClient : AbstractCommerceClient<TransactionReportRequest, TransactionReportResponse>() {

    @Value("\${commerce.client.request.report.duration}")
    lateinit var reportDuration: String

    override fun generateRequest(): TransactionReportRequest {
        val dateTime = LocalDateTime.now()
        return TransactionReportRequest.newBuilder()
            .setStartDateTime(formatDate(dateTime.minusSeconds(reportDuration.toLong())))
            .setEndDateTime(formatDate(dateTime))
            .build()

    }

    override fun getResponse(request: TransactionReportRequest): TransactionReportResponse {
        return stub.transactionReport(request)
    }
}
