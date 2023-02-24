package com.commerce.server.service.impl

import com.commerce.server.mapper.toPaymentEntity
import com.commerce.server.mapper.toTransactionEntity
import com.commerce.server.model.container.TransactionContainer
import com.commerce.server.model.container.TransactionReportContainer
import com.commerce.server.model.entity.Payment
import com.commerce.server.repo.PaymentRepository
import com.commerce.server.repo.TransactionRepository
import com.commerce.server.service.StoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Profile("server")
class StoreServiceImpl @Autowired constructor(
    val transactionRepository: TransactionRepository,
    val paymentRepository: PaymentRepository
) : StoreService {

    @Transactional
    override fun store(container: TransactionContainer) {
        val tranEntity = transactionRepository.save(toTransactionEntity(container))

        val paymentEntity = toPaymentEntity(container)
        paymentEntity.transaction = tranEntity

        paymentRepository.save(paymentEntity)
    }

    override fun findReports(container: TransactionReportContainer): List<Payment> {
        return paymentRepository.findAllByDateTimeBetween(
            container.startDateTime,
            container.endDateTime
        )
    }
}