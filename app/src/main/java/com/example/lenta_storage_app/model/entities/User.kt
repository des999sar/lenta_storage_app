package com.example.lenta_storage_app.model.entities

data class User(
    val id : Int,
    val userName : String,
    val password : String,
    val roleId : Int
)