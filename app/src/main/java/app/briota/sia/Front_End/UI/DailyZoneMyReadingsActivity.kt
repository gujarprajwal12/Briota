package app.briota.sia.Front_End.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import app.briota.sia.R
import kotlinx.android.synthetic.main.activity_daily_zone_my_readings.*

class DailyZoneMyReadingsActivity : AppCompatActivity() {


    lateinit var peakflowmeter: CardView

    lateinit var spirometer: CardView
    lateinit var Back: TextView

    lateinit var txtTitle: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_zone_my_readings)



        initView()
        onClick()
    }


    private fun initView() {

        peakflowmeter = findViewById(R.id.peakflowmeter)
        spirometer = findViewById(R.id.spirometer)
        Back = findViewById(R.id.Back)

        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.Daily_Zone)
    }

    private fun onClick() {


        Back.setOnClickListener {

            var intent = Intent(this, DailyZoneMysymptomsActivity::class.java)
            startActivity(intent)
        }

        peakflowmeter.setOnClickListener {
            val intent = Intent(this, DailyZoneReadingsPeakFlowMeterActivity::class.java)

            startActivity(intent)
        }

        spirometer.setOnClickListener {
            val intent = Intent(this, DailyZoneReadingSpirometerActivity::class.java)

            startActivity(intent)
        }

        txtSkip.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}