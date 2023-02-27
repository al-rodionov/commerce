package com.commerce.server.mapper

import com.commerce.grpc.PaymentsReportRequest
import com.commerce.server.model.container.PaymentsReportContainer
import com.commerce.util.parseDate

fun toContainer(request: PaymentsReportRequest) = PaymentsReportContainer (
    startDateTime = parseDate(request.startDateTime),
    endDateTime = parseDate(request.endDateTime)
)
