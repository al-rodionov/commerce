package com.commerce

import com.commerce.service.TransactionStoreService
import com.commerce.util.generateTransactionContainer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class StoreServiceTests @Autowired constructor(
    val storeService: TransactionStoreService
){

    @Test
    fun store() {
        storeService.store(generateTransactionContainer())
        storeService.store(generateTransactionContainer())
        storeService.store(generateTransactionContainer())

        assert(storeService.findAll().size >= 3)
    }
}
