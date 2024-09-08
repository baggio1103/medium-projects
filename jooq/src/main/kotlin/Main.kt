package com.atomiccoding

import org.flywaydb.core.Flyway

fun main() {
    val flyway = Flyway.configure()
        .dataSource(
            hikariDataSource()
        )
        .load()
    flyway.migrate()
}