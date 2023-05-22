package com.example.lenta_storage_app.view.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.infrastructure.MyDateFormatter
import com.example.lenta_storage_app.view.DatePickerFragment
import com.example.lenta_storage_app.view.DirectorActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.time.LocalDate


class ShipmentStatsFragment : Fragment() {

    private var db = ApplicationDbContext()
    private lateinit var directorActivity: DirectorActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stats_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        directorActivity = (activity as DirectorActivity)
        directorActivity.setActionBarTitle("Отгрузка")

        var buttonCustomDate = view.findViewById(R.id.btnSetDate) as Button
        var buttonToday = view.findViewById(R.id.button_today) as Button

        buttonCustomDate.setOnClickListener { _ ->
            var datePickerFragment = DatePickerFragment()
            datePickerFragment.onDismissListener = {
                if (buttonCustomDate.text.toString() != "Выбрать дату") {
                    var date = MyDateFormatter().formatStringToDate(buttonCustomDate.text.toString())
                    var newDate = LocalDate.of(date.year, date.monthValue + 1, date.dayOfMonth)
                    buttonCustomDate.text = MyDateFormatter().formatDateToString(newDate)
                    getShipmentsStats(MyDateFormatter().formatStringToDate(buttonCustomDate.text.toString()))
                }
            }
            datePickerFragment.show(directorActivity.supportFragmentManager, "date picker")
        }
        buttonToday.setOnClickListener { _ ->
            getShipmentsStats(MyDateFormatter().getCurrentLocalData())
        }

        getShipmentsStats(MyDateFormatter().getCurrentLocalData())
    }

    private fun getShipmentsStats(date : LocalDate) {
        var chart = view?.findViewById(R.id.stat_chart) as BarChart
        chart.clear()
        var entries = ArrayList<BarEntry>()

        var incomes = db.getShipmentStats(date)
        val dateNames = ArrayList<String>()
        var i = 0
        var sum = 0

        for (income in incomes) {
            //add entry like (0, 25), where 0 is date, 25 – sum amount for this date
            entries.add(BarEntry(i.toFloat(), income.sumAmount.toFloat()))
            dateNames.add(MyDateFormatter().formatDateToString(income.shipmentDate))
            sum += income.sumAmount
            i++
        }

        var dataSet = BarDataSet(entries, "Отгружено, шт.")
        chart.data = BarData(dataSet)
        chart.invalidate()

        //var textIncomeAmount = view?.findViewById(R.id.text_amount) as TextView
        //textIncomeAmount.text = "Отгружено: " + sum
    }
}