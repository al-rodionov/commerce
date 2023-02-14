package com.commerce.mapper

import com.commerce.grpc.AdditionalItem
import com.commerce.grpc.BankItem
import com.commerce.grpc.TransactionRequest
import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun toContainer(request: TransactionRequest) = TransactionContainer(
    customerId = request.customerId,
    price = request.price,
    priceModifier = request.priceModifier,
    paymentMethod = request.paymentMethod,
    dateTime = mapDateTime(request.dateTime),
    additionalItem = mapAdditionalItem(request.additionalItem)
)

fun toEntity(container: TransactionContainer) = Transaction(
    id = null,
    customerId = container.customerId,
    price = container.price,
    priceModifier = container.priceModifier,
    paymentMethod = container.paymentMethod,
    dateTime = container.dateTime,
    additionalItem = container.additionalItem.toString()
)

fun mapDateTime(date: String): LocalDateTime {
    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd\'T\'HH:mm:ss\'Z\'")
    return LocalDateTime.parse(date, pattern)
}

private fun mapAdditionalItem(additionalItem: AdditionalItem): TransactionContainer.AdditionalItem =
    TransactionContainer.AdditionalItem(
        last4 = additionalItem.last4,
        courier = additionalItem.courier,
        bankItem = mapBankItem(additionalItem.bankItem)
    )

private fun mapBankItem(bankItem: BankItem): TransactionContainer.BankItem =
    TransactionContainer.BankItem(
        bankName = bankItem.bankName,
        accountNumber = bankItem.accountNumber,
        chequeNumber = bankItem.chequeNumber
    )
