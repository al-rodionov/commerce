package com.commerce.service

import com.commerce.model.entity.PaymentMethod
import com.commerce.repo.PaymentMethodRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("server")
class PaymentMethodService @Autowired constructor(
    val repository: PaymentMethodRepository
) {

    fun findAll(): List<PaymentMethod> {
        return repository.findAll()
    }

}