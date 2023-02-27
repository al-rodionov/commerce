package com.commerce.client

import com.commerce.grpc.PaymentsReportRequest
import com.commerce.grpc.PaymentsReportResponse
import com.commerce.util.formatDate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Profile("report-client")
class CommerceReportClient : AbstractCommerceClient<PaymentsReportRequest, PaymentsReportResponse>() {

    @Value("\${commerce.client.request.report.duration}")
    lateinit var reportDuration: String

    override fun generateRequest(): PaymentsReportRequest {
        val dateTime = LocalDateTime.now()
        return PaymentsReportRequest.newBuilder()
            .setStartDateTime(formatDate(dateTime.minusSeconds(reportDuration.toLong())))
            .setEndDateTime(formatDate(dateTime))
            .build()

    }

    override fun getResponse(request: PaymentsReportRequest): PaymentsReportResponse {
        return stub.paymentsReport(request)
    }
}
