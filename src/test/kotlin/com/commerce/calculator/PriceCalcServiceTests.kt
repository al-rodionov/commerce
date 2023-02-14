package com.commerce.calculator

import com.commerce.mapper.mapDateTime
import com.commerce.model.container.TransactionContainer
import com.commerce.service.PriceCalcService
import com.commerce.util.DATE_TIME
import com.commerce.util.generateTransactionContainer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("server")
class PriceCalcServiceTests @Autowired constructor(
    val calculator: PriceCalcService
){

    @Test
    fun calculatePrice() {
        val price = calculator.calculate(generateTransactionContainer())
        assert(price.compareTo(950.00) == 0)
    }

    @Test
    fun calculatePriceDigits() {
        val price = calculator.calculate(
            TransactionContainer(
                customerId = 1L,
                price = 333.18,
                priceModifier = 0.33,
                paymentMethod = "CASH",
                dateTime = mapDateTime(DATE_TIME),
                additionalItem = null
            )
        )
        assert(price.compareTo(109.95) == 0)
    }
}