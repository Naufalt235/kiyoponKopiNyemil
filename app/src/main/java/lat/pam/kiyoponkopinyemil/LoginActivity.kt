package lat.pam.kiyoponkopinyemil

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val regNama = intent.getStringExtra("REG_NAMA")
        val regEmail = intent.getStringExtra("REG_EMAIL")
        val regAlamat = intent.getStringExtra("REG_ALAMAT")
        val regTelepon = intent.getStringExtra("REG_TELEPON")

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {


                val editor = sharedPreferences.edit()

                val savedNama = sharedPreferences.getString("NAMA_USER", regNama ?: username)
                val savedEmail = sharedPreferences.getString("EMAIL_USER", regEmail ?: "email@example.com")
                val savedAlamat = sharedPreferences.getString("ALAMAT_USER", regAlamat ?: "Jl. Kopi Nikmat No. 123, Jakarta")
                val savedTelepon = sharedPreferences.getString("TELEPON_USER", regTelepon ?: "+62 812 3456 7890")

                editor.putString("NAMA_USER", regNama ?: savedNama)
                editor.putString("EMAIL_USER", regEmail ?: savedEmail)
                editor.putString("ALAMAT_USER", regAlamat ?: savedAlamat)
                editor.putString("TELEPON_USER", regTelepon ?: savedTelepon)
                editor.apply()

                val intentHome = Intent(this, HomeActivity::class.java)
                startActivity(intentHome)
                finish()

            } else {
                Toast.makeText(this, "Username dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}