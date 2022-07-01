package app.briota.sia.Front_End.UI

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.briota.sia.R
import kotlinx.android.synthetic.main.activity_my_daily_zone.*
import kotlinx.android.synthetic.main.activity_select_date_for_report.*
import kotlinx.android.synthetic.main.report_toolbar.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.back_tol
import java.util.*

class SelectDateForReportActivity : AppCompatActivity() {

    lateinit var start_Date: String
    lateinit var end_Date: String
    lateinit var start_Date1: String
    lateinit var end_Date1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_date_for_report)

        initView()

        onClick()

    }


    fun initView() {

        img_cal.visibility = View.INVISIBLE
        txt_Report_title.text = resources.getString(R.string.SelectDate)
    }


    fun onClick() {

        back_tol.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
            finish()
        }

//        val otl1 = View.OnTouchListener { v, event ->
//            // the listener has consumed the event
//            true
//        }
//
//        fromdate.setOnTouchListener(otl1)


        fromdate.setOnClickListener {


            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                R.style.MyDatePickerStyle,
                DatePickerDialog.OnDateSetListener {

                        view, year, monthOfYear, dayOfMonth ->

                    val month = monthOfYear + 1
                    var formattedMonth = "" + month
                    var formattedDayOfMonth = "" + dayOfMonth
                    if (month < 10) {
                        formattedMonth = "0$month"
                    }
                    if (dayOfMonth < 10) {
                        formattedDayOfMonth = "0$dayOfMonth"
                    }
                    fromdate.setText("$year-$formattedMonth-$formattedDayOfMonth")
                    start_Date = "$year-$formattedMonth-$formattedDayOfMonth"
                    start_Date1 = "$formattedDayOfMonth-$formattedMonth-$year"
                    //  fromDate = from_date.text.toString().trim()

                },
                year,
                month,
                day
            )

            cal.add(Calendar.YEAR, -26)
            datePickerDialog.datePicker.minDate = cal.timeInMillis
            cal.add(Calendar.YEAR, 26)
            datePickerDialog.datePicker.maxDate = cal.timeInMillis

            datePickerDialog.show()


        }




        todate.setOnClickListener {


            val cal = Calendar.getInstance()

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            // cal.set(year, month, day + 1)

            val datePickerDialog = DatePickerDialog(
                this,
                R.style.MyDatePickerStyle,
                DatePickerDialog.OnDateSetListener {


                        view, year, monthOfYear, dayOfMonth ->
                    val month = monthOfYear + 1
                    var formattedMonth = "" + month
                    var formattedDayOfMonth = "" + dayOfMonth
                    if (month < 10) {
                        formattedMonth = "0$month"
                    }
                    if (dayOfMonth < 10) {
                        formattedDayOfMonth = "0$dayOfMonth"
                    }
                    todate.setText("$year-$formattedMonth-$formattedDayOfMonth")
                    end_Date = "$year-$formattedMonth-$formattedDayOfMonth"
                    end_Date1 = "$formattedDayOfMonth-$formattedMonth-$year"
                    //  toDate = to_date.text.toString().trim()

                },
                year,
                month,
                day
            )



            datePickerDialog.datePicker.minDate = cal.timeInMillis

            cal.add(Calendar.YEAR, -26)
            datePickerDialog.datePicker.minDate = cal.timeInMillis
            cal.add(Calendar.YEAR, 26)
            datePickerDialog.datePicker.maxDate = cal.timeInMillis
            datePickerDialog.show()
        }

        btnGeneratereport.setOnClickListener {


            if (start_Date > end_Date) {

                Utility.sharedInstance.showToastError(
                    this@SelectDateForReportActivity,
                    resources.getString(R.string.dategreatertahnfrom)
                )

            } else {
                val intent = Intent(this, ReportActivity::class.java)
                intent.putExtra("start", start_Date + "T00:00:01.000Z")
                intent.putExtra("end", end_Date + "T23:59:59.704Z")
                intent.putExtra("start1", start_Date1)
                intent.putExtra("end1", end_Date1)

                startActivity(intent)
                finish()
            }
        }


    }
}