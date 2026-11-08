package lat.pam.kiyoponkopinyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNama = findViewById<EditText>(R.id.etNamaLengkap)
        val etEmail = findViewById<EditText>(R.id.etEmailReg)
        val etAlamat = findViewById<EditText>(R.id.etAlamatReg)
        val etTelepon = findViewById<EditText>(R.id.etTeleponReg)
        val btnRegister = findViewById<Button>(R.id.btnRegisterSubmit)

        btnRegister.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("REG_NAMA", etNama.text.toString())
            intent.putExtra("REG_EMAIL", etEmail.text.toString())
            intent.putExtra("REG_ALAMAT", etAlamat.text.toString())
            intent.putExtra("REG_TELEPON", etTelepon.text.toString())

            Toast.makeText(this, "Registrasi Berhasil! Silakan Login", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
    }
}