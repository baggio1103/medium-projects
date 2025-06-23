package com.atomic.coding.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    val id: Int,
    val customerName: String,
    val totalAmount: BigDecimal,
    val orderDate: LocalDateTime,
)

data class Item(
    val id: Int,
    val name: String,
    val price: BigDecimal,
)

data class OrderItem(
    val itemId: Int,
    val quantity: Int,
)

data class OrderDetails(
    val id: Int,
    val customerName: String,
    val totalAmount: BigDecimal,
    val orderDate: LocalDateTime,
    val items: List<ItemOrderDetails>,
)

data class ItemOrderDetails(
    val itemId: Int,
    val quantity: Int,
    val name: String
)