package com.commerce.model.container

import java.time.LocalDateTime


class TransactionContainer(
    val customerId: Long,
    val price: Double,
    val priceModifier: Double,
    val paymentMethod: String,
    val dateTime: LocalDateTime,
    val additionalItem: AdditionalItem?
) {
    class AdditionalItem(
        val last4: Int?,
        val courier: String?,
        val bankItem: BankItem?
    ) {
        override fun toString(): String {
            if (last4 != null) {
                return  "last4=" + last4
            } else if (courier != null) {
                return "courier=" + courier
            } else if (bankItem != null) {
                if (bankItem.accountNumber != null) {
                    return "bankName=" + bankItem.bankName + ";accountNumber=" + bankItem.accountNumber
                } else {
                    return "bankName=" + bankItem.bankName + ";chequeNumber=" + bankItem.chequeNumber
                }
            } else {
                return  ""
            }
        }
    }
    class BankItem(
        val bankName: String,
        val accountNumber: String?,
        val chequeNumber: String?
    )
}