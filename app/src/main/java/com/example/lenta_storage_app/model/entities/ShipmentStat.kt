package com.example.lenta_storage_app.model.entities

import java.time.LocalDate

data class ShipmentStat(
    val shipmentDate : LocalDate,
    val sumAmount : Int
)