package com.example.lenta_storage_app.model.entities

data class Employee(
    val id : Int,
    val surname : String,
    val firstname : String,
    val patronymic : String,
    val positionId : Int,
    val departmentId : Int,
    val userId : Int
)

{
    override fun toString() : String {
        return surname + ' ' + firstname
    }
}