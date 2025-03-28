package com.atomic.coding

import com.zaxxer.hikari.HikariDataSource

fun hikariDataSource(): HikariDataSource = HikariDataSource().apply {
    jdbcUrl = "jdbc:postgresql://localhost:5432/book-hub"
    username = "book-hub-user"
    password = "hashed-password"
}
