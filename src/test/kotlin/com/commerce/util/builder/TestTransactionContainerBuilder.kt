package com.commerce.util.builder

import com.commerce.server.model.container.TransactionContainer
import com.commerce.util.DATE_TIME
import com.commerce.util.parseDate
import java.time.LocalDateTime


class TestTransactionContainerBuilder: AbstractTestTransactionBuilder<TransactionContainer>() {
    private var dateTime: LocalDateTime = parseDate(DATE_TIME)
    private var additionalItem: TransactionContainer.AdditionalItem? = null

    private var sales: Double = 0.0
    private var points: Double = 0.0

    fun withDateTime(dateTime: LocalDateTime): TestTransactionContainerBuilder {
        this.dateTime = dateTime
        return this
    }

    fun withAdditionalItem(additionalItem: TransactionContainer.AdditionalItem?): TestTransactionContainerBuilder {
        this.additionalItem = additionalItem
        return this
    }

    fun withSales(sales: Double) : TestTransactionContainerBuilder {
        this.sales = sales
        return this
    }

    fun withPoints(points: Double) : TestTransactionContainerBuilder {
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
