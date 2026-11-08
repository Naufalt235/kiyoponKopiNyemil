package lat.pam.kiyoponkopinyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnToLogin = findViewById<Button>(R.id.btnToLogin)


        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }


        btnToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }
}