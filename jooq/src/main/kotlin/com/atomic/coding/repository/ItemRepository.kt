package com.atomic.coding.repository

import com.atomic.coding.domain.Item
import com.atomicCoding.generated.public_.tables.Items.ITEMS
import org.jooq.DSLContext

class ItemRepository(
    private val dslContext: DSLContext
) {

    fun findItems(ids: List<Int>): List<Item> = dslContext.select(ITEMS.ID, ITEMS.NAME, ITEMS.PRICE)
        .from(ITEMS)
        .where(ITEMS.ID.`in`(ids))
        .fetch { record ->
            Item(
                id = record[ITEMS.ID],
                name = record[ITEMS.NAME],
                price = record[ITEMS.PRICE],
            )
        }

}