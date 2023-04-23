import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.model.entities.Storage

class StorageAdapter (var list: ArrayList<Storage>, val callback: Callback) : RecyclerView.Adapter<StorageAdapter.ViewHolder>() {
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
    {
        var textTitle: TextView = view.findViewById(R.id.text_title) as TextView
        var cardView: CardView = view.findViewById(R.id.card_view) as CardView
        var textDescription: TextView = view.findViewById(R.id.text_conditions) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.basic_card_view, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var item = list[position]
        viewHolder.textTitle.text = "Склад " + item.name
        viewHolder.cardView.setOnClickListener { callback.onItemClicked(item) }
        viewHolder.textDescription.text = "Условия хранения: " + item.storageConditions
    }

    interface Callback {
        fun onItemClicked(item: Storage)
    }
}