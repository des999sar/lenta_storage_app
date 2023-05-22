package com.example.lenta_storage_app.view.complectations

import ComplectationAdapter
import IncomeAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.model.entities.Complectation
import com.example.lenta_storage_app.model.entities.Income
import com.example.lenta_storage_app.view.ShifSuperVisorActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ComplectationsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var db = ApplicationDbContext()
    private lateinit var complectationAdapter: ComplectationAdapter
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
        shifSuperVisorActivity.setActionBarTitle("Комплектации")

        val btnAdd = view.findViewById(R.id.btnAdd) as FloatingActionButton
        btnAdd.setOnClickListener { _ ->
            shifSuperVisorActivity.openFragment(AddComplectationFragment())
        }

        getIncomes()
    }

    private fun getIncomes() {
        recyclerView.adapter = null
        complectationAdapter =
            ComplectationAdapter(db.getComplectations(), object : ComplectationAdapter.Callback {
                override fun onItemDelete(item: Complectation, position: Int) {
                    db.deleteComplectation(item.id)
                    complectationAdapter.list = db.getComplectations()
                    complectationAdapter.notifyItemRemoved(position)
                }
            })
        recyclerView.adapter = complectationAdapter
    }
}