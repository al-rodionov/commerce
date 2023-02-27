package com.commerce.server.mapper

import com.commerce.server.model.container.TransactionContainer
import com.commerce.server.model.entity.Payment
import com.commerce.util.formatDate
import java.time.LocalDateTime

fun toPaymentEntity(container: TransactionContainer) = Payment(
    id = null,
    sales = container.sales,
    points = container.points,
    dateTime = LocalDateTime.now(),
    null
)

fun toGrpcPayment(payment: Payment) = com.commerce.grpc.payment {
    sales = payment.sales
    points = payment.points
    dateTime = formatDate(payment.dateTime)
}