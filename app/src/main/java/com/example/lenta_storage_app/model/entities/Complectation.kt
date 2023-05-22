package com.example.lenta_storage_app.model.entities

import java.time.LocalDate
import java.time.LocalTime

data class Complectation(
    val id : Int,
    val incomeId : Int,
    val productAmount : Int,
    val complectationDate : LocalDate,
    val complectationTime : LocalTime
)
