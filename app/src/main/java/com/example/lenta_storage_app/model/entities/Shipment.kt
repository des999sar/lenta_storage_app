package com.example.lenta_storage_app.model.entities

import java.time.LocalDate

data class Shipment(
    val id : Int,
    val shipmentDate : LocalDate,
    val productAmount : Int,
    val productId : Int,
    val shipmentOrderId : Int,
    val empId : Int
)