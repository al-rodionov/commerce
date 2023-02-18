package com.commerce.service.impl.store

import com.commerce.repo.TransactionRepository
import com.commerce.service.TransactionStoreService
import com.commerce.util.generateTransactionContainer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD


@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class TransactionStoreServiceTests @Autowired constructor(
    val storeService: TransactionStoreService,
    val repository: TransactionRepository
){

    @Test
    fun store() {
        storeService.store(generateTransactionContainer())
        storeService.store(generateTransactionContainer())
        storeService.store(generateTransactionContainer())

        assertEquals(3, repository.findAll().size)
    }
}
