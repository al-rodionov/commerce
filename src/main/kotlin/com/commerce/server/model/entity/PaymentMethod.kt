package com.commerce.server.model.entity

import javax.persistence.*

@Entity
@Table(name = "PAYMENT_METHOD")
class PaymentMethod (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?=null,

    @Column(nullable = false, name = "NAME")
    val name: String,

    @Column(nullable = false, name = "PRICE_MODIFIER_MIN")
    val priceModifierMin: Double,

    @Column(nullable = false, name = "PRICE_MODIFIER_MAX")
    val priceModifierMax: Double,

    @Column(nullable = false, name = "POINTS_MODIFIER")
    val pointsModifier: Double,
)