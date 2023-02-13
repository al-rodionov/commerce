package com.commerce

import com.commerce.service.TransactionStoreService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("server")
class StoreServiceTests @Autowired constructor(
    val storeService: TransactionStoreService
){

    @Test
    fun helloWorld() {
        storeService.store("service1")
        storeService.store("service2")
        storeService.store("service3")

        assert(storeService.findAll().size >= 3)
    }
}
