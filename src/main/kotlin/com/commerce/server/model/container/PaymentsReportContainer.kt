package com.commerce.server.model.container

import java.time.LocalDateTime


class PaymentsReportContainer(
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
)