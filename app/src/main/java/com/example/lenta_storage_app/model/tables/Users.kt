package com.example.lenta_storage_app.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Users : Table<Nothing>("users") {
    val id = int("user_id").primaryKey()
    val userName = varchar("username")
    val password = varchar("password")
    val roleId = int("role_id")
}