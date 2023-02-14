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

    @Column(nullable = false)
    val name: String,

)