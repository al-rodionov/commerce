package com.commerce.util

import kotlin.random.Random


const val DEFAULT_PAYMENT_METHOD = "CASH"
const val DATE_TIME: String = "2022-09-01T00:00:00Z";

abstract class AbstractTransactionBuilder<T> {
    protected var customerId: Long = Random.nextLong(1000L)
    protected var price: Double = 1000.0
    protected var priceModifier: Double = 0.95
    protected var paymentMethod: String = DEFAULT_PAYMENT_METHOD

    abstract fun build(): T

    fun withCustomerId(customerId: Long): AbstractTransactionBuilder<T> {
        this.customerId = customerId
        return this
    }

    fun withPrice(price: Double): AbstractTransactionBuilder<T> {
        this.price = price
        return this
    }

    fun withPriceModifier(priceModifier: Double): AbstractTransactionBuilder<T> {
        this.priceModifier = priceModifier
        return this
    }

    fun withPaymentMethod(paymentMethod: String): AbstractTransactionBuilder<T> {
        this.paymentMethod = paymentMethod
        return this
    }
}
