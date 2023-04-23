package com.example.lenta_storage_app.model.entities

data class Product(
    val id : Int,
    val name : String,
    val storageId : Int,
    var inStock : Int
)
{
    override fun toString() : String {
        return name
    }
}