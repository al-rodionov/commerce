package com.commerce.service.impl

import com.commerce.CommerceServer
import com.commerce.exception.ValidationException
import com.commerce.model.container.TransactionContainer
import com.commerce.service.ValidatorService
import com.commerce.util.TransactionContainerBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("server")
class ValidatorServiceTests @Autowired constructor(
    val validatorService: ValidatorService,
    @MockBean val commerceServer: CommerceServer
){

    @Test
    fun validRequest() {
        validatorService.validate(
            TransactionContainerBuilder().build()
        )

        validatorService.validate(
            TransactionContainerBuilder()
                .withAdditionalItem(TransactionContainer.AdditionalItem(1111, null, null))
                .withPaymentMethod("VISA")
                .withPriceModifier(0.95)
                .build()
        )
    }

    @Test
    fun invalidRequestBasic() {
        assertThrow(
            TransactionContainerBuilder()
                .withPaymentMethod("INVALID")
                .build(),
            "Invalid payment method"
        )

        assertThrow(
            TransactionContainerBuilder()
                .withPriceModifier(0.5)
                .build(),
            "Price modifier not in the range"
        )

        assertThrow(
            TransactionContainerBuilder()
                .withPriceModifier(2.5)
                .build(),
            "Price modifier not in the range"
        )
    }

    @Test
    fun invalidRequestAdditional() {
        assertThrow(
            TransactionContainerBuilder()
                .withPaymentMethod("CASH_ON_DELIVERY")
                .withPriceModifier(1.0)
                .build(),
            "Unknown courier"
        )
        assertThrow(
            TransactionContainerBuilder()
                .withPaymentMethod("VISA")
                .build(),
            "Require last 4 card digits in additional items"
        )
        assertThrow(
            TransactionContainerBuilder()
                .withPaymentMethod("BANK_TRANSFER")
                .withPriceModifier(1.0)
                .build(),
            "Require bank name and account number in additional items"
        )
        assertThrow(
            TransactionContainerBuilder()
                .withPaymentMethod("CHEQUE")
                .withPriceModifier(1.0)
                .build(),
            "Require bank name and cheque number in additional items"
        )
    }

    private fun assertThrow(container: TransactionContainer,
                            errorMsg: String) {
        val ex = assertThrows<ValidationException> {
            validatorService.validate(container)
        }
        assertEquals(errorMsg, ex.message)
    }
}
