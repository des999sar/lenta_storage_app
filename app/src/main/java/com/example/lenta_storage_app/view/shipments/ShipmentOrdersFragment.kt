package com.example.lenta_storage_app.view.shipments

import ShipmentOrderAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.model.entities.ShipmentOrder
import com.example.lenta_storage_app.view.ShifSuperVisorActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShipmentOrdersFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var db = ApplicationDbContext()
    private lateinit var shipmentOrderAdapter: ShipmentOrderAdapter
    private lateinit var shifSuperVisorActivity: ShifSuperVisorActivity

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
        shifSuperVisorActivity.setActionBarTitle("Приказы отгрузки")

        val btnAdd = view.findViewById(R.id.btnAdd) as FloatingActionButton
        btnAdd.setOnClickListener { _ ->
            shifSuperVisorActivity.openFragment(AddShipmentOrderFragment())
        }

        getShipmentOrders()
    }

    private fun getShipmentOrders() {
        recyclerView.adapter = null
        shipmentOrderAdapter =
            ShipmentOrderAdapter(db.getShipmentOrders(), object : ShipmentOrderAdapter.Callback {
                override fun onItemClicked(item: ShipmentOrder) {
                    shifSuperVisorActivity.openFragment(ShipmentsFragment(item.id))
                }

                override fun onItemDelete(item: ShipmentOrder, position: Int) {
                    db.deleteShipmentOrder(item.id)
                    shipmentOrderAdapter.list = db.getShipmentOrders()
                    shipmentOrderAdapter.notifyItemRemoved(position)
                }
            })
        recyclerView.adapter = shipmentOrderAdapter
    }
}