package com.commerce.repo

import com.commerce.model.entity.PaymentMethod
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository

@Profile("server")
interface PaymentMethodRepository : JpaRepository<PaymentMethod, Long> {
}