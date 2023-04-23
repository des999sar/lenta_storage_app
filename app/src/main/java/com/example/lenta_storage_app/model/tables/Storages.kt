package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Storages : Table<Nothing>("storages") {
    val id = int("storage_id").primaryKey()
    val name = varchar("name")
    val storageConditions = varchar("storage_conditions")
    var orderingNumber = int("ordering_number")
}