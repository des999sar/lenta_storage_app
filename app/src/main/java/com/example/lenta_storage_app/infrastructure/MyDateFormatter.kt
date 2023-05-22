package com.example.lenta_storage_app.infrastructure

import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

class MyDateFormatter {
    fun formatDateToString(date : LocalDate) : String {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, date.year)
        calendar.set(Calendar.MONTH, date.monthValue)
        calendar.set(Calendar.DAY_OF_MONTH, date.dayOfMonth)

        return SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(date.toString()))
    }

    fun formatTimeToString(time : LocalTime) : String {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, time.hour)
        calendar.set(Calendar.MINUTE, time.minute)
        calendar.set(Calendar.SECOND, time.second)
        return SimpleDateFormat("HH:mm:ss").format(calendar.time)
    }

    fun formatStringToDate(str : String) : LocalDate {
        return LocalDate.parse(str)
    }

    fun formatStringToTime(str : String) : LocalTime {
        return LocalTime.parse(str)
    }

    fun getCurrentLocalData() : LocalDate {
        var calendar = Calendar.getInstance()
        var stringDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

        return formatStringToDate(stringDate)
    }

    fun getCurrentLocalTime() : LocalTime {
        var calendar = Calendar.getInstance()
        var stringTime = SimpleDateFormat("HH:mm:ss").format(calendar.time)
        return formatStringToTime(stringTime)
    }
}