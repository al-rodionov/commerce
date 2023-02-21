package com.commerce.service

import com.commerce.model.cache.PaymentConfig
import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Payment
import java.time.LocalDateTime

interface PaymentConfigService {
    fun getPaymentConfig(paymentMethod: String): PaymentConfig
}

interface ValidatorService {
    fun validate(container: TransactionContainer)
}

interface Calculator {
    fun calculate(container: TransactionContainer): Double
}

interface PointsCalcService : Calculator

interface PriceCalcService : Calculator

interface TransactionStoreService {
    fun store(container: TransactionContainer)

    fun findAllBetweenDates(beginDate: LocalDateTime,
                            endDate: LocalDateTime): List<Payment>
}