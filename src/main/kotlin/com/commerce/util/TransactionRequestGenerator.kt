package com.commerce.util

import com.commerce.grpc.AdditionalItem
import com.commerce.grpc.BankItem
import com.commerce.grpc.TransactionRequest
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime
import kotlin.random.Random


private val PAYMENT_METHODS = listOf(
    "CASH", "CASH_ON_DELIVERY", "VISA", "MASTERCARD", "AMEX", "JCB",
    "LINE_PAY", "PAYPAY", "POINTS", "GRAB_PAY", "BANK_TRANSFER", "CHEQUE"
)
private val PAYMENT_METHOD_MAX_INDEX = PAYMENT_METHODS.size - 1

private val emptyAdditionalItem = AdditionalItem.newBuilder().build()
private val fullAdditionalItem = AdditionalItem.newBuilder()
    .setLast4(1515)
    .setCourier("YAMATO")
    .setBankItem(
        BankItem.newBuilder()
            .setBankName("Bank Name 1")
            .setAccountNumber("1234567")
            .setChequeNumber("7654321")
    ).build()

fun generateTransactionRequest(): TransactionRequest {
    return TransactionRequest.newBuilder()
        .setCustomerId(generateCustomerId())
        .setPrice(generatePrice())
        .setPriceModifier(generatePriceModifier())
        .setPaymentMethod(generatePaymentType())
        .setDateTime(formatDate(LocalDateTime.now()))
        .setAdditionalItem(generateAdditionalItem())
        .build()
}

private fun generateCustomerId(): Long {
    return Random.nextLong(0, 50000)
}

private fun generatePrice(): Double {
    return generateDouble(100.0, 3000.0)
}

private fun generatePriceModifier(): Double {
    return generateDouble(0.93, 1.01)
}

private fun generateDouble(from: Double,
                           to: Double): Double {
    val random = Random.nextDouble(from, to)
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN
    return df.format(random).toDouble()
}

private fun generatePaymentType(): String {
    return PAYMENT_METHODS[(0..PAYMENT_METHOD_MAX_INDEX).random()]
}

private fun generateAdditionalItem(): AdditionalItem {
    if (Random.nextBoolean()) {
        return emptyAdditionalItem
    }
    return fullAdditionalItem
}