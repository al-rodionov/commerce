package com.commerce.mapper

import com.commerce.grpc.TransactionReportItem
import com.commerce.grpc.transactionReportItem
import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Payment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun toPaymentEntity(container: TransactionContainer): Payment = Payment(
    id = null,
    sales = container.sales,
    points = container.points,
    dateTime = LocalDateTime.now()
)

fun toReportItem(payment: Payment): TransactionReportItem = transactionReportItem {
    sales = payment.sales
    points = payment.points

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\'T\'HH:mm:ss\'Z\'")
    dateTime = payment.dateTime.format(formatter)
}