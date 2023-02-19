package com.commerce.service.impl.store

import com.commerce.repo.PaymentRepository
import com.commerce.repo.TransactionRepository
import com.commerce.service.StoreService
import com.commerce.util.generateTransactionContainer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles

@DataJpaTest()
@ActiveProfiles("server")
@ComponentScan(basePackages = ["com.commerce.service.impl.store"])
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

        assertEquals(3, transactionRepository.findAll().size)
        assertEquals(3, paymentRepository.findAll().size)
    }
}
