package com.example.lenta_storage_app.model.entities

data class Supplier(
    val id : Int,
    val name : String
)
{
    override fun toString() : String {
        return name
    }
}