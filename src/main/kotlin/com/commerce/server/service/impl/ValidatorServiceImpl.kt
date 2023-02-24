package com.commerce.server.service.impl

import com.commerce.server.model.container.TransactionContainer
import com.commerce.server.service.PaymentMethodService
import com.commerce.server.service.ValidatorService
import com.commerce.server.validator.validateAdditional
import com.commerce.server.validator.validatePriceModifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("server")
class ValidatorServiceImpl @Autowired constructor(
    val configService: PaymentMethodService
) : ValidatorService {

    override fun validate(container: TransactionContainer) {
        val paymentMethod = container.paymentMethod
        val config = configService.getPaymentMethod(paymentMethod)

        validatePriceModifier(config, container.priceModifier)
        validateAdditional(paymentMethod, container.additionalItem)
    }
}