package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.jdbcDate

object IncomeOrders : Table<Nothing>("income_orders") {
    val id = int("income_order_id").primaryKey()
    val orderNumber = int("order_number")
    val creationDate = jdbcDate("creation_date")
    val supplierId = int("supplier_id")
}