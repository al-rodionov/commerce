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
    val configService: PaymentsConfigService
) {

    fun validate(container: TransactionContainer) {
        val paymentMethod = container.paymentMethod
        val config = configService.getPaymentsConfig(paymentMethod)

        validatePriceModifier(config, container.priceModifier)
        validateAdditional(paymentMethod, container.additionalItem)
    }
}