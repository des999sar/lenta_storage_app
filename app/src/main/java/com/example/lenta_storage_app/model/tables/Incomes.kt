package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.jdbcDate

object Incomes : Table<Nothing>("incomes") {
    val id = int("income_id").primaryKey()
    val incomeDate = jdbcDate("income_date")
    val productAmount = int("product_amount")
    val productId = int("product_id")
    val incomeOrderId = int("income_order_id")
    val empId = int("emp_id")
}