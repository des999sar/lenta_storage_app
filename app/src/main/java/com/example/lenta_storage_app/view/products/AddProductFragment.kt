package com.example.lenta_storage_app.view.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.example.lenta_storage_app.view.ShifSuperVisorActivity

class AddProductFragment constructor(storageId : Int) : Fragment() {

    private var db = ApplicationDbContext()
    private var _storageId = storageId
    private lateinit var shifSuperVisorActivity: ShifSuperVisorActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_product_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shifSuperVisorActivity = (activity as ShifSuperVisorActivity)
        shifSuperVisorActivity.setActionBarTitle("Новый товар")

        val btnAdd = view.findViewById(R.id.btnAdd) as Button
        val editName = view.findViewById(R.id.editName) as EditText

        btnAdd.setOnClickListener { _ ->
            db.addProduct(editName.text.toString(), _storageId)
            shifSuperVisorActivity.returnBack()
        }
    }
}