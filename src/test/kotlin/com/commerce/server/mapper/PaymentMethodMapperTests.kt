package com.commerce.server.mapper

import com.commerce.server.model.entity.PaymentMethod
import com.commerce.util.DEFAULT_PAYMENT_METHOD
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PaymentMethodMapperTests {

    @Test
    fun toCache() {
        val paymentMethod = PaymentMethod(
            null,
            DEFAULT_PAYMENT_METHOD,
            0.97,
            1.14,
            0.02
        )
        val container = toCache(paymentMethod)

        assertEquals(DEFAULT_PAYMENT_METHOD, container.name)
        assertEquals(0.97, container.priceModifierMin)
        assertEquals(1.14, container.priceModifierMax)
        assertEquals(0.02, container.pointsModifier)
    }
}