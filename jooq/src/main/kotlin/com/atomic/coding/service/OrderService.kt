package com.atomic.coding.service

import com.atomic.coding.domain.OrderDetails
import com.atomic.coding.domain.OrderItem
import com.atomic.coding.repository.ItemRepository
import com.atomic.coding.repository.OrderRepository
import org.jooq.DSLContext

class OrderService(
    private val itemRepository: ItemRepository,
    private val orderRepository: OrderRepository,
    private val transactionManager: TransactionManager
) {

    fun order(customerName: String, orderItems: List<OrderItem>): List<OrderDetails> {
        val items = itemRepository.findItems(orderItems.map { it.itemId })
        val oderItemsById = orderItems.associateBy { it.itemId }
        val overallPrice = items.sumOf { item ->
            val itemQuantity = oderItemsById.getValue(item.id).quantity.toBigDecimal()
            item.price * itemQuantity
        }
        return transactionManager.runInTransaction { transactionContext ->
            // 1st Insert
            val orderId = orderRepository.saveOrder(
                customerName = customerName, overallPrice = overallPrice, context = transactionContext
            )
            // 2nd Insert
            orderRepository.saveOrderItems(
                orderId = orderId, orderItems = orderItems, context = transactionContext
            )

            orderRepository.findOrders(orderId, transactionContext)
        }
    }

}

class TransactionManager(private val dslContext: DSLContext) {

    fun <T> runInTransaction(block: (DSLContext) -> T): T =
        dslContext.transactionResult { configuration ->
            val transactionalContext = configuration.dsl()
            block(transactionalContext)
        }

}