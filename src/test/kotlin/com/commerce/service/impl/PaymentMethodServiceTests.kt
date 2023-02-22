package com.commerce.service.impl

import com.commerce.service.PaymentMethodService
import com.commerce.util.DEFAULT_PAYMENT_METHOD
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles

@DataJpaTest()
@ActiveProfiles("server")
@ComponentScan(basePackages = ["com.commerce.service.impl"])
class PaymentMethodServiceTests @Autowired constructor(
    val configService: PaymentMethodService
) {

    @Test
    fun cashConfig() {
        val paymentMethod = configService.getPaymentMethod(DEFAULT_PAYMENT_METHOD)

        assertEquals(0.9, paymentMethod.priceModifierMin)
        assertEquals(1.0, paymentMethod.priceModifierMax)
        assertEquals(0.05, paymentMethod.pointsModifier)
    }

    @Test
    fun visaConfig() {
        val paymentMethod = configService.getPaymentMethod("VISA")

        assertEquals(0.95, paymentMethod.priceModifierMin)
        assertEquals(1.0, paymentMethod.priceModifierMax)
        assertEquals(0.03, paymentMethod.pointsModifier)
    }
}