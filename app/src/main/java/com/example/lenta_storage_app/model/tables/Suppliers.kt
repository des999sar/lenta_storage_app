package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Suppliers : Table<Nothing>("suppliers") {
    val id = int("supplier_id").primaryKey()
    val name = varchar("name")
}