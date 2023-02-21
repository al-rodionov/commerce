package com.commerce.service.impl.store

import com.commerce.mapper.toTranEntity
import com.commerce.model.container.TransactionContainer
import com.commerce.repo.TransactionRepository
import com.commerce.service.TransactionStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionStoreServiceImpl @Autowired constructor(
    val transactionRepository: TransactionRepository
) : TransactionStoreService {

    override fun store(container: TransactionContainer) {
        transactionRepository.save(toTranEntity(container))
    }

}