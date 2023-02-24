package com.commerce.server.service.impl.calc

import com.commerce.server.model.container.TransactionContainer
import com.commerce.server.service.PriceCalcService
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