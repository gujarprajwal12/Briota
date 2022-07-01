package app.briota.sia.Front_End.UI

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import app.briota.sia.R

class DailyZoneMysymptomsActivity : AppCompatActivity() {

    lateinit var Back: TextView

    lateinit var green_card1: CardView
    lateinit var yellow_card1: CardView
    lateinit var red_card1: CardView
    lateinit var txtTitle: TextView

    var selectedzone: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_zone_mysymptoms)


        initView()
        onClick()
    }


    private fun initView() {


        Back = findViewById(R.id.Back)
        green_card1 = findViewById(R.id.green_card1)
        yellow_card1 = findViewById(R.id.yellow_card1)
        red_card1 = findViewById(R.id.red_card1)

        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.my_symptoms)
    }

    private fun onClick() {

        Back.setOnClickListener {

            var intent = Intent(this, MyDailyZoneActivity::class.java)
            startActivity(intent)
        }

        green_card1.setOnClickListener {

            selectedzone = "Green"

            var sharedPreferences1: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)
            var greencared = sharedPreferences1.edit()
            greencared.putString("grenncared", selectedzone)
            greencared.apply()
            greencared.commit()

            val intent = Intent(this, DailyZoneMyReadingsActivity::class.java)

            startActivity(intent)


            //     Utility.sharedInstance.showToastSuccess(this,resources.getString(R.string.selected_green_zone_symptoms))
            var toast = Toast.makeText(
                this,
                resources.getString(R.string.selected_green_zone_symptoms),
                Toast.LENGTH_LONG
            )
            toast.show()

        }

        yellow_card1.setOnClickListener {

            selectedzone = "Yellow"

            var sharedPreferences1: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)
            var yellowcared = sharedPreferences1.edit()
            yellowcared.putString("grenncared", selectedzone)
            yellowcared.apply()
            yellowcared.commit()


            val intent = Intent(this, DailyZoneMyReadingsActivity::class.java)

            startActivity(intent)

            //   Utility.sharedInstance.showToastSuccess(this,resources.getString(R.string.selected_yellow_zone_symptoms))

            var toast = Toast.makeText(
                this,
                resources.getString(R.string.selected_yellow_zone_symptoms),
                Toast.LENGTH_LONG
            )
            toast.show()

        }


        red_card1.setOnClickListener {
            selectedzone = "Red"

            var sharedPreferences1: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)
            var redcared = sharedPreferences1.edit()
            redcared.putString("grenncared", selectedzone)
            redcared.apply()
            redcared.commit()

            val intent = Intent(this, DailyZoneMyReadingsActivity::class.java)

            startActivity(intent)

            //   Utility.sharedInstance.showToastSuccess(this,resources.getString(R.string.selected_red_zone_symptoms))
            var toast = Toast.makeText(
                this,
                resources.getString(R.string.selected_red_zone_symptoms),
                Toast.LENGTH_LONG
            )
            toast.show()

        }
    }
}