package com.commerce.service.impl

import com.commerce.model.container.TransactionContainer
import com.commerce.service.PaymentConfigService
import com.commerce.service.ValidatorService
import com.commerce.validator.validateAdditional
import com.commerce.validator.validatePriceModifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ValidatorServiceImpl @Autowired constructor(
    val configService: PaymentConfigService
) : ValidatorService {

    override fun validate(container: TransactionContainer) {
        val paymentMethod = container.paymentMethod
        val config = configService.getPaymentConfig(paymentMethod)

        validatePriceModifier(config, container.priceModifier)
        validateAdditional(paymentMethod, container.additionalItem)
    }
}