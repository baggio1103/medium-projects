package com.atomic.coding.repository

import com.atomic.coding.domain.ItemOrderDetails
import com.atomic.coding.domain.OrderDetails
import com.atomic.coding.domain.OrderItem
import com.atomicCoding.generated.public_.tables.Items.ITEMS
import com.atomicCoding.generated.public_.tables.OrderItems.ORDER_ITEMS
import com.atomicCoding.generated.public_.tables.Orders.ORDERS
import org.jooq.DSLContext
import org.jooq.impl.DSL.multiset
import org.jooq.impl.DSL.select
import java.math.BigDecimal
import java.time.LocalDateTime

class OrderRepository(
    private val dslContext: DSLContext
) {

    fun saveOrder(customerName: String, overallPrice: BigDecimal, context: DSLContext = dslContext): Int =
        context.insertInto(ORDERS)
            .set(ORDERS.CUSTOMER_NAME, customerName)
            .set(ORDERS.TOTAL_PRICE, overallPrice)
            .set(ORDERS.ORDER_DATE, LocalDateTime.now())
            .returning(ORDERS.ID).fetchOne { record -> record[ORDERS.ID] } ?: error("ID cannot be null")

    fun saveOrderItems(orderId: Int, orderItems: List<OrderItem>, context: DSLContext = dslContext) {
        context.insertInto(ORDER_ITEMS, ORDER_ITEMS.ORDER_ID, ORDER_ITEMS.ITEM_ID, ORDER_ITEMS.QUANTITY)
            .apply {
                orderItems.forEach { orderItem -> values(orderId, orderItem.itemId, orderItem.quantity) }
            }.execute()
    }

    fun findOrders(orderId: Int, context: DSLContext = dslContext): List<OrderDetails> = context.select(
        ORDERS.ID,
        ORDERS.CUSTOMER_NAME,
        ORDERS.TOTAL_PRICE,
        ORDERS.ORDER_DATE,
        multiset(
            select(ITEMS.ID, ITEMS.NAME, ORDER_ITEMS.QUANTITY).from(ITEMS)
                .innerJoin(ORDER_ITEMS).on(ORDER_ITEMS.ITEM_ID.eq(ITEMS.ID))
                .where(ORDER_ITEMS.ORDER_ID.eq(ORDERS.ID))
        ).convertFrom { records ->
            records.map { record ->
                ItemOrderDetails(
                    itemId = record[ITEMS.ID], quantity = record[ORDER_ITEMS.QUANTITY], name = record[ITEMS.NAME]
                )
            }
        }
    )
        .from(ORDERS)
        .where(ORDERS.ID.eq(orderId))
        .fetch { record ->
            OrderDetails(
                id = record[ORDERS.ID],
                customerName = record[ORDERS.CUSTOMER_NAME],
                totalAmount = record[ORDERS.TOTAL_PRICE],
                orderDate = record[ORDERS.ORDER_DATE],
                items = record.component5()
            )
        }

}