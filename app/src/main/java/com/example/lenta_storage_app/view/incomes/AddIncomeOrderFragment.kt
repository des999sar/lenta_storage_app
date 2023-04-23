package com.example.lenta_storage_app.view.incomes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.infrastructure.MyDateFormatter
import com.example.lenta_storage_app.model.entities.Supplier
import com.example.lenta_storage_app.view.DatePickerFragment
import com.example.lenta_storage_app.view.ShifSuperVisorActivity
import java.time.LocalDate

class AddIncomeOrderFragment : Fragment() {

    private var db = ApplicationDbContext()
    private lateinit var shifSuperVisorActivity: ShifSuperVisorActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_income_order_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shifSuperVisorActivity = (activity as ShifSuperVisorActivity)
        shifSuperVisorActivity.setActionBarTitle("Новый приказ поставки")

        val btnAdd = view.findViewById(R.id.btnAdd) as Button
        val editOrderNumber = view.findViewById(R.id.editOrderNumber) as EditText
        val spinnerSupplierId = view.findViewById(R.id.spinnerSupplierId) as Spinner
        val btnSetDate = view.findViewById(R.id.btnSetDate) as Button
        btnSetDate.setOnClickListener { _ ->
            var datePickerFragment = DatePickerFragment()
            datePickerFragment.onDismissListener = {
                if (btnSetDate.text.toString() != "Выбрать дату") {
                    var date = MyDateFormatter().formatStringToDate(btnSetDate.text.toString())
                    var newDate = LocalDate.of(date.year, date.monthValue + 1, date.dayOfMonth)
                    btnSetDate.text = MyDateFormatter().formatDateToString(newDate)
                }
            }
            datePickerFragment.show(shifSuperVisorActivity.supportFragmentManager, "date picker")
        }

        btnAdd.setOnClickListener { _ ->
            db.addIncomeOrder(editOrderNumber.text.toString().toInt(),
            LocalDate.parse(btnSetDate.text),
            (spinnerSupplierId.selectedItem as Supplier).id)
            shifSuperVisorActivity.returnBack()
        }

        setSuppliersSpinnerAdapter(spinnerSupplierId)
    }

    private fun setSuppliersSpinnerAdapter(spinner : Spinner) {
        var arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, db.getSuppliers())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define your actions if you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedObject = spinner.selectedItem as Supplier
            }
        }
    }
}