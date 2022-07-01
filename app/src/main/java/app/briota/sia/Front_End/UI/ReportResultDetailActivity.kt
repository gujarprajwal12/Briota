package app.briota.sia.Front_End.UI

import android.content.ContentValues
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import app.briota.sia.Front_End.view.adapter.ZoneListAdapter
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.Report.Reportbyzone
import app.briota.sia.integration_layer.models.User_Detail.Report.data
import app.briota.sia.integration_layer.models.User_Detail.ReportModel
import com.briota.sia.integration_layer.models.User_Detail.GetPatientAllDetails.GetPatientAllDetailsResponseModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.activity_report_deatail_dairy.*
import kotlinx.android.synthetic.main.activity_report_result_detail.*
import kotlinx.android.synthetic.main.report_toolbar.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.back_tol
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReportResultDetailActivity : AppCompatActivity() {

    val now: LocalDateTime = LocalDateTime.now()
    var myzone: String? = null

    var zone_from_date: String? = null
    var zone_to_date: String? = null
    var zone_from_date1: String? = null
    var zone_to_date1: String? = null
    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    var output = formatter.format(now)
    var output1 = formatter.format(now.minusMonths(1))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_result_detail)


        myzone = intent.getStringExtra("zone")
        zone_from_date = intent.getStringExtra("Start")
        zone_to_date = intent.getStringExtra("End")
        zone_from_date1 = intent.getStringExtra("Start1")
        zone_to_date1 = intent.getStringExtra("End1")


        initView()

        onClick()

    }


    fun initView() {

        img_cal.visibility = View.INVISIBLE

        if (myzone.equals("yellow") || myzone.equals("Gul")) {
            zone_color.text = resources.getString(R.string.Yellow_Zone)
            total_no_of_time.text = resources.getString(R.string.TotalnooftimeyouareinYellowzone)
        } else if (myzone.equals("red") || myzone.equals("Rød")) {
            zone_color.text = resources.getString(R.string.Red_Zone)
            total_no_of_time.text = resources.getString(R.string.TotalnootimeyouareinRedzone)
        } else {
            zone_color.text = resources.getString(R.string.green_zone)
            total_no_of_time.text = resources.getString(R.string.TotalnootimeyouareinGreenzone)
        }


    }


    fun onClick() {


        back_tol.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
            finish()
        }


    }


    override fun onResume() {
        super.onResume()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + token)

        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var user_id = uId.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + user_id)

        var end_date = now

        Log.d(ContentValues.TAG, "onResume: " + end_date)


        val start_date = now.minusMonths(1)


        Log.d(ContentValues.TAG, "onResume: " + start_date)

        if (zone_from_date1 == null) {
            zone_from_date = now.minusMonths(1).toString()
        }
        if (zone_to_date1 == null) {
            zone_to_date = now.toString()
        }

        if (zone_from_date1 == null && zone_to_date1 == null) {
            zone_report_from.text = "${resources.getString(R.string.ReportFrom)} ${
                output1!!.substring(
                    0,
                    10
                )
            } ${resources.getString(R.string.To)} ${output!!.substring(0, 10)}"
        } else {
            zone_report_from.text = "${resources.getString(R.string.ReportFrom)} ${
                zone_to_date1!!.substring(
                    0,
                    10
                )
            } ${resources.getString(R.string.To)} ${zone_from_date1!!.substring(0, 10)}"
        }
        getreportDetailsbyzone(
            token!!,
            user_id,
            myzone!!,
            zone_from_date.toString(),
            zone_to_date.toString()
        )


    }

    private fun getreportDetailsbyzone(
        token: String?,
        user_id: Int,
        myzone: String,
        start_date: String,
        end_date: String
    ) {

        RetrofitClient.getRetrofitInstance.getReportzonedairy(
            "Bearer " + token,
            user_id,
            myzone,
            start_date,
            end_date
        )


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
                            val json: String = gson.toJson(response.body())


                            Log.d(ContentValues.TAG, "onResponse: " + json)

                            val getreportbyzone: Reportbyzone =
                                gson.fromJson(json, Reportbyzone::class.java)

                            Log.d(ContentValues.TAG, "onResponse: " + getreportbyzone)

                            if (myzone.equals("yellow")) {
                                total_count.text = getreportbyzone.yellowZoneCount.toString()
                                total_count.setTextColor(resources.getColor(R.color.text_yellow))
                            } else if (myzone.equals("red")) {
                                total_count.text = getreportbyzone.redZoneCount.toString()
                                total_count.setTextColor(resources.getColor(R.color.text_red))
                            } else {
                                total_count.text = getreportbyzone.greenZoneCount.toString()
                                total_count.setTextColor(resources.getColor(R.color.text_green))
                            }



                            setReportDatabyzone(getreportbyzone)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@ReportResultDetailActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }

    private fun setReportDatabyzone(reportbyzone: Reportbyzone) {

        nodataforreport.visibility = View.GONE

        val zoneList: ArrayList<data>
        zoneList = reportbyzone.data!!

        if (zoneList.size == 0) {
            nodataforreport.visibility = View.VISIBLE
        } else {

            nodataforreport.visibility = View.GONE
        }

        recycle_by_zone.adapter = ZoneListAdapter(this, zoneList)

        recycle_by_zone.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


    }


}