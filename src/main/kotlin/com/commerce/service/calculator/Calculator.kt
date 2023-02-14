package com.commerce.service.calculator

import com.commerce.model.container.TransactionContainer

interface Calculator {
    fun calculate(container: TransactionContainer): Double
}

interface PointsCalculator : Calculator

interface PriceCalculator : Calculator