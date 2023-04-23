package com.example.lenta_storage_app.view.shipments

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
import com.example.lenta_storage_app.model.entities.Employee
import com.example.lenta_storage_app.model.entities.Product
import com.example.lenta_storage_app.view.DatePickerFragment
import com.example.lenta_storage_app.view.ShifSuperVisorActivity
import java.time.LocalDate

class AddShipmentFragment constructor(shipmentOrderId : Int) : Fragment() {

    private var db = ApplicationDbContext()
    private lateinit var shifSuperVisorActivity: ShifSuperVisorActivity
    private var _shipmentOrderId = shipmentOrderId

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_shipment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shifSuperVisorActivity = (activity as ShifSuperVisorActivity)
        shifSuperVisorActivity.setActionBarTitle("Новая отгрузка")

        val btnAdd = view.findViewById(R.id.btnAdd) as Button
        val editProductAmount = view.findViewById(R.id.editProductAmount) as EditText
        val spinnerProductId = view.findViewById(R.id.spinnerProductId) as Spinner
        //val spinnerShipmentOrderId = view.findViewById(R.id.spinnerShipmentOrderId) as Spinner
        val spinnerEmpId = view.findViewById(R.id.spinnerEmpId) as Spinner
        val btnSetShipmentDate = view.findViewById(R.id.btnSetDate) as Button
        btnSetShipmentDate.setOnClickListener { _ ->
            var datePickerFragment = DatePickerFragment()
            datePickerFragment.onDismissListener = {
                if (btnSetShipmentDate.text.toString() != "Выбрать дату") {
                    var date = MyDateFormatter().formatStringToDate(btnSetShipmentDate.text.toString())
                    var newDate = LocalDate.of(date.year, date.monthValue + 1, date.dayOfMonth)
                    btnSetShipmentDate.text = MyDateFormatter().formatDateToString(newDate)
                }
            }
            datePickerFragment.show(shifSuperVisorActivity.supportFragmentManager, "date picker")
        }

        btnAdd.setOnClickListener { _ ->
            db.addShipment(LocalDate.parse(btnSetShipmentDate.text),
            editProductAmount.text.toString().toInt(),
            (spinnerProductId.selectedItem as Product).id,
            _shipmentOrderId,
            //(spinnerShipmentOrderId.selectedItem as ShipmentOrder).id,
            (spinnerEmpId.selectedItem as Employee).id)
            shifSuperVisorActivity.returnBack()
        }

        setProductsSpinnerAdapter(spinnerProductId)
        setEmployeesSpinnerAdapter(spinnerEmpId)
        //setShipmentOrdersSpinnerAdapter(spinnerShipmentOrderId)
    }

    private fun setProductsSpinnerAdapter(spinner : Spinner) {
        var arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, db.getProducts())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define your actions if you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedObject = spinner.selectedItem as Product
            }
        }
    }

    private fun setEmployeesSpinnerAdapter(spinner : Spinner) {
        var arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, db.getEmployees())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define your actions if you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedObject = spinner.selectedItem as Employee
            }
        }
    }

/*    private fun setShipmentOrdersSpinnerAdapter(spinner : Spinner) {
        var arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, db.getShipmentOrders())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define your actions if you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedObject = spinner.selectedItem as ShipmentOrder
            }
        }
    }*/
}