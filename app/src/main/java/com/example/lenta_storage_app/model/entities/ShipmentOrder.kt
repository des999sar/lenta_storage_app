package com.example.lenta_storage_app.model.entities

import java.time.LocalDate

data class ShipmentOrder(
    val id : Int,
    val orderNumber : Int,
    val creationDate : LocalDate,
    val clientId : Int
)
{
    override fun toString() : String {
        return "Номер приказа: " + orderNumber
    }
}