package com.commerce.repo

import com.commerce.model.entity.Payment
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

@Profile("server")
interface PaymentRepository : JpaRepository<Payment, Long> {
    fun findAllByDateTimeBetween(beginDate: LocalDateTime,
                                 endDate: LocalDateTime) : List<Payment>
}