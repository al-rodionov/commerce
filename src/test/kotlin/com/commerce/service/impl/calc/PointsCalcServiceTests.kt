package com.commerce.service.impl.calc

import com.commerce.model.container.TransactionContainer
import com.commerce.service.PointsCalcService
import com.commerce.util.DATE_TIME
import com.commerce.util.generateTransactionContainer
import com.commerce.util.parseDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest(properties = [
    "commerce.server.port: 15003"
])
@ActiveProfiles("server")
class PointsCalcServiceTests @Autowired constructor(
    val calculator: PointsCalcService
) {

    @Test
    fun calculatePoints() {
        val price = calculator.calculate(generateTransactionContainer())
        assertEquals(50.01, price)
    }

    @Test
    fun calculatePointsDigits() {
        val price = calculator.calculate(
            TransactionContainer(
                customerId = 1L,
                price = 333.33,
                priceModifier = 0.9,
                paymentMethod = "CASH",
                dateTime = parseDate(DATE_TIME),
                additionalItem = null,
                0.0, 0.0
            )
        )
        assertEquals(16.67, price)
    }
}