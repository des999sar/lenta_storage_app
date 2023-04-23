package com.example.lenta_storage_app.model.entities

data class Storage(
    val id : Int,
    val name : String,
    val storageConditions : String,
    var orderingNumber : Int
)