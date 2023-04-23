package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.jdbcDate

object Shipments : Table<Nothing>("shipments") {
    val id = int("shipment_id").primaryKey()
    val shipmentDate = jdbcDate("shipment_date")
    val productAmount = int("product_amount")
    val productId = int("product_id")
    val shipmentOrderId = int("shipment_order_id")
    val empId = int("emp_id")
}