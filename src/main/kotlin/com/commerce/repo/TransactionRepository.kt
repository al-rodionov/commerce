package com.commerce.repo

import com.commerce.model.entity.Transaction
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository

@Profile("server")
interface TransactionRepository : JpaRepository<Transaction, Long>