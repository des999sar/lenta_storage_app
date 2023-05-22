package com.example.lenta_storage_app.model.entities

import java.time.LocalDate
import java.time.LocalTime

data class Income(
    val id : Int,
    val incomeDate : LocalDate,
    val incomeTime : LocalTime,
    val productAmount : Int,
    val productId : Int,
    val incomeOrderId : Int,
    val empId : Int
)

{
    override fun toString() : String {
        return "Приемка номер $id от $incomeDate $incomeTime"
    }
}