package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Positions : Table<Nothing>("positions") {
    val id = int("pos_id").primaryKey()
    val name = varchar("name")
}