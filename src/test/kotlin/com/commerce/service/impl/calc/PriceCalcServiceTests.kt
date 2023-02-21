package com.commerce.service.impl.calc

import com.commerce.CommerceServer
import com.commerce.model.container.TransactionContainer
import com.commerce.service.PriceCalcService
import com.commerce.util.DATE_TIME
import com.commerce.util.generateTransactionContainer
import com.commerce.util.parseDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("server")
class PriceCalcServiceTests @Autowired constructor(
    val calculator: PriceCalcService,
    @MockBean val commerceServer: CommerceServer
) {

    @Test
    fun calculatePrice() {
        val price = calculator.calculate(generateTransactionContainer())
        assertEquals(950.00, price)
    }

    @Test
    fun calculatePriceDigits() {
        val price = calculator.calculate(
            TransactionContainer(
                customerId = 1L,
                price = 333.18,
                priceModifier = 0.33,
                paymentMethod = "CASH",
                dateTime = parseDate(DATE_TIME),
                additionalItem = null,
                0.0, 0.0
            )
        )
        assertEquals(109.95, price)
    }
}