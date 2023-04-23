package com.example.lenta_storage_app.model.entities

import java.time.LocalDate

data class IncomeStat(
    val incomeDate : LocalDate,
    val sumAmount : Int
)