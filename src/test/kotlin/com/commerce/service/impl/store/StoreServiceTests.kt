package com.commerce.service.impl.store

import com.commerce.repo.PaymentRepository
import com.commerce.repo.TransactionRepository
import com.commerce.service.StoreService
import com.commerce.util.generateTransactionContainer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD


@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class StoreServiceTests @Autowired constructor(
    val storeService: StoreService,
    val transactionRepository: TransactionRepository,
    val paymentRepository: PaymentRepository
){

    @Test
    fun store() {
//    todo get id and contains in list
        storeService.store(generateTransactionContainer())
        storeService.store(generateTransactionContainer())
        storeService.store(generateTransactionContainer())

        assertTrue(transactionRepository.findAll().size >= 3)
        assertTrue(paymentRepository.findAll().size >= 3)
    }
}
