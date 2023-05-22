package com.example.lenta_storage_app.infrastructure

import com.github.mikephil.charting.formatter.ValueFormatter

class MyValueFormatter constructor(timeList : ArrayList<String>) : ValueFormatter() {
    private val _timeList = timeList

    override fun getFormattedValue(value: Float): String {
        return _timeList[value.toInt()]
    }
}