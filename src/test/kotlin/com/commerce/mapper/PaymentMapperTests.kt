package com.commerce.mapper

import com.commerce.model.entity.Payment
import com.commerce.util.DATE_TIME
import com.commerce.util.builder.TransactionContainerBuilder
import com.commerce.util.parseDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class PaymentMapperTests {

    @Test
    fun toReportItem() {
        val payment = Payment(
            null,
            1328.97,
            21.14,
            parseDate(DATE_TIME),
            null
        )
        val reportItem = toReportItem(payment)

        assertEquals(1328.97, reportItem.sales)
        assertEquals(21.14, reportItem.points)
        assertEquals(DATE_TIME, reportItem.dateTime)
    }

    @Test
    fun toPaymentEntity() {
        val dateBeforeConvert = LocalDateTime.now()
        val payment = toPaymentEntity(
            TransactionContainerBuilder()
                .withSales(6271.22)
                .withPoints(53.90)
                .build()
        )

        assertEquals(6271.22, payment.sales)
        assertEquals(53.90, payment.points)
        assertTrue(dateBeforeConvert.isBefore(payment.dateTime))
        assertTrue(LocalDateTime.now().isAfter(payment.dateTime))
    }
}