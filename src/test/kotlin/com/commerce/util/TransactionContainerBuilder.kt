package com.commerce.util

import com.commerce.model.container.TransactionContainer
import java.time.LocalDateTime


class TransactionContainerBuilder: AbstractTransactionBuilder<TransactionContainer>() {
    private var dateTime: LocalDateTime = parseDate(DATE_TIME)
    private var additionalItem: TransactionContainer.AdditionalItem? = null

    private var sales: Double = 0.0
    private var points: Double = 0.0

    fun withDateTime(dateTime: LocalDateTime): TransactionContainerBuilder {
        this.dateTime = dateTime
        return this
    }

    fun withAdditionalItem(additionalItem: TransactionContainer.AdditionalItem?): TransactionContainerBuilder {
        this.additionalItem = additionalItem
        return this
    }

    fun withSales(sales: Double) :TransactionContainerBuilder {
        this.sales = sales
        return this
    }

    fun withPoints(points: Double) : TransactionContainerBuilder {
        this.points = points
        return this
    }

    override fun build(): TransactionContainer {
        return TransactionContainer(
            customerId = customerId,
            price = price,
            priceModifier = priceModifier,
            paymentMethod = paymentMethod,
            dateTime = dateTime,
            additionalItem = additionalItem,
            sales = sales,
            points = points
        )
    }
}
