package app.briota.sia.Front_End.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import app.briota.sia.R

class PasswordResetSuccessful : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset_successful)


        val Backtosignin = findViewById<Button>(R.id.btnBacktoSignin)

        Backtosignin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)

            startActivity(intent)
        }
    }
}
