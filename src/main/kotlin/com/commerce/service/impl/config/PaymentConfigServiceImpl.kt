package com.commerce.service.impl.config

import com.commerce.exception.ValidationException
import com.commerce.mapper.toPaymentConfig
import com.commerce.model.cache.PaymentConfig
import com.commerce.repo.PaymentMethodRepository
import com.commerce.service.PaymentConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
@Profile("server")
@DependsOnDatabaseInitialization
class PaymentConfigServiceImpl @Autowired constructor(
    repository: PaymentMethodRepository
) : PaymentConfigService {

    val configs: List<PaymentConfig> = repository.findAll().stream()
        .map { toPaymentConfig(it) }
        .collect(Collectors.toList())

    override fun getPaymentConfig(paymentMethod: String): PaymentConfig {
        return configs.find { it.name.equals(paymentMethod) }
            ?: throw ValidationException("Invalid payment method")
    }
}