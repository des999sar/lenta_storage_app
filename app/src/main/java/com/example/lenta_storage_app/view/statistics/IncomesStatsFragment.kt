package com.example.lenta_storage_app.view.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.infrastructure.MyDateFormatter
import com.example.lenta_storage_app.infrastructure.MyValueFormatter
import com.example.lenta_storage_app.view.DatePickerFragment
import com.example.lenta_storage_app.view.DirectorActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.time.LocalDate


class IncomesStatsFragment : Fragment() {

    private var db = ApplicationDbContext()
    private lateinit var directorActivity: DirectorActivity
    private lateinit var textIncomeAmount : TextView
    private lateinit var textComplectedAmount : TextView
    private lateinit var textShipmentAmount : TextView

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
        directorActivity.setActionBarTitle("Статистика")

        var buttonCustomDate = view.findViewById(R.id.btnSetDate) as Button
        var buttonToday = view.findViewById(R.id.button_today) as Button

        textIncomeAmount = view.findViewById(R.id.text_income_amount) as TextView
        textComplectedAmount = view.findViewById(R.id.text_complected_amount) as TextView
        textShipmentAmount = view.findViewById(R.id.text_shipment_amount) as TextView

        buttonCustomDate.setOnClickListener { _ ->
            var datePickerFragment = DatePickerFragment()
            datePickerFragment.onDismissListener = {
                if (buttonCustomDate.text.toString() != "Выбрать дату") {
                    var date = MyDateFormatter().formatStringToDate(buttonCustomDate.text.toString())
                    var newDate = LocalDate.of(date.year, date.monthValue + 1, date.dayOfMonth)
                    buttonCustomDate.text = MyDateFormatter().formatDateToString(newDate)
                    getIncomesStats(MyDateFormatter().formatStringToDate(buttonCustomDate.text.toString()))
                }
            }
            datePickerFragment.show(directorActivity.supportFragmentManager, "date picker")
        }
        buttonToday.setOnClickListener { _ ->
            getIncomesStats(MyDateFormatter().getCurrentLocalData())
        }

        getIncomesStats(MyDateFormatter().getCurrentLocalData())
    }

    private fun getIncomesStats(date : LocalDate) {
        var chart = view?.findViewById(R.id.stat_chart) as BarChart
        chart.clear()
        var entries = ArrayList<BarEntry>()
        var stats = db.getStatisticsOnDate(date)
        val timeList = ArrayList<String>()
        val amounts = arrayOf(0, 0, 0)
        var i = 0

        for (stat in stats) {
            entries.add(BarEntry(i.toFloat(),
                floatArrayOf(
                    stat.incomeAmount.toFloat(),
                    stat.complectedAmount.toFloat(),
                    stat.shipmentAmount.toFloat()
                )))

            timeList.add(stat.timeString)

            amounts[0] += stat.incomeAmount
            amounts[1] += stat.complectedAmount
            amounts[2] += stat.shipmentAmount

            i++
        }

        var dataSet : BarDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            dataSet = chart.data.getDataSetByIndex(0) as BarDataSet
            dataSet.values = entries
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        }
        else {
            dataSet = BarDataSet(entries, "Статистика")
            dataSet.setDrawIcons(false)

            val colors = IntArray(3)
            System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3)
            dataSet.colors = mutableListOf(colors[0], colors[1], colors[2])

            dataSet.stackLabels = arrayOf("Принято", "Скомплектовано", "Отгружено")

            var dataSets = ArrayList<IBarDataSet>()
            dataSets.add(dataSet)

            var barData = BarData(dataSets)
            barData.setValueTextColor(Color.BLACK)
            barData.setValueTextSize(8f)
            barData.setDrawValues(false)

            chart.xAxis.valueFormatter = MyValueFormatter(timeList)
            chart.xAxis.textSize = 10f

            chart.data = barData
        }

        chart.setFitBars(true)
        chart.invalidate()

        textIncomeAmount.text = "Принято: " + amounts[0]
        textComplectedAmount.text = "Скомплектовано: " + amounts[1]
        textShipmentAmount.text = "Отгружено: " + amounts[2]
    }
}