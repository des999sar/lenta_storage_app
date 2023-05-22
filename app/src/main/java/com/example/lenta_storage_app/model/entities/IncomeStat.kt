package com.example.lenta_storage_app.model.entities

import java.time.LocalTime

data class IncomeStat(
    val incomeTime : LocalTime,
    val incomeAmount : Int,
    val complectedAmount : Int
)