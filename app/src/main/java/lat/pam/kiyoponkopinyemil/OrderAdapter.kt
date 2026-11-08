package lat.pam.kiyoponkopinyemil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(
    private val listOrder: MutableList<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem: TextView = itemView.findViewById(R.id.tvItemOrder)
        val btnHapus: TextView = itemView.findViewById(R.id.tvHapusItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = listOrder[position]

        val parts = item.split("|")
        if (parts.size == 3) {
            val nama = parts[0]
            val jumlah = parts[1]
            val total = parts[2].toIntOrNull() ?: 0

            val totalFormatted = String.format("%,d", total).replace(',', '.')

            holder.tvItem.text = "$nama (${jumlah}x) - Rp $totalFormatted"
        } else {
            holder.tvItem.text = item
        }

        holder.btnHapus.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int = listOrder.size
}