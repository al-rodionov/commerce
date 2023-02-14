package com.commerce.service

import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Transaction
import com.commerce.model.mapper.toEntity
import com.commerce.repo.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("server")
class TransactionStoreService @Autowired constructor(
    val repository: TransactionRepository
) {
    fun store(transaction: TransactionContainer) {
        println("save ")
        repository.save(
            toEntity(transaction)
        )
    }

    fun findAll(): List<Transaction> {
        return repository.findAll()
    }

}