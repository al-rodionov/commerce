package com.commerce.service.impl.store

import com.commerce.mapper.toPaymentEntity
import com.commerce.mapper.toTranEntity
import com.commerce.model.container.TransactionContainer
import com.commerce.model.container.TransactionReportContainer
import com.commerce.model.entity.Payment
import com.commerce.repo.PaymentRepository
import com.commerce.repo.TransactionRepository
import com.commerce.service.StoreService
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
        val tranEntity = transactionRepository.save(toTranEntity(container))

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