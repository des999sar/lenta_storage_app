package com.example.lenta_storage_app.view.incomes

import IncomeAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.model.entities.Income
import com.example.lenta_storage_app.view.ShifSuperVisorActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class IncomesFragment constructor(incomeOrderId : Int) : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var db = ApplicationDbContext()
    private lateinit var incomeAdapter: IncomeAdapter
    private lateinit var shifSuperVisorActivity: ShifSuperVisorActivity
    private val _incomeOrderId: Int = incomeOrderId

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
        shifSuperVisorActivity.setActionBarTitle("Поставки")

        val btnAdd = view.findViewById(R.id.btnAdd) as FloatingActionButton
        btnAdd.setOnClickListener { _ ->
            shifSuperVisorActivity.openFragment(AddIncomeFragment(_incomeOrderId))
        }

        getIncomes()
    }

    private fun getIncomes() {
        recyclerView.adapter = null
        incomeAdapter =
            IncomeAdapter(db.getIncomes(_incomeOrderId), object : IncomeAdapter.Callback {
                override fun onItemDelete(item: Income, position: Int) {
                    db.deleteIncome(item.id)
                    incomeAdapter.list = db.getIncomes(_incomeOrderId)
                    incomeAdapter.notifyItemRemoved(position)
                }
            })
        recyclerView.adapter = incomeAdapter
    }
}