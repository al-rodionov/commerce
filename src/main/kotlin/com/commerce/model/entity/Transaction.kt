package com.commerce.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "TRANSACTION")
class Transaction (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?=null,

    @Column(nullable = false, name = "CUSTOMER_ID")
    val customerId: Long,

    @Column(nullable = false, name = "PRICE")
    val price: Double,

    @Column(nullable = false, name = "PRICE_MODIFIER")
    val priceModifier: Double,

    @Column(nullable = false, name = "PAYMENT_METHOD")
    val paymentMethod: String,

    @Column(nullable = false, name = "DATE_TIME")
    val dateTime: LocalDateTime,

    @Column(name = "ADDITIONAL_ITEM")
    val additionalItem: String,

    @OneToOne(mappedBy = "transaction")
    var payment: Payment?
)