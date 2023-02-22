package com.commerce.mapper

import com.commerce.grpc.AdditionalItem
import com.commerce.grpc.BankItem
import com.commerce.grpc.TransactionRequest
import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Transaction
import com.commerce.util.parseDate

fun toContainer(request: TransactionRequest) = TransactionContainer(
    customerId = request.customerId,
    price = request.price,
    priceModifier = request.priceModifier,
    paymentMethod = request.paymentMethod,
    dateTime = parseDate(request.dateTime),
    additionalItem = mapAdditionalItem(request.additionalItem),
    0.0, 0.0
)

fun toTransactionEntity(container: TransactionContainer) = Transaction(
    id = null,
    customerId = container.customerId,
    price = container.price,
    priceModifier = container.priceModifier,
    paymentMethod = container.paymentMethod,
    dateTime = container.dateTime,
    additionalItem = additionalItemDbDescription(container.additionalItem),
    null
)

private fun mapAdditionalItem(additionalItem: AdditionalItem): TransactionContainer.AdditionalItem? {
    if(AdditionalItem.getDefaultInstance().equals(additionalItem)) {
        return null
    }
    return TransactionContainer.AdditionalItem(
        last4 = additionalItem.last4,
        courier = additionalItem.courier,
        bankItem = mapBankItem(additionalItem.bankItem)
    )
}

private fun mapBankItem(bankItem: BankItem): TransactionContainer.BankItem? {
    if(BankItem.getDefaultInstance().equals(bankItem)) {
        return null
    }
    return TransactionContainer.BankItem(
        bankName = bankItem.bankName,
        accountNumber = bankItem.accountNumber,
        chequeNumber = bankItem.chequeNumber
    )
}

private const val EMPTY_STRING = ""

private fun additionalItemDbDescription(additionalItem: TransactionContainer.AdditionalItem?): String {
    if (additionalItem == null) {
        return EMPTY_STRING
    }
    if (additionalItem.last4 != null) {
        return  "last4=${additionalItem.last4}"
    }
    if (additionalItem.courier != null) {
        return "courier=${additionalItem.courier}"
    }
    val bankItem = additionalItem.bankItem
    if (bankItem != null) {
        if (bankItem.accountNumber != null) {
            return "bankName=${bankItem.bankName};accountNumber=${bankItem.accountNumber}"
        }
        return "bankName=${bankItem.bankName};chequeNumber=${bankItem.chequeNumber}"
    }
    return  EMPTY_STRING
}
