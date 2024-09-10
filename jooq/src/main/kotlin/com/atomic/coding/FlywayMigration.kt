package com.atomic.coding

import org.flywaydb.core.Flyway
import javax.sql.DataSource

fun flywayMigrate(dataSource: DataSource) {
    val flyway = Flyway.configure()
        .dataSource(
            dataSource
        )
        .load()
    flyway.migrate()
}