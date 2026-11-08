package lat.pam.kiyoponkopinyemil

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var imgAvatar: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imgAvatar.setImageURI(uri)
            Toast.makeText(this, "Foto berhasil diubah!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val tvHalo = findViewById<TextView>(R.id.tvHaloProfile)
        val etName = findViewById<EditText>(R.id.etProfileName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etTelepon = findViewById<EditText>(R.id.etTelepon)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)

        val btnSave = findViewById<Button>(R.id.btnSaveProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        imgAvatar = findViewById(R.id.imgAvatar)

        val namaUser = sharedPreferences.getString("NAMA_USER", intent.getStringExtra("NAMA_USER") ?: "Pengunjung") ?: "Pengunjung"
        val emailUser = sharedPreferences.getString("EMAIL_USER", intent.getStringExtra("EMAIL_USER") ?: "") ?: ""
        val teleponUser = sharedPreferences.getString("TELEPON_USER", intent.getStringExtra("TELEPON_USER") ?: "") ?: ""
        val alamatUser = sharedPreferences.getString("ALAMAT_USER", intent.getStringExtra("ALAMAT_USER") ?: "") ?: ""

        tvHalo.text = "Halo $namaUser,"
        etName.setText(namaUser)
        etEmail.setText(emailUser)
        etTelepon.setText(teleponUser)
        etAlamat.setText(alamatUser)

        imgAvatar.setOnClickListener {
            pickImage.launch("image/*")
        }

        btnSave.setOnClickListener {
            val newName = etName.text.toString()
            val newEmail = etEmail.text.toString()
            val newTelepon = etTelepon.text.toString()
            val newAlamat = etAlamat.text.toString()

            val editor = sharedPreferences.edit()
            editor.putString("NAMA_USER", newName)
            editor.putString("EMAIL_USER", newEmail)
            editor.putString("TELEPON_USER", newTelepon)
            editor.putString("ALAMAT_USER", newAlamat)
            editor.apply()

            Toast.makeText(this, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()

            val intentHome = Intent(this, HomeActivity::class.java)
            intentHome.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intentHome)
            finish()
        }

        btnLogout.setOnClickListener {

            sharedPreferences.edit().clear().apply()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}