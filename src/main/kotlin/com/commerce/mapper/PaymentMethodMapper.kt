package com.commerce.mapper

import com.commerce.model.cache.PaymentConfig
import com.commerce.model.entity.PaymentMethod

fun toCache(paymentMethod: PaymentMethod) = PaymentConfig (
    name = paymentMethod.name,
    priceModifierMin = paymentMethod.priceModifierMin,
    priceModifierMax = paymentMethod.priceModifierMax,
    pointsModifier = paymentMethod.pointsModifier
)
