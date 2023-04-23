package com.example.lenta_storage_app.infrastructure

import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

class MyDateFormatter {
    fun formatDateToString(date : LocalDate) : String {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, date.year)
        calendar.set(Calendar.MONTH, date.monthValue)
        calendar.set(Calendar.DAY_OF_MONTH, date.dayOfMonth)

        return SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(date.toString()))
    }

    fun formatStringToDate(str : String) : LocalDate {
        return LocalDate.parse(str)
    }

    fun getCurrentLocalData() : LocalDate {
        var calendar = Calendar.getInstance()
        var stringDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

        return formatStringToDate(stringDate)
    }
}