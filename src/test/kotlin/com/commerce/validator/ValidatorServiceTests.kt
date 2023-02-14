package com.commerce.validator

import com.commerce.exception.ValidationException
import com.commerce.model.container.TransactionContainer
import com.commerce.service.ValidatorService
import com.commerce.util.generateTransactionContainer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("server")
class ValidatorServiceTests @Autowired constructor(
    val validatorService: ValidatorService
){

    @Test
    fun validRequest() {
        validatorService.validate(generateTransactionContainer())
        validatorService.validate(generateTransactionContainer(
            "VISA", 0.95,
            TransactionContainer.AdditionalItem(1111, null, null)
        ))
    }

    @Test
    fun invalidRequestBasic() {
        assertThrow(
            generateTransactionContainer("INVALID"),
            "Invalid payment method"
        )

        assertThrow(
            generateTransactionContainer(0.5),
            "Price modifier not in the range"
        )
    }

    @Test
    fun invalidRequestAdditional() {
        assertThrow(
            generateTransactionContainer("CASH_ON_DELIVERY", 1.0, null),
            "Unknown courier"
        )
        assertThrow(
            generateTransactionContainer("VISA"),
            "Require last 4 card digits in additional items"
        )
        assertThrow(
            generateTransactionContainer("BANK_TRANSFER", 1.0, null),
            "Require bank name and account number in additional items"
        )
        assertThrow(
            generateTransactionContainer("CHEQUE", 1.0, null),
            "Require bank name and cheque number in additional items"
        )
    }

    private fun assertThrow(container: TransactionContainer,
                            errorMsg: String) {
        val ex = assertThrows<ValidationException> {
            validatorService.validate(container)
        }
        print("718" + errorMsg)
        println("7181" + ex.message)
        assert(errorMsg.equals(ex.message))
    }
}
