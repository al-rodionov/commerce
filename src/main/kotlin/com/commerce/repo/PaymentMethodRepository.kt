package com.commerce.repo

import com.commerce.model.entity.PaymentMethod
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentMethodRepository : JpaRepository<PaymentMethod, Long>