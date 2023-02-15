package com.commerce.calculator

import com.commerce.mapper.mapDateTime
import com.commerce.model.container.TransactionContainer
import com.commerce.service.PointsCalcService
import com.commerce.util.DATE_TIME
import com.commerce.util.generateTransactionContainer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("test")
class PointsCalcServiceTests @Autowired constructor(
    val calculator: PointsCalcService
) {

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