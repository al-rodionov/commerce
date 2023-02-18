package com.commerce.service.impl.store

import com.commerce.repo.PaymentRepository
import com.commerce.service.PaymentStoreService
import com.commerce.util.generateTransactionContainer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD


@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class PaymentStoreServiceTests @Autowired constructor(
    val storeService: PaymentStoreService,
    val repository: PaymentRepository
){

    @Test
    fun store() {
        storeService.store(generateTransactionContainer())
        storeService.store(generateTransactionContainer())
        storeService.store(generateTransactionContainer())

        Assertions.assertEquals(3, repository.findAll().size)
    }
}
