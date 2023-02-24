package com.commerce.server.service.impl

import com.commerce.server.exception.ValidationException
import com.commerce.server.mapper.toCache
import com.commerce.server.model.cache.PaymentMethod
import com.commerce.server.repo.PaymentMethodRepository
import com.commerce.server.service.PaymentMethodService
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