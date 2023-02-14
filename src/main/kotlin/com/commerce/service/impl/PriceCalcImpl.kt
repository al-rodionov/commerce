package com.commerce.service.impl

import com.commerce.model.container.TransactionContainer
import com.commerce.service.PriceCalcService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
@Profile("server")
class PriceCalcImpl : PriceCalcService {

    override fun calculate(container: TransactionContainer): Double {
        return BigDecimal(container.price)
            .multiply(BigDecimal(container.priceModifier))
            .setScale(2, RoundingMode.UP)
            .toDouble()
    }

}