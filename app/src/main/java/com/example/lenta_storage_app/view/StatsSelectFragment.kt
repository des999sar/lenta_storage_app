package com.example.lenta_storage_app.view

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.view.statistics.IncomesStatsFragment
import com.example.lenta_storage_app.view.statistics.ShipmentStatsFragment

class StatsSelectFragment : Fragment() {

    private lateinit var directorActivity: DirectorActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stats_select_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        directorActivity = (activity as DirectorActivity)
        directorActivity.setActionBarTitle("Выбор статистики")

        var buttonIncomesStats = view.findViewById(R.id.button_incomes_stats) as Button
        var buttonShipmentsStats = view.findViewById(R.id.button_shipments_stats) as Button

        buttonIncomesStats.setOnClickListener { _ ->
            directorActivity.openFragment(IncomesStatsFragment())
        }
        buttonShipmentsStats.setOnClickListener { _ ->
            directorActivity.openFragment(ShipmentStatsFragment())
        }
    }
}