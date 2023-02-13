package com.commerce.service

import com.commerce.repo.TransactionRepository
import com.commerce.model.entity.Transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("server")
class TransactionStoreService @Autowired constructor(
    val repository: TransactionRepository
) {
    fun store(name: String) {
        println("save " + name)
        val tran = Transaction(null, name)
        repository.save(tran)
    }

    fun findAll(): List<Transaction> {
        return repository.findAll()
    }

}