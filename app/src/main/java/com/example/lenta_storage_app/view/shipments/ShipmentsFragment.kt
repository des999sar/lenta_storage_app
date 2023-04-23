package com.example.lenta_storage_app.view.shipments

import ShipmentAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.model.entities.Shipment
import com.example.lenta_storage_app.view.ShifSuperVisorActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShipmentsFragment constructor(shipmentOrderId : Int) : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var db = ApplicationDbContext()
    private lateinit var shipmentAdapter: ShipmentAdapter
    private lateinit var shifSuperVisorActivity: ShifSuperVisorActivity
    private val _shipmentOrderId = shipmentOrderId

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_with_add_button_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shifSuperVisorActivity = (activity as ShifSuperVisorActivity)
        recyclerView = view.findViewById(R.id.recycler_view_with_add_button)
        shifSuperVisorActivity.setActionBarTitle("Отгрузки")

        val btnAdd = view.findViewById(R.id.btnAdd) as FloatingActionButton
        btnAdd.setOnClickListener { _ ->
            shifSuperVisorActivity.openFragment(AddShipmentFragment(_shipmentOrderId))
        }

        getShipments()
    }

    private fun getShipments() {
        recyclerView.adapter = null
        shipmentAdapter =
            ShipmentAdapter(db.getShipments(_shipmentOrderId), object : ShipmentAdapter.Callback {
                override fun onItemDelete(item: Shipment, position: Int) {
                    db.deleteShipment(item.id)
                    shipmentAdapter.list = db.getShipments(_shipmentOrderId)
                    shipmentAdapter.notifyItemRemoved(position)
                }
            })
        recyclerView.adapter = shipmentAdapter
    }
}