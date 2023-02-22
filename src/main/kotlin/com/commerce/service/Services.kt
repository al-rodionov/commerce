package com.commerce.service

import com.commerce.model.cache.PaymentMethod
import com.commerce.model.container.TransactionContainer
import com.commerce.model.container.TransactionReportContainer
import com.commerce.model.entity.Payment

interface PaymentMethodService {
    fun getPaymentMethod(name: String): PaymentMethod
}

interface ValidatorService {
    fun validate(container: TransactionContainer)
}

interface Calculator {
    fun calculate(container: TransactionContainer): Double
}

interface PointsCalcService : Calculator

interface PriceCalcService : Calculator

interface StoreService {
    fun store(container: TransactionContainer)

    fun findReports(container: TransactionReportContainer): List<Payment>
}