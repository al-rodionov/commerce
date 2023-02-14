package com.commerce.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

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