package com.commerce.mapper

import com.commerce.grpc.AdditionalItem
import com.commerce.grpc.BankItem
import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Transaction
import com.commerce.util.DATE_TIME
import com.commerce.util.builder.TransactionContainerBuilder
import com.commerce.util.builder.TransactionRequestBuilder
import com.commerce.util.parseDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransactionMapperTests {

    @Test
    fun transactionRequestToContainerDefault() {
        val container: TransactionContainer = toContainer(
            TransactionRequestBuilder()
                .withCustomerId(1L)
                .build()
        )

        assertEquals(1L, container.customerId)
        assertEquals(1000.0, container.price)
        assertEquals(0.95, container.priceModifier)
        assertEquals(parseDate(DATE_TIME), container.dateTime)
    }

    @Test
    fun transactionRequestToContainerAdditional() {
        val container: TransactionContainer = toContainer(
            TransactionRequestBuilder()
                .withAdditionalItem(
                    AdditionalItem.newBuilder()
                        .setLast4(1212)
                        .setCourier("YAMATO")
                        .setBankItem(
                            BankItem.newBuilder()
                                .setBankName("Bank Name 1")
                                .setAccountNumber("1234567")
                                .setChequeNumber("7654321")
                                .build()
                        )
                        .build()


                )
                .withCustomerId(1L)
                .withPrice(100.0)
                .build()
        )

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
        val container =
            TransactionContainerBuilder()
                .withAdditionalItem(
                    TransactionContainer.AdditionalItem(2345, null, null)
                )
                .withCustomerId(1L)
                .build()
        val entity: Transaction = toTransactionEntity(container)

        assertEquals(1L, entity.customerId)
        assertEquals(1000.00, entity.price)
        assertEquals(0.95, entity.priceModifier)
        assertEquals(parseDate(DATE_TIME), entity.dateTime)
        assertEquals("last4=2345", entity.additionalItem)
    }

    @Test
    fun additionalItemsDbDescriptionLast4Empty() {
        val container: TransactionContainer =
            TransactionContainerBuilder()
                .build()
        val entity = toTransactionEntity(container)

        assertEquals("", entity.additionalItem)
    }

    @Test
    fun additionalItemsDbDescriptionLast4() {
        val container: TransactionContainer =
            TransactionContainerBuilder()
                .withAdditionalItem(
                    TransactionContainer.AdditionalItem(5252, null, null)
                )
                .withPaymentMethod("VISA")
                .build()
        val entity = toTransactionEntity(container)

        assertEquals("last4=5252", entity.additionalItem)
    }
}