package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.jdbcDate

object ShipmentOrders : Table<Nothing>("shipment_orders") {
    val id = int("shipment_order_id").primaryKey()
    val orderNumber = int("order_number")
    val creationDate = jdbcDate("creation_date")
    val clientId = int("client_id")
}