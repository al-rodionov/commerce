package com.commerce.service.impl.store

import com.commerce.mapper.toPaymentEntity
import com.commerce.mapper.toTranEntity
import com.commerce.model.container.TransactionContainer
import com.commerce.model.container.TransactionReportContainer
import com.commerce.model.entity.Payment
import com.commerce.repo.PaymentRepository
import com.commerce.repo.TransactionRepository
import com.commerce.service.PaymentStoreService
import com.commerce.service.TransactionStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PaymentStoreServiceImpl @Autowired constructor(
    val paymentRepository: PaymentRepository
) : PaymentStoreService {

    override fun store(container: TransactionContainer) {
        paymentRepository.save(toPaymentEntity(container))
    }

    override fun findReports(container: TransactionReportContainer): List<Payment> {
        return paymentRepository.findAllByDateTimeBetween(
            container.startDateTime,
            container.endDateTime
        )
    }
}