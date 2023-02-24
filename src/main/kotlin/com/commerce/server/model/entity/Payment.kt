package com.commerce.server.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "PAYMENT")
class Payment (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?=null,

    @Column(nullable = false, name = "SALES")
    val sales: Double,

    @Column(nullable = false, name = "POINTS")
    val points: Double,

    @Column(nullable = false, name = "DATE_TIME")
    val dateTime: LocalDateTime,

    @OneToOne
    var transaction: Transaction?
)