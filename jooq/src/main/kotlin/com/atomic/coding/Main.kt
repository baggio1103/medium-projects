package com.atomic.coding

import com.atomicCoding.generated.public_.tables.Posts
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.LoggerFactory

fun main() {
    val logger = LoggerFactory.getLogger("Jooq")
    val dataSource = hikariDataSource()
    val dsl = DSL.using(dataSource, SQLDialect.POSTGRES)
    val println = { value: Any -> logger.info(value.toString()) }
    dsl.selectFrom(Posts.POSTS).fetch().forEach(println)
}
