package com.commerce.server.validator

import com.commerce.server.exception.ValidationException
import com.commerce.server.model.cache.PaymentMethod
import com.commerce.server.model.container.TransactionContainer


fun validatePriceModifier(config: PaymentMethod,
                          priceModifier: Double) {
    val configMin = config.priceModifierMin
    val configMax = config.priceModifierMax
    if (priceModifier.compareTo(configMin) < 0 ||
        priceModifier.compareTo(configMax) > 0) {
        throw ValidationException("Price modifier not in the range")
    }
}

fun validateAdditional(paymentMethod: String,
                       additionalItem: TransactionContainer.AdditionalItem?) {
    when(paymentMethod) {
        "CASH_ON_DELIVERY" ->
            if (additionalItem?.courier != "YAMATO" && additionalItem?.courier != "SAGAWA") {
                throw ValidationException("Unknown courier")
            }
        "VISA",
        "MASTERCARD",
        "AMEX",
        "JCB" -> {
            if (additionalItem?.last4 == null) {
                throw ValidationException("Require last 4 card digits in additional items")
            }
            if (additionalItem.last4 < 1000 || additionalItem.last4 >= 10000) {
                throw ValidationException("Invalid count of card digits: require last 4 digits")
            }
        }
        "BANK_TRANSFER" ->
            if (additionalItem?.bankItem?.accountNumber == null) {
                throw ValidationException("Require bank name and account number in additional items")
            }
        "CHEQUE" ->
            if (additionalItem?.bankItem?.chequeNumber == null) {
                throw ValidationException("Require bank name and cheque number in additional items")
            }
    }
}