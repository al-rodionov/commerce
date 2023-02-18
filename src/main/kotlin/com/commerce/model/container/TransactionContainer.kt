package com.commerce.model.container

import java.time.LocalDateTime


class TransactionContainer(
    val customerId: Long,
    val price: Double,
    val priceModifier: Double,
    val paymentMethod: String,
    val dateTime: LocalDateTime,
    val additionalItem: AdditionalItem?,

    var sales: Double,
    var points: Double,
)

class AdditionalItem(
    val last4: Int?,
    val courier: String?,
    val bankItem: BankItem?
)

class BankItem(
    val bankName: String,
    val accountNumber: String?,
    val chequeNumber: String?
)
