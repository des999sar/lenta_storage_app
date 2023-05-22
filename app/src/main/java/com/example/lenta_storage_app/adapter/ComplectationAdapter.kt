import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.infrastructure.MyDateFormatter
import com.example.lenta_storage_app.model.entities.Complectation
import com.example.lenta_storage_app.model.entities.Income

class ComplectationAdapter (var list: ArrayList<Complectation>, val callback: Callback) : RecyclerView.Adapter<ComplectationAdapter.ViewHolder>() {
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
    {
        var textTitle: TextView = view.findViewById(R.id.text_title) as TextView
        var cardView: CardView = view.findViewById(R.id.card_view) as CardView
        var textDescription: TextView = view.findViewById(R.id.text_description) as TextView
        var imgDelete: ImageView = view.findViewById(R.id.imgDelete) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_with_delete, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var item = list[position]
        viewHolder.textTitle.text = "Комплектация № " + item.id
        //viewHolder.cardView.setOnClickListener { callback.onItemClicked(item) }

        viewHolder.textDescription.text = "Дата комплектации: " +
                MyDateFormatter().formatDateToString(item.complectationDate)
        viewHolder.imgDelete.setOnClickListener { callback.onItemDelete(item, position) }
    }

    interface Callback {
        //fun onItemClicked(product: Product)
        fun onItemDelete(item: Complectation, position: Int)
    }
}