package com.commerce.service.impl.calc

import com.commerce.CommerceServer
import com.commerce.service.PointsCalcService
import com.commerce.util.builder.TransactionContainerBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("server")
class PointsCalcServiceTests @Autowired constructor(
    val calculator: PointsCalcService,
    @MockBean val commerceServer: CommerceServer
) {

    @Test
    fun calculatePoints() {
        val price = calculator.calculate(TransactionContainerBuilder().build())
        assertEquals(50.01, price)
    }

    @Test
    fun calculatePointsDigits() {
        val price = calculator.calculate(
            TransactionContainerBuilder()
                .withPrice(333.33)
                .withPriceModifier(0.9)
                .build()
        )
        assertEquals(16.67, price)
    }
}