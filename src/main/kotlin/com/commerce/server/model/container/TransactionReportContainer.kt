package com.commerce.server.model.container

import java.time.LocalDateTime


class TransactionReportContainer(
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
)