package com.commerce.util

import com.commerce.grpc.AdditionalItem
import com.commerce.grpc.BankItem
import com.commerce.grpc.TransactionRequest
import com.commerce.model.container.TransactionContainer
import com.commerce.model.mapper.mapDateTime

val DATE_TIME: String = "2022-09-01T00:00:00Z";

fun generateTransaction(): TransactionRequest =
    TransactionRequest.newBuilder()
        .setCustomerId(1)
        .setPrice(100.0)
        .setPriceModifier(0.95)
        .setDateTime(DATE_TIME)
        .setAdditionalItem(generateAdditionalItem())
        .build()

private fun generateAdditionalItem(): AdditionalItem =
    AdditionalItem.newBuilder()
        .setLast4(1212)
        .setCourier("YAMATO")
        .setBankItem(generateBankItem())
        .build()

private fun generateBankItem(): BankItem =
    BankItem.newBuilder()
        .setBankName("Bank Name 1")
        .setAccountNumber("1234567")
        .setChequeNumber("7654321")
        .build()

fun generateTransactionContainer(): TransactionContainer = TransactionContainer(
    customerId = 1L,
    price = 1000.00,
    priceModifier = 0.95,
    dateTime = mapDateTime(DATE_TIME),
    additionalItem = TransactionContainer.AdditionalItem(
        last4 = 2345,null,null
    )

)
