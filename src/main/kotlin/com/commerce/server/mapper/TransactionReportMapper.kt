package com.commerce.server.mapper

import com.commerce.grpc.TransactionReportRequest
import com.commerce.server.model.container.TransactionReportContainer
import com.commerce.util.parseDate

fun toContainer(request: TransactionReportRequest) = TransactionReportContainer (
    startDateTime = parseDate(request.startDateTime),
    endDateTime = parseDate(request.endDateTime)
)
