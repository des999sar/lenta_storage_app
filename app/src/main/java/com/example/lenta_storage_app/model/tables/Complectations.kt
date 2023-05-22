package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.jdbcDate
import org.ktorm.schema.jdbcTime

object Complectations : Table<Nothing>("complectations") {
    val id = int("complectation_id").primaryKey()
    val incomeId = int("income_id")
    val productAmount = int("product_amount")
    val complectationDate = jdbcDate("complectation_date")
    val complectationTime = jdbcTime("complectation_time")
}