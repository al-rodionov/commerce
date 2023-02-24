package com.commerce.server.mapper

import com.commerce.server.model.cache.PaymentMethod

fun toCache(paymentMethod: com.commerce.server.model.entity.PaymentMethod) = PaymentMethod(
    name = paymentMethod.name,
    priceModifierMin = paymentMethod.priceModifierMin,
    priceModifierMax = paymentMethod.priceModifierMax,
    pointsModifier = paymentMethod.pointsModifier
)
