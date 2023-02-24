package com.commerce.server.mapper

import com.commerce.grpc.TransactionReportRequest
import com.commerce.util.DATE_TIME
import com.commerce.util.formatDate
import com.commerce.util.parseDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit.SECONDS

@SpringBootTest
class TransactionReportMapperTests {

    @Test
    fun toContainer() {
        val endDateTime = LocalDateTime.now()
        val reportRequest = TransactionReportRequest.newBuilder()
            .setStartDateTime(DATE_TIME)
            .setEndDateTime(formatDate(endDateTime))
            .build()
        val container = toContainer(reportRequest)

        Assertions.assertEquals(parseDate(DATE_TIME), container.startDateTime)
        Assertions.assertEquals(endDateTime.truncatedTo(SECONDS), container.endDateTime)
    }
}