package com.atomic.coding

import com.atomic.coding.Application.CUSTOMER_NAME
import com.atomic.coding.Application.logger
import com.atomic.coding.Application.orderItems
import com.atomic.coding.Application.orderService
import com.atomic.coding.domain.OrderItem
import com.atomic.coding.repository.DataSource
import com.atomic.coding.repository.ItemRepository
import com.atomic.coding.repository.OrderRepository
import com.atomic.coding.service.OrderService
import com.atomic.coding.service.TransactionManager
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Application {
    val logger: Logger = LoggerFactory.getLogger("Application")
    private val dsl = DSL.using(
        DataSource.hikariDataSource(),
        SQLDialect.POSTGRES
    )
    val orderService = OrderService(
        orderRepository = OrderRepository(dsl),
        itemRepository = ItemRepository(dsl),
        transactionManager = TransactionManager(dsl)
    )

    val orderItems = listOf(
        OrderItem(1, 10),
        OrderItem(2, 2)
    )
    const val CUSTOMER_NAME = "Bruce"
}

fun main() {
    val orders = orderService.order(customerName = CUSTOMER_NAME, orderItems = orderItems)
    logger.info("Orders: $orders")
}
