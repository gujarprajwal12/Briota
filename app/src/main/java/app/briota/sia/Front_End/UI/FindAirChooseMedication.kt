package app.briota.sia.Front_End.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.briota.sia.R
import kotlinx.android.synthetic.main.activity_find_air_choose_medication.*
import kotlinx.android.synthetic.main.toolbar.*

class FindAirChooseMedication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_air_choose_medication)

        initView()
        onClick()

    }

    fun initView() {

        txtTitle.text = resources.getString(R.string.AddDevice)
        findairmedicationlinear.visibility = View.VISIBLE

    }

    fun onClick() {

        back_tol.setOnClickListener {
            val intent = Intent(this, FindAirAddDevice::class.java)
            startActivity(intent)
            finish()
        }




        buttonFindAirMedication.setOnClickListener {

            findairmedicationlinear.visibility = View.GONE
            findairscrren1.visibility = View.VISIBLE
            findairscreen2.visibility = View.GONE

        }


        buttonFindAirstep1.setOnClickListener {

            findairmedicationlinear.visibility = View.GONE
            findairscrren1.visibility = View.GONE
            findairscreen2.visibility = View.VISIBLE
        }

        buttonFindAirstep2.setOnClickListener {

            val intent = Intent(this, FindAirDeviceConnectActvity::class.java)
            startActivity(intent)
            finish()

        }

    }




}