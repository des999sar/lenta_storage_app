package com.example.lenta_storage_app.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.infrastructure.MyDateFormatter
import com.example.lenta_storage_app.view.incomes.IncomeOrdersFragment
import com.example.lenta_storage_app.view.shipments.ShipmentOrdersFragment
import com.google.android.material.navigation.NavigationBarView
import java.time.LocalDate

class DirectorActivity : AppCompatActivity(), /*FragmentManager.OnBackStackChangedListener,*/ DatePickerDialog.OnDateSetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_director)

        openFragment(StatsSelectFragment())

        //config bottom navigation view
        var navigationBarView = findViewById<NavigationBarView>(R.id.bottom_navigation)
        navigationBarView.setOnItemSelectedListener (onNavigationItemSelectedListener)
        //supportFragmentManager.addOnBackStackChangedListener (this)
    }

    private val onNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener { menuItem ->
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()

        when (menuItem.itemId) {
            R.id.action_stats -> {
                openFragment(StatsSelectFragment())
                return@OnItemSelectedListener true
            }
        }
        false
    }

    fun openFragment(fragment : Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment).addToBackStack(null)
            .setReorderingAllowed(true)
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .show(fragment).commit()
    }

    fun hideFragment(fragment : Fragment)
    {
        if (fragment.isVisible) {
            supportFragmentManager.beginTransaction().hide(fragment).commit()
        }
    }

    fun returnBack() {
        supportFragmentManager.popBackStack();
    }

    fun setActionBarTitle(title : String) {
        supportActionBar?.title = title
    }

/*    override fun onBackStackChanged() {
        val canGoBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar!!.setDisplayHomeAsUpEnabled(canGoBack)
    }*/

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        var btnSetIncomeDate = findViewById(R.id.btnSetDate) as Button
        btnSetIncomeDate.text = MyDateFormatter().formatDateToString(LocalDate.of(p1, p2, p3))
    }
}