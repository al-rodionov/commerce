package com.commerce.service.impl.calc

import com.commerce.model.container.TransactionContainer
import com.commerce.service.PaymentConfigService
import com.commerce.service.PointsCalcService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class PointsCalcImpl @Autowired constructor(
    val configService: PaymentConfigService
) : PointsCalcService {

    override fun calculate(container: TransactionContainer): Double {
        val config = configService.getPaymentConfig(container.paymentMethod)
        return BigDecimal(container.price)
            .multiply(BigDecimal(config.pointsModifier))
            .setScale(2, RoundingMode.UP)
            .toDouble()
    }
}