package com.commerce.service

import com.commerce.exception.ValidationException
import com.commerce.model.container.TransactionContainer
import com.commerce.validator.validateAdditional
import com.commerce.validator.validatePriceModifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("server")
class ValidatorService @Autowired constructor(
    configService: PaymentsConfigService
) {

    val paymentsConfig = configService.getPaymentsConfig()

    fun validate(container: TransactionContainer) {
        val paymentMethod = container.paymentMethod
        val config = paymentsConfig.find { it.name.equals(paymentMethod) }
            ?: throw ValidationException("Invalid payment method")

        validatePriceModifier(config, container.priceModifier)
        validateAdditional(paymentMethod, container.additionalItem)
    }
}