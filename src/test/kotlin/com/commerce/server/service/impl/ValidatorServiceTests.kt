package com.commerce.server.service.impl

import com.commerce.CommerceServer
import com.commerce.server.exception.ValidationException
import com.commerce.server.model.container.TransactionContainer
import com.commerce.server.service.ValidatorService
import com.commerce.util.builder.TestTransactionContainerBuilder
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

    companion object {
        const val INVALID_PAYMENT_METHOD = "Invalid payment method"
        const val INVALID_PRICE_MODIFIER = "Price modifier not in the range"
        const val UNKNOWN_COURIER = "Unknown courier"
        const val CASH_ON_DELIVERY = "CASH_ON_DELIVERY"
        const val CHEQUE = "CHEQUE"
    }

    @Test
    fun validWithoutAdditional() {
        validatorService.validate(
            TestTransactionContainerBuilder()
                .build()
        )
    }

    @Test
    fun validWithAdditional() {
        validatorService.validate(
            TestTransactionContainerBuilder()
                .withAdditionalItem(TransactionContainer.AdditionalItem(1111, null, null))
                .withPaymentMethod("VISA")
                .withPriceModifier(0.95)
                .build()
        )
    }

    @Test
    fun invalidPaymentMethod() {
        assertThrow(
            TestTransactionContainerBuilder()
                .withPaymentMethod("INVALID")
                .build(),
            INVALID_PAYMENT_METHOD
        )
        assertThrow(
            TestTransactionContainerBuilder()
                .withPaymentMethod("UNKNOWN")
                .build(),
            INVALID_PAYMENT_METHOD
        )
    }

    @Test
    fun invalidPriceModifier() {
        assertThrow(
            TestTransactionContainerBuilder()
                .withPriceModifier(0.5)
                .build(),
            INVALID_PRICE_MODIFIER
        )

        assertThrow(
            TestTransactionContainerBuilder()
                .withPriceModifier(2.5)
                .build(),
            INVALID_PRICE_MODIFIER
        )
    }

    @Test
    fun paySystems() {
        baseTestPaySystem("VISA")
        baseTestPaySystem("MASTERCARD")
        baseTestPaySystem("AMEX")
        baseTestPaySystem("JCB")
    }

    private fun baseTestPaySystem(paySystem: String) {
        validatorService.validate(
            TestTransactionContainerBuilder()
                .withAdditionalItem(TransactionContainer.AdditionalItem(1234, null, null))
                .withPriceModifier(0.98)
                .withPaymentMethod(paySystem)
                .build()
        )

        assertThrow(
            TestTransactionContainerBuilder()
                .withPriceModifier(0.98)
                .withPaymentMethod(paySystem)
                .build(),
            "Require last 4 card digits in additional items"
        )

        assertThrow(
            TestTransactionContainerBuilder()
                .withAdditionalItem(TransactionContainer.AdditionalItem(123, null, null))
                .withPriceModifier(0.98)
                .withPaymentMethod(paySystem)
                .build(),
            "Invalid count of card digits: require last 4 digits"
        )

        assertThrow(
            TestTransactionContainerBuilder()
                .withAdditionalItem(TransactionContainer.AdditionalItem(12345, null, null))
                .withPriceModifier(0.98)
                .withPaymentMethod(paySystem)
                .build(),
            "Invalid count of card digits: require last 4 digits"
        )
    }

    @Test
    fun validAdditionalCourier() {
        validatorService.validate(
            TestTransactionContainerBuilder()
                .withAdditionalItem(TransactionContainer.AdditionalItem(null, "SAGAWA", null))
                .withPaymentMethod(CASH_ON_DELIVERY)
                .withPriceModifier(1.0)
                .build()
        )

        validatorService.validate(
            TestTransactionContainerBuilder()
                .withAdditionalItem(TransactionContainer.AdditionalItem(null, "YAMATO", null))
                .withPaymentMethod(CASH_ON_DELIVERY)
                .withPriceModifier(1.0)
                .build()
        )
    }

    @Test
    fun invalidAdditionalUnfamiliarCourier() {
        assertThrow(
            TestTransactionContainerBuilder()
                .withPaymentMethod(CASH_ON_DELIVERY)
                .withPriceModifier(1.0)
                .build(),
            UNKNOWN_COURIER
        )
        assertThrow(
            TestTransactionContainerBuilder()
                .withAdditionalItem(TransactionContainer.AdditionalItem(null, "UNFAMILIAR", null))
                .withPaymentMethod(CASH_ON_DELIVERY)
                .withPriceModifier(1.0)
                .build(),
            UNKNOWN_COURIER
        )
    }

    @Test
    fun validAdditionalBankTransfer() {
        validatorService.validate(
            TestTransactionContainerBuilder()
                .withAdditionalItem(
                    TransactionContainer.AdditionalItem(
                    null, null,
                    TransactionContainer.BankItem(
                        "Bank name", "1234567", null
                    )
                ))
                .withPaymentMethod("BANK_TRANSFER")
                .withPriceModifier(1.0)
                .build(),
        )
    }

    @Test
    fun invalidAdditionalBankTransfer() {
        assertThrow(
            TestTransactionContainerBuilder()
                .withPaymentMethod("BANK_TRANSFER")
                .withPriceModifier(1.0)
                .build(),
            "Require bank name and account number in additional items"
        )
    }

    @Test
    fun validAdditionalCheque() {
        validatorService.validate(
            TestTransactionContainerBuilder()
                .withAdditionalItem(
                    TransactionContainer.AdditionalItem(
                    null, null,
                    TransactionContainer.BankItem(
                        "Bank name", null, "1234567"
                    )
                ))
                .withPaymentMethod(CHEQUE)
                .withPriceModifier(1.0)
                .build()
        )
    }

    @Test
    fun invalidAdditionalCheque() {
        assertThrow(
            TestTransactionContainerBuilder()
                .withPaymentMethod(CHEQUE)
                .withPriceModifier(1.0)
                .build(),
            "Require bank name and cheque number in additional items"
        )
        assertThrow(
            TestTransactionContainerBuilder()
                .withAdditionalItem(
                    TransactionContainer.AdditionalItem(
                    null, null,
                    TransactionContainer.BankItem(
                        "Bank name", null, null
                    )
                ))
                .withPaymentMethod(CHEQUE)
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
