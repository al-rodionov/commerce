package com.commerce.util

import com.commerce.grpc.AdditionalItem
import com.commerce.grpc.BankItem
import com.commerce.grpc.TransactionRequest
import com.commerce.model.container.TransactionContainer
import com.commerce.mapper.mapDateTime

val DATE_TIME: String = "2022-09-01T00:00:00Z";

fun generateTransaction(): TransactionRequest =
    TransactionRequest.newBuilder()
        .setCustomerId(1)
        .setPrice(100.0)
        .setPriceModifier(0.95)
        .setPaymentMethod("CASH")
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

fun generateTransactionContainer(paymentMethod: String,
                                 priceModifier: Double,
                                 additionalItem: TransactionContainer.AdditionalItem?): TransactionContainer =
    TransactionContainer(
        customerId = 1L,
        price = 1000.00,
        priceModifier = priceModifier,
        paymentMethod = paymentMethod,
        dateTime = mapDateTime(DATE_TIME),
        additionalItem = additionalItem,
        0.0, 0.0
    )

//todo builder
fun generateTransactionContainer(paymentMethod: String): TransactionContainer =
    generateTransactionContainer(paymentMethod, 0.95, null)

fun generateTransactionContainer(priceModifier: Double): TransactionContainer =
    generateTransactionContainer("CASH", priceModifier, null)

fun generateTransactionContainer(): TransactionContainer =
    generateTransactionContainer("CASH")
