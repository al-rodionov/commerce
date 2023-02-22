package com.commerce.service.impl

import com.commerce.repo.PaymentRepository
import com.commerce.repo.TransactionRepository
import com.commerce.service.StoreService
import com.commerce.util.TransactionContainerBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles

@DataJpaTest()
@ActiveProfiles("server")
@ComponentScan(basePackages = ["com.commerce.service.impl"])
class StoreServiceTests @Autowired constructor(
    val storeService: StoreService,
    val transactionRepository: TransactionRepository,
    val paymentRepository: PaymentRepository
){

    @Test
    fun massStore() {
        val random = (5..15).random()
        var counter = random
        while (counter > 0) {
            counter--
            store()
        }

        assertEquals(random, transactionRepository.findAll().size)
        assertEquals(random, paymentRepository.findAll().size)
    }

    private fun store() {
        storeService.store(TransactionContainerBuilder().build())
    }
}
