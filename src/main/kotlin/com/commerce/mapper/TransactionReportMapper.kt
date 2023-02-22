package com.commerce.mapper

import com.commerce.grpc.TransactionReportRequest
import com.commerce.model.container.TransactionReportContainer
import com.commerce.util.parseDate

fun toContainer(request: TransactionReportRequest) = TransactionReportContainer (
    startDateTime = parseDate(request.startDateTime),
    endDateTime = parseDate(request.endDateTime)
)
