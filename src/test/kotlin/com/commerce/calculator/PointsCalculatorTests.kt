package com.commerce.calculator

import com.commerce.model.container.TransactionContainer
import com.commerce.model.mapper.mapDateTime
import com.commerce.service.calculator.PointsCalculator
import com.commerce.util.DATE_TIME
import com.commerce.util.generateTransactionContainer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("server")
class PointsCalculatorTests @Autowired constructor(
    val calculator: PointsCalculator
){

    @Test
    fun calculatePoints() {
        val price = calculator.calculate(generateTransactionContainer())
        assert(price.compareTo(50.01) == 0)
    }

    @Test
    fun calculatePointsDigits() {
        val price = calculator.calculate(
            TransactionContainer(
                customerId = 1L,
                price = 333.33,
                priceModifier = 0.9,
                paymentMethod = "CASH",
                dateTime = mapDateTime(DATE_TIME),
                additionalItem = null
            )
        )
        assert(price.compareTo(16.67) == 0)
    }
}