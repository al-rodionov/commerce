package com.commerce.server.service

import com.commerce.server.model.cache.PaymentMethod
import com.commerce.server.model.container.PaymentsReportContainer
import com.commerce.server.model.container.TransactionContainer
import com.commerce.server.model.entity.Payment

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

    fun findReports(container: PaymentsReportContainer): List<Payment>
}