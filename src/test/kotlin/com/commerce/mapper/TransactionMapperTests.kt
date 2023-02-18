package com.commerce.mapper

import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Transaction
import com.commerce.util.DATE_TIME
import com.commerce.util.generateTransaction
import com.commerce.util.generateTransactionContainer
import com.commerce.util.parseDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransactionMapperTests {

    @Test
    fun grpcToContainer() {
        val container: TransactionContainer = toContainer(generateTransaction())

        assertEquals(1L, container.customerId)
        assertEquals(100.0, container.price)
        assertEquals(0.95, container.priceModifier)
        assertEquals(parseDate(DATE_TIME), container.dateTime)

        val additionalItem = container.additionalItem
        assertEquals(1212, additionalItem?.last4)
        assertEquals("YAMATO", additionalItem?.courier)

        val bankItem = additionalItem?.bankItem
        assertEquals(bankItem?.bankName, "Bank Name 1")
        assertEquals(bankItem?.accountNumber, "1234567")
        assertEquals(bankItem?.chequeNumber, "7654321")
    }

    @Test
    fun containerToEntity() {
        val container = generateTransactionContainer(
            "CASH",
            0.95,
            TransactionContainer.AdditionalItem(2345, null, null)
        )
        val entity: Transaction = toTranEntity(container)

        assertEquals(1L, entity.customerId)
        assertEquals(1000.00, entity.price)
        assertEquals(0.95, entity.priceModifier)
        assertEquals(parseDate(DATE_TIME), entity.dateTime)
        assertEquals("last4=2345", entity.additionalItem)
    }

    @Test
    fun AdditionalItemsDbDescription() {
        val entity = toTranEntity(generateTransactionContainer(
            "VISA", 0.95,
            TransactionContainer.AdditionalItem(5252, null, null)
        ))

        println(entity.additionalItem)

        assertEquals("last4=5252", entity.additionalItem)
    }
}