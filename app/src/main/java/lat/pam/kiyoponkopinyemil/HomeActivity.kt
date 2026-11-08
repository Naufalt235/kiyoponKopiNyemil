package lat.pam.kiyoponkopinyemil

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val namaUser = sharedPreferences.getString("NAMA_USER", "Pengunjung") ?: "Pengunjung"
        val tvSapaan = findViewById<TextView>(R.id.tvSapaan)
        tvSapaan.text = "Halo $namaUser,"

        // --- DATA MAKANAN ---
        val listMakanan = listOf(
            Menu("Sourdough Loaf", "Roti fermentasi alami yang renyah di luar, lembut di dalam.", "Rp 35.000", R.drawable.sourdough),
            Menu("Nasi Goreng Spesial", "Nasi goreng dengan bumbu rahasia dan telur mata sapi.", "Rp 25.000", R.drawable.nasigoreng),
            Menu("Nasi Goreng Seafood", "Nasi goreng dengan campuran udang dan cumi segar.", "Rp 35.000", R.drawable.nasigorengseafood),
            Menu("Spaghetti Bolognaise", "Pasta spaghetti dengan saus daging sapi dan keju.", "Rp 30.000", R.drawable.spaghetti)
        )

        // --- DATA MINUMAN ---
        val listMinuman = listOf(
            Menu("Kopi Susu Gula Aren", "Espresso, susu segar, dan manisnya gula aren asli.", "Rp 22.000", R.drawable.kopisusugulaaren),
            Menu("Cappuccino", "Espresso dengan foam susu lembut dan taburan bubuk kakao.", "Rp 25.000", R.drawable.cappuccino),
            Menu("Matcha Latte", "Matcha premium Jepang dipadukan dengan susu segar.", "Rp 27.000", R.drawable.matcha),
            Menu("Thai Tea", "Teh hitam khas Thailand dengan susu kental manis.", "Rp 22.000", R.drawable.thaitea),
            Menu("Espresso", "Kopi hitam pekat murni dari biji kopi pilihan.", "Rp 18.000", R.drawable.espresso)
        )

        // --- DATA DESSERT ---
        val listDessert = listOf(
            Menu("Donat Cokelat", "Donat empuk dengan lapisan glaze cokelat manis.", "Rp 12.000", R.drawable.donat),
            Menu("Ice Cream Vanilla", "Es krim vanilla lembut dengan topping cherry.", "Rp 20.000", R.drawable.icecream)
        )


        // --- SETUP RECYCLERVIEW ---
        val rvMakanan = findViewById<RecyclerView>(R.id.rvMakanan)
        rvMakanan.layoutManager = LinearLayoutManager(this)
        rvMakanan.adapter = MenuAdapter(listMakanan) { menu -> tambahKeKeranjang(menu) }
        rvMakanan.isNestedScrollingEnabled = false

        val rvMinuman = findViewById<RecyclerView>(R.id.rvMinuman)
        rvMinuman.layoutManager = LinearLayoutManager(this)
        rvMinuman.adapter = MenuAdapter(listMinuman) { menu -> tambahKeKeranjang(menu) }
        rvMinuman.isNestedScrollingEnabled = false

        val rvDessert = findViewById<RecyclerView>(R.id.rvDessert)
        rvDessert.layoutManager = LinearLayoutManager(this)
        rvDessert.adapter = MenuAdapter(listDessert) { menu -> tambahKeKeranjang(menu) }
        rvDessert.isNestedScrollingEnabled = false

        // --- NAVIGASI BAWAH ---
        val navOrder = findViewById<TextView>(R.id.navOrder)
        val navProfile = findViewById<TextView>(R.id.navProfile)

        navOrder.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))
        }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun tambahKeKeranjang(menu: Menu) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_tambah_pesanan, null)
        val builder = android.app.AlertDialog.Builder(this).setView(dialogView)

        val tvJudul = dialogView.findViewById<TextView>(R.id.tvJudulDialog)
        val tvHarga = dialogView.findViewById<TextView>(R.id.tvHargaDialog)
        val etJumlah = dialogView.findViewById<android.widget.EditText>(R.id.etJumlah)
        val btnMinus = dialogView.findViewById<android.widget.Button>(R.id.btnMinus)
        val btnPlus = dialogView.findViewById<android.widget.Button>(R.id.btnPlus)

        tvJudul.text = menu.nama
        tvHarga.text = menu.harga

        btnMinus.setOnClickListener {
            var jml = etJumlah.text.toString().toIntOrNull() ?: 1
            if (jml > 1) {
                jml--
                etJumlah.setText(jml.toString())
            }
        }

        btnPlus.setOnClickListener {
            var jml = etJumlah.text.toString().toIntOrNull() ?: 1
            jml++
            etJumlah.setText(jml.toString())
        }

        builder.setPositiveButton("Tambahkan") { _, _ ->
            val jumlahPesan = etJumlah.text.toString().toIntOrNull() ?: 1
            val hargaSatuan = menu.harga.replace("Rp ", "").replace(".", "").toIntOrNull() ?: 0
            val totalHarga = hargaSatuan * jumlahPesan

            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val keranjangLama = sharedPreferences.getStringSet("KERANJANG", mutableSetOf()) ?: mutableSetOf()

            val keranjangBaru = HashSet(keranjangLama)
            keranjangBaru.add("${menu.nama}|$jumlahPesan|$totalHarga")

            sharedPreferences.edit().putStringSet("KERANJANG", keranjangBaru).apply()
            Toast.makeText(this, "$jumlahPesan ${menu.nama} ditambahkan!", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}