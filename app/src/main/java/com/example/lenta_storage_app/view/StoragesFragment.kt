package com.example.lenta_storage_app.view

import StorageAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.model.entities.Storage
import com.example.lenta_storage_app.view.products.ProductsFragment

class StoragesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var db = ApplicationDbContext()
    private lateinit var storageAdapter: StorageAdapter
    private lateinit var shifSuperVisorActivity: ShifSuperVisorActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.storages_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.storage_recycler_view)
        shifSuperVisorActivity = (activity as ShifSuperVisorActivity)
        shifSuperVisorActivity.setActionBarTitle("Склады")

        getStorages()
    }

    private fun getStorages() {
        recyclerView.adapter = null
        storageAdapter =
            StorageAdapter(db.getStorages(), object : StorageAdapter.Callback {
                override fun onItemClicked(item: Storage) {
                    shifSuperVisorActivity.openFragment(
                        ProductsFragment(item.id)
                    )
                }
            })
        recyclerView.adapter = storageAdapter
    }
}