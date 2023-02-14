package com.commerce.validator

import com.commerce.exception.ValidationException
import com.commerce.model.cache.PaymentConfig
import com.commerce.model.container.TransactionContainer


fun validatePaymentMethod(config: PaymentConfig?) {
    println("valid")
    if (config == null) {
        throw ValidationException("Invalid payment method")
    }
}

fun validatePriceModifier(config: PaymentConfig?,
                          priceModifier: Double) {
    if (config?.priceModifierMin?.compareTo(priceModifier)!! > 0 ||
        config?.priceModifierMax?.compareTo(priceModifier)!! < 0) {
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
        "APEX",
        "JCB" ->
            if (additionalItem?.last4 == null) {
                throw ValidationException("Require last 4 card digits in additional items")
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