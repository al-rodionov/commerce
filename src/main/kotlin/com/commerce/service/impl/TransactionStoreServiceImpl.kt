package com.commerce.service.impl

import com.commerce.mapper.toPaymentEntity
import com.commerce.mapper.toTranEntity
import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Payment
import com.commerce.repo.PaymentRepository
import com.commerce.repo.TransactionRepository
import com.commerce.service.TransactionStoreService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TransactionStoreServiceImpl @Autowired constructor(
    val transactionRepository: TransactionRepository,
    val paymentRepository: PaymentRepository
) : TransactionStoreService {

    val logger: Logger = LoggerFactory.getLogger(TransactionStoreServiceImpl::class.java)

    override fun store(container: TransactionContainer) {
        logger.debug("save 718 + logger")
        transactionRepository.save(toTranEntity(container))
        paymentRepository.save(toPaymentEntity(container))
    }

    override fun findAllBetweenDates(beginDate: LocalDateTime,
                                     endDate: LocalDateTime
    ): List<Payment> {
        return paymentRepository.findAllByDateTimeBetween(beginDate, endDate)
    }

}