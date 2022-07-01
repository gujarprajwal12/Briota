package app.briota.sia.Front_End.UI

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.briota.sia.R
import kotlinx.android.synthetic.main.activity_find_air_device_connect_actvity.*
import kotlinx.android.synthetic.main.toolbar.*

class FindAirDeviceConnectActvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_air_device_connect_actvity)

        initView()
        onClick()
    }


    @SuppressLint("ResourceAsColor")
    fun initView() {

        txtTitle.text = resources.getString(R.string.connect)
        toolbackcolour.setBackgroundResource(R.color.lightblue)
    }

    fun onClick() {

        back_tol.setOnClickListener {
            val intent = Intent(this, FindAirChooseMedication::class.java)
            startActivity(intent)
            finish()
        }


        buttonFindAirConnect.setOnClickListener {
            findairconnect1.visibility = View.GONE
            Findairconnectscrren2.visibility = View.VISIBLE
        }


    }
}