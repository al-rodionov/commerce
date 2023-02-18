package com.commerce.mapper

import com.commerce.grpc.TransactionReportItem
import com.commerce.grpc.transactionReportItem
import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Payment
import com.commerce.util.formatDate
import java.time.LocalDateTime

fun toPaymentEntity(container: TransactionContainer) = Payment(
    id = null,
    sales = container.sales,
    points = container.points,
    dateTime = LocalDateTime.now()
)

fun toReportItem(payment: Payment) = transactionReportItem {
    sales = payment.sales
    points = payment.points
    dateTime = formatDate(payment.dateTime)
}