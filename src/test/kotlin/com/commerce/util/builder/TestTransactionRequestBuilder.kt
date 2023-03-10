package com.commerce.util.builder

import com.commerce.grpc.AdditionalItem
import com.commerce.grpc.TransactionRequest
import com.commerce.util.DATE_TIME


class TestTransactionRequestBuilder: AbstractTestTransactionBuilder<TransactionRequest>() {
    private var dateTime: String = DATE_TIME
    private var additionalItem: AdditionalItem = AdditionalItem.getDefaultInstance()

    fun withDateTime(dateTime: String): TestTransactionRequestBuilder {
        this.dateTime = dateTime
        return this
    }

    fun withAdditionalItem(additionalItem: AdditionalItem): TestTransactionRequestBuilder {
        this.additionalItem = additionalItem
        return this
    }

    override fun build(): TransactionRequest {
        return TransactionRequest.newBuilder()
            .setCustomerId(customerId)
            .setPrice(price)
            .setPriceModifier(priceModifier)
            .setPaymentMethod(paymentMethod)
            .setDateTime(dateTime)
            .setAdditionalItem(additionalItem)
            .build()
    }
}