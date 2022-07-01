package app.briota.sia.Front_End.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.briota.sia.R
import kotlinx.android.synthetic.main.activity_find_air_add_device.*
import kotlinx.android.synthetic.main.toolbar.*

class FindAirAddDevice : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_air_add_device)

        initView()
        onClick()
    }


     fun initView() {

         txtTitle.text = resources.getString(R.string.AddDevice)


    }


    fun onClick(){


        back_tol.setOnClickListener {
            val intent = Intent(this, PatientProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        FindAirDeviceBlue.setOnClickListener {

            val intent = Intent(this, FindAirChooseMedication::class.java)
            startActivity(intent)
            finish()

        }


        FindAirDeviceRed.setOnClickListener {

            val intent = Intent(this, FindAirChooseMedication::class.java)
            startActivity(intent)
            finish()

        }

    }
}