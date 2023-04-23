package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Employees : Table<Nothing>("employees") {
    val id = int("emp_id").primaryKey()
    val surname = varchar("surname")
    val firstname = varchar("firstname")
    val patronymic = varchar("patronymic")
    val positionId = int("pos_id")
    val departmentId = int("dep_id")
    val userId = int("user_id")
}