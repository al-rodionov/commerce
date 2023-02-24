package com.commerce.util.builder

import com.commerce.util.DEFAULT_PAYMENT_METHOD
import kotlin.random.Random


abstract class AbstractTestTransactionBuilder<T> {
    protected var customerId: Long = Random.nextLong(1000L)
    protected var price: Double = 1000.0
    protected var priceModifier: Double = 0.95
    protected var paymentMethod: String = DEFAULT_PAYMENT_METHOD

    abstract fun build(): T

    fun withCustomerId(customerId: Long): AbstractTestTransactionBuilder<T> {
        this.customerId = customerId
        return this
    }

    fun withPrice(price: Double): AbstractTestTransactionBuilder<T> {
        this.price = price
        return this
    }

    fun withPriceModifier(priceModifier: Double): AbstractTestTransactionBuilder<T> {
        this.priceModifier = priceModifier
        return this
    }

    fun withPaymentMethod(paymentMethod: String): AbstractTestTransactionBuilder<T> {
        this.paymentMethod = paymentMethod
        return this
    }
}
