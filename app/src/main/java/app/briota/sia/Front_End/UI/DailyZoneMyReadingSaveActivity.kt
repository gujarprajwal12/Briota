package app.briota.sia.Front_End.UI

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import app.briota.sia.R
import kotlinx.android.synthetic.main.activity_daily_zone_my_reading_save.*

class DailyZoneMyReadingSaveActivity : AppCompatActivity() {

    lateinit var Back: TextView
    lateinit var txtTitle: TextView
    lateinit var txt_Language: TextView
    lateinit var homedailyzone: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_zone_my_reading_save)

        intView()
        onClick()


    }


    fun intView() {
        Back = findViewById(R.id.Back)
        homedailyzone = findViewById(R.id.homedailyzone)
        txt_Language = findViewById(R.id.txt_Language)
        txt_Language.text = resources.getString(R.string.Daily_Zone)


    }

    fun onClick() {


        Back.setOnClickListener {

            var intent =
                Intent(this, app.briota.sia.Front_End.UI.DailyZoneMyReadingsActivity::class.java)
            startActivity(intent)
        }


        homedailyzone.setOnClickListener {

            var intent = Intent(this, app.briota.sia.Front_End.UI.HomeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        var sharedPreferences1: SharedPreferences =
            this.getSharedPreferences("Briota", MODE_PRIVATE)

        var selectedzone = sharedPreferences1.getString("grenncared", "")

        if (selectedzone.equals("Green")) {

            zonetext.text = resources.getString(R.string.YouhavechosenGreenZone)
            zoneimage.setImageResource(R.drawable.greensymextrasmall)

        }
        if (selectedzone.equals("Yellow")) {

            zonetext.text = resources.getString(R.string.YouhavechosenYellowzone)
            zoneimage.setImageResource(R.drawable.yellowextrasmall)

        }
        if (selectedzone.equals("Red")) {

            zonetext.text = resources.getString(R.string.YouhavechosenRedzone)
            zoneimage.setImageResource(R.drawable.redsymextrasmall)

        }


    }

}