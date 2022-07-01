package app.briota.sia.Front_End.UI

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.view.adapter.PeakReadingAdapter
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.GetPatientPeakReading
import app.briota.sia.integration_layer.models.User_Detail.PeakData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_my_daily_zone.*
import kotlinx.android.synthetic.main.peak_reading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MyDailyZoneActivity : AppCompatActivity() {


    lateinit var butmydailyzone: Button
    lateinit var Back: TextView
    lateinit var calender: CalendarView
    lateinit var currentdate: TextView
    lateinit var txtTitle: TextView
    lateinit var peakRecyclerview: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_daily_zone)

        initView()
        onClick()
    }

    fun initView() {


        butmydailyzone = findViewById(R.id.butmydailyzone)
        Back = findViewById(R.id.Back)
        calender = findViewById(R.id.calender)
        currentdate = findViewById(R.id.currentdate)

        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.Daily_Zone)
        peakRecyclerview = findViewById(R.id.peakRecyclerview)
    }

    fun onClick() {

        Back.setOnClickListener {

            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        butmydailyzone.setOnClickListener {

            var intent = Intent(this, DailyZoneMysymptomsActivity::class.java)

            startActivity(intent)
        }


        calender.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {

            }
        })


    }

    override fun onResume() {
        super.onResume()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var mytoken = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + mytoken)

        var sh5 = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myuserId = sh5.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + myuserId)

        getSpiroReading(mytoken!!, myuserId)
    }

    private fun getSpiroReading(token: String, userId: Int) {


        RetrofitClient.getRetrofitInstance.getSpiroReadingAPI("Bearer " + token, userId)
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {


                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {

                    if (response.code() == 200) {

                        Log.d(ContentValues.TAG, "onResponse: +++++++" + response.body())


                        try {

                            val gson = Gson()
                            var json: String = gson.toJson(response.body())


                            var getPatientPeakReading: GetPatientPeakReading =
                                gson.fromJson(json, GetPatientPeakReading::class.java)

                            Log.d(TAG, "onResponse: " + json)
                            Log.d(TAG, "onResponse: " + getPatientPeakReading)

                            setReading(getPatientPeakReading)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

                    } else {


                    }
                }
            })


    }

    private fun setReading(patientPeakReading: GetPatientPeakReading) {

        val peakData: ArrayList<PeakData>
        peakData = patientPeakReading.data!!


        Log.d(TAG, "setReading: " + peakData.size)
        Log.d(TAG, "setReading: " + peakData)

        if (peakData.size == 0) {
            peakRecyclerview.visibility = View.GONE
            emptyTxt.visibility = View.VISIBLE
        } else {
            emptyTxt.visibility = View.GONE
            peakRecyclerview.adapter = PeakReadingAdapter(this, peakData)

            peakRecyclerview.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }


}