package com.commerce.server.service.impl.calc

import com.commerce.server.model.container.TransactionContainer
import com.commerce.server.service.PaymentMethodService
import com.commerce.server.service.PointsCalcService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
@Profile("server")
class PointsCalcImpl @Autowired constructor(
    val configService: PaymentMethodService
) : PointsCalcService {

    override fun calculate(container: TransactionContainer): Double {
        val config = configService.getPaymentMethod(container.paymentMethod)
        return BigDecimal(container.price)
            .multiply(BigDecimal(config.pointsModifier))
            .setScale(2, RoundingMode.UP)
            .toDouble()
    }
}