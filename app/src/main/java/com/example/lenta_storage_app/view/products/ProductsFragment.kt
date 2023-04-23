package com.example.lenta_storage_app.view.products

import ProductAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.model.entities.Product
import com.example.lenta_storage_app.view.ShifSuperVisorActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductsFragment constructor(storageId : Int) : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var db = ApplicationDbContext()
    private lateinit var productAdapter: ProductAdapter
    private val _storageId: Int = storageId
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
        shifSuperVisorActivity.setActionBarTitle("Товары")

        val btnShowAddProductFragment = view.findViewById(R.id.btnAdd) as FloatingActionButton
        btnShowAddProductFragment.setOnClickListener { _ ->
            shifSuperVisorActivity.openFragment(AddProductFragment(_storageId))
        }

        getProducts()
    }

    private fun getProducts() {
        recyclerView.adapter = null
        productAdapter =
            ProductAdapter(db.getProducts(_storageId), object : ProductAdapter.Callback {
                override fun onItemDelete(item: Product, position: Int) {
                    db.deleteProduct(item.id)
                    productAdapter.list = db.getProducts(_storageId)
                    productAdapter.notifyItemRemoved(position)
                }
            })
        recyclerView.adapter = productAdapter
    }
}