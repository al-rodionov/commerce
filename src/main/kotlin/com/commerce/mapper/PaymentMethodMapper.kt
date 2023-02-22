package com.commerce.mapper

import com.commerce.model.entity.PaymentMethod

fun toCache(paymentMethod: PaymentMethod) = com.commerce.model.cache.PaymentMethod(
    name = paymentMethod.name,
    priceModifierMin = paymentMethod.priceModifierMin,
    priceModifierMax = paymentMethod.priceModifierMax,
    pointsModifier = paymentMethod.pointsModifier
)
