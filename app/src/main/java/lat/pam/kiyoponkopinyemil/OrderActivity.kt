package lat.pam.kiyoponkopinyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val rvOrder = findViewById<RecyclerView>(R.id.rvOrder)
        val tvTotalHarga = findViewById<TextView>(R.id.tvTotalHarga)
        val btnOrderKirim = findViewById<Button>(R.id.btnOrderKirim)
        val btnHapusKeranjang = findViewById<Button>(R.id.btnHapusKeranjang)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val keranjangLama = sharedPreferences.getStringSet("KERANJANG", mutableSetOf()) ?: mutableSetOf()


        val listOrder: MutableList<String> = ArrayList(keranjangLama)

        val adapter = OrderAdapter(listOrder) { position ->
            listOrder.removeAt(position)

            sharedPreferences.edit().putStringSet("KERANJANG", HashSet(listOrder)).apply()
            (rvOrder.adapter as OrderAdapter).notifyItemRemoved(position)

            hitungTotal(listOrder, tvTotalHarga)
        }

        rvOrder.layoutManager = LinearLayoutManager(this)
        rvOrder.adapter = adapter

        hitungTotal(listOrder, tvTotalHarga)

        btnHapusKeranjang.setOnClickListener {
            sharedPreferences.edit().remove("KERANJANG").apply()
            recreate()
        }

        btnOrderKirim.setOnClickListener {
            if (listOrder.isNotEmpty()) {
                startActivity(Intent(this, KonfirmasiActivity::class.java))
                sharedPreferences.edit().remove("KERANJANG").apply()
            }
        }
    }

    private fun hitungTotal(listOrder: List<String>, tvTotalHarga: TextView) {
        var total = 0
        for (item in listOrder) {
            val parts = item.split("|")
            if (parts.size == 3) {
                total += parts[2].toIntOrNull() ?: 0
            }
        }
        val totalFormatted = String.format("%,d", total).replace(',', '.')
        tvTotalHarga.text = "Total: Rp $totalFormatted"
    }
}