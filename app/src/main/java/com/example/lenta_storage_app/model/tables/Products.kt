package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Products : Table<Nothing>("products") {
    val id = int("product_id").primaryKey()
    val name = varchar("name")
    val storageId = int("storage_id")
    var inStock = int("in_stock")
}