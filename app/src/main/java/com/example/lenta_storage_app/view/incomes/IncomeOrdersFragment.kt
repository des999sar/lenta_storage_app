package com.example.lenta_storage_app.view.incomes

import IncomeOrderAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.model.entities.IncomeOrder
import com.example.lenta_storage_app.view.ShifSuperVisorActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class IncomeOrdersFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var db = ApplicationDbContext()
    private lateinit var incomeOrderAdapter: IncomeOrderAdapter
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
        shifSuperVisorActivity.setActionBarTitle("Приказы поставки")

        val btnAdd = view.findViewById(R.id.btnAdd) as FloatingActionButton
        btnAdd.setOnClickListener { _ ->
            shifSuperVisorActivity.openFragment(AddIncomeOrderFragment())
        }

        getIncomeOrders()
    }

    private fun getIncomeOrders() {
        recyclerView.adapter = null
        incomeOrderAdapter =
            IncomeOrderAdapter(db.getIncomeOrders(), object : IncomeOrderAdapter.Callback {
                override fun onItemClicked(item: IncomeOrder) {
                    shifSuperVisorActivity.openFragment(IncomesFragment(item.id))
                }

                override fun onItemDelete(item: IncomeOrder, position: Int) {
                    db.deleteIncomeOrder(item.id)
                    incomeOrderAdapter.list = db.getIncomeOrders()
                    incomeOrderAdapter.notifyItemRemoved(position)
                }
            })
        recyclerView.adapter = incomeOrderAdapter
    }
}