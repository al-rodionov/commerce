package com.commerce.service.calculator

import com.commerce.model.container.TransactionContainer
import com.commerce.service.PaymentsConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
@Profile("server")
class PointsCalcImpl @Autowired constructor(
    val configService: PaymentsConfigService
) : PointsCalculator {

    override fun calculate(container: TransactionContainer): Double {
        val config = configService.getPaymentsConfig(container.paymentMethod)
        return BigDecimal(container.price)
            .multiply(BigDecimal(config.pointsModifier))
            .setScale(2, RoundingMode.UP)
            .toDouble()
    }
}