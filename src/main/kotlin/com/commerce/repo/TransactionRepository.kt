package com.commerce.repo

import com.commerce.model.entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long>