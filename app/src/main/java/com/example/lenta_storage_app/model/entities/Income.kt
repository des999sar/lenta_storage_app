package com.example.lenta_storage_app.model.entities

import java.time.LocalDate

data class Income(
    val id : Int,
    val incomeDate : LocalDate,
    val productAmount : Int,
    val productId : Int,
    val incomeOrderId : Int,
    val empId : Int
)