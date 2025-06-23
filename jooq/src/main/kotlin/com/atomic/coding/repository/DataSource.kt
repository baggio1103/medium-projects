package com.atomic.coding.repository

import com.zaxxer.hikari.HikariDataSource

object DataSource {

    fun hikariDataSource(): HikariDataSource = HikariDataSource().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5432/ecommerce"
        username = "ecommerce-user"
        password = "ecommerce-password"
    }

}

