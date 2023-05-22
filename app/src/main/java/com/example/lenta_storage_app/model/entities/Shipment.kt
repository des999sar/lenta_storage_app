package com.example.lenta_storage_app.model.entities

import java.time.LocalDate
import java.time.LocalTime

data class Shipment(
    val id : Int,
    val shipmentDate : LocalDate,
    val shipmentTime : LocalTime,
    val productAmount : Int,
    val productId : Int,
    val shipmentOrderId : Int,
    val empId : Int
)