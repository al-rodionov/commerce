package com.commerce.service.impl

import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Transaction
import com.commerce.mapper.toEntity
import com.commerce.repo.TransactionRepository
import com.commerce.service.TransactionStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("server")
class TransactionStoreServiceImpl @Autowired constructor(
    val repository: TransactionRepository
) : TransactionStoreService {
    override fun store(transaction: TransactionContainer) {
        println("save ")
        repository.save(
            toEntity(transaction)
        )
    }

    override fun findAll(): List<Transaction> {
        return repository.findAll()
    }

}