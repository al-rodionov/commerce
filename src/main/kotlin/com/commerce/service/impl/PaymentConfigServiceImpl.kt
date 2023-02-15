package com.commerce.service.impl

import com.commerce.exception.ValidationException
import com.commerce.mapper.toCache
import com.commerce.model.cache.PaymentConfig
import com.commerce.repo.PaymentMethodRepository
import com.commerce.service.PaymentConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
@Profile("!test")
class PaymentConfigServiceImpl @Autowired constructor(
    repository: PaymentMethodRepository
) : PaymentConfigService {

    val configs: List<PaymentConfig> = repository.findAll().stream()
        .map { toCache(it) }
        .collect(Collectors.toList())

    override fun getPaymentConfig(paymentMethod: String): PaymentConfig {
        return configs.find { it.name.equals(paymentMethod) }
            ?: throw ValidationException("Invalid payment method")
    }
}