package com.commerce.service.impl.calc

import com.commerce.CommerceServer
import com.commerce.service.PriceCalcService
import com.commerce.util.TransactionContainerBuilder
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
        val price = calculator.calculate(TransactionContainerBuilder().build())
        assertEquals(950.00, price)
    }

    @Test
    fun calculatePriceDigits() {
        val container = TransactionContainerBuilder()
            .withPrice(333.18)
            .withPriceModifier(0.33)
            .build()
        val price = calculator.calculate(
            container
        )
        assertEquals(109.95, price)
    }
}