package com.commerce.service.impl

import com.commerce.exception.ValidationException
import com.commerce.mapper.toCache
import com.commerce.model.cache.PaymentMethod
import com.commerce.repo.PaymentMethodRepository
import com.commerce.service.PaymentMethodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
@Profile("server")
@DependsOn("liquibase")
class PaymentMethodServiceImpl @Autowired constructor(
    repository: PaymentMethodRepository
) : PaymentMethodService {

    val configs: List<PaymentMethod> = repository.findAll().stream()
        .map { toCache(it) }
        .collect(Collectors.toList())

    override fun getPaymentMethod(name: String): PaymentMethod {
        return configs.find { it.name.equals(name) }
            ?: throw ValidationException("Invalid payment method")
    }
}