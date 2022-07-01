package app.briota.sia.Front_End.UI

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ReportModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.report_toolbar.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.back_tol
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReportActivity : AppCompatActivity() {

    val now: LocalDateTime = LocalDateTime.now()
    var zone: String? = null
    var dairy: String? = null

    var from_date: String? = null
    var to_date: String? = null
    var from_date1: String? = null
    var to_date1: String? = null
    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    var output = formatter.format(now)
    var output1 = formatter.format(now.minusMonths(1))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_report)



        from_date = intent.getStringExtra("start")
        to_date = intent.getStringExtra("end")
        from_date1 = intent.getStringExtra("start1")
        to_date1 = intent.getStringExtra("end1")
        Log.d(TAG, "onCreate: " + from_date)
        Log.d(TAG, "onCreate: " + to_date)


        initView()

        onClick()


    }


    fun initView() {

//        txtTitle.setText(resources.getString(R.string.Reports))
//
//        txtTitle1.visibility = View.INVISIBLE
//
//        img_cal.visibility = View.VISIBLE

    }


    fun onClick() {


        back_tol.setOnClickListener {
            val intent = Intent(this, PatientProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_cal.setOnClickListener {
            val intent = Intent(this, SelectDateForReportActivity::class.java)
            startActivity(intent)

        }

        greenzoneupdate.setOnClickListener {
            val intent = Intent(this, ReportResultDetailActivity::class.java)
            zone = "green"
            if (from_date1 == null) {
                from_date = now.minusMonths(1).toString()
            }

            if (to_date1 == null) {
                to_date = now.toString()
            }
            intent.putExtra("zone", zone)
            intent.putExtra("Start", from_date)
            intent.putExtra("End", to_date)
            intent.putExtra("Start1", from_date1)
            intent.putExtra("End1", to_date1)
            Log.d(TAG, "onClick: " + from_date1)
            print(from_date1)
            Log.d(TAG, "onClick: " + to_date1)
            print(to_date1)

            startActivity(intent)


        }

        yellowzoneupdate.setOnClickListener {

            val intent = Intent(this, ReportResultDetailActivity::class.java)

            zone = "yellow"
            if (from_date1 == null) {
                from_date = now.minusMonths(1).toString()
            }

            if (to_date1 == null) {
                to_date = now.toString()
            }
            intent.putExtra("zone", zone)
            intent.putExtra("Start", from_date)
            intent.putExtra("End", to_date)
            intent.putExtra("Start1", from_date1)
            intent.putExtra("End1", to_date1)

            startActivity(intent)
        }

        redzoneupdate.setOnClickListener {
            val intent = Intent(this, ReportResultDetailActivity::class.java)
            zone = "red"
            if (from_date1 == null) {
                from_date = now.minusMonths(1).toString()
            }

            if (to_date1 == null) {
                to_date = now.toString()
            }
            intent.putExtra("zone", zone)
            intent.putExtra("Start", from_date)
            intent.putExtra("End", to_date)
            intent.putExtra("Start1", from_date1)
            intent.putExtra("End1", to_date1)
            startActivity(intent)
        }

        exerciseupdate.setOnClickListener {

            val intent = Intent(this, ReportDeatailDairyActivity::class.java)
            dairy = "exercise"
            if (from_date1 == null) {
                from_date = now.minusMonths(1).toString()
            }

            if (to_date1 == null) {
                to_date = now.toString()
            }
            intent.putExtra("zone", zone)
            intent.putExtra("Start", from_date)
            intent.putExtra("End", to_date)
            intent.putExtra("dairy", dairy)
            intent.putExtra("Start1", from_date1)
            intent.putExtra("End1", to_date1)

            startActivity(intent)
        }

        dietupdate.setOnClickListener {

            val intent = Intent(this, ReportDeatailDairyActivity::class.java)
            dairy = "diet"
            if (from_date1 == null) {
                from_date = now.minusMonths(1).toString()
            }

            if (to_date1 == null) {
                to_date = now.toString()
            }
            intent.putExtra("zone", zone)
            intent.putExtra("Start", from_date)
            intent.putExtra("End", to_date)
            intent.putExtra("dairy", dairy)
            Log.d(TAG, "onClick: " + from_date1)
            print(from_date1)
            intent.putExtra("Start1", from_date1)
            Log.d(TAG, "onClick: " + to_date1)
            print(to_date1)
            intent.putExtra("End1", to_date1)

            startActivity(intent)
        }

        Medicationupdate.setOnClickListener {

            val intent = Intent(this, ReportDeatailDairyActivity::class.java)
            dairy = "medication"
            if (from_date1 == null) {
                from_date = now.minusMonths(1).toString()
            }

            if (to_date1 == null) {
                to_date = now.toString()
            }
            intent.putExtra("zone", zone)
            intent.putExtra("Start", from_date)
            intent.putExtra("End", to_date)
            intent.putExtra("dairy", dairy)
            intent.putExtra("Start1", from_date1)
            intent.putExtra("End1", to_date1)

            startActivity(intent)
        }


    }


    override fun onResume() {
        super.onResume()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + token)

        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var user_id = uId.getString("user_Id", "")!!.toInt()
        Log.d(TAG, "onResponse: +++++++" + user_id)

        var end_date = now

        Log.d(TAG, "onResume: " + end_date)

        val start_date = now.minusMonths(1)


        Log.d(TAG, "onResume: " + start_date)

        if (from_date1 == null) {
            from_date = now.minusMonths(1).toString()
        }



        if (to_date1 == null) {
            to_date = now.toString()
        }

        if (from_date1 == null) {
            from_to_txt.text = "${resources.getString(R.string.ReportFrom)} ${
                output1!!.substring(
                    0,
                    10
                )
            } ${resources.getString(R.string.To)} ${output!!.substring(0, 10)}"
        } else {
            from_to_txt.text = "${resources.getString(R.string.ReportFrom)} ${
                to_date1!!.substring(
                    0,
                    10
                )
            } ${resources.getString(R.string.To)} ${from_date1!!.substring(0, 10)}"

        }
        getreportDetails(token!!, user_id, from_date.toString(), to_date.toString())

    }


    private fun getreportDetails(
        token: String?,
        user_id: Int,
        start_date: String,
        end_date: String
    ) {

        RetrofitClient.getRetrofitInstance.getReport(
            "Bearer " + token,
            user_id,
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


                    try {

                        var gson = Gson()
                        var json: String = gson.toJson(response.body())


                        Log.d(ContentValues.TAG, "onResponse: " + json)


                        var getReportModel: ReportModel =
                            gson.fromJson(json, ReportModel::class.java)

                        Log.d(ContentValues.TAG, "onResponse: " + getReportModel)


                        setReportData(getReportModel)


                    } catch (t: Throwable) {
                        Log.e(
                            "My App", "" + t

                        )
                    }


                }
            })


    }

    private fun setReportData(reportModel: ReportModel) {

        greenzonecount.text = reportModel.greenZoneCount!!.toString()
        Log.d(TAG, "setReportData: " + reportModel.greenZoneCount)

        greenzonecount.text = reportModel.greenZoneCount!!.toString()
        Log.d(TAG, "setReportData: " + reportModel.greenZoneCount)
        redzonecount.text = reportModel.redZoneCount!!.toString()
        yellowzonecount.text = reportModel.yellowZoneCount!!.toString()



        dietcountfollowed.text = reportModel.diet.followed!!.toString()
        dietcountunfollowed.text = reportModel.diet.unfollowed!!.toString()
        exercisecountfollowed.text = reportModel.exercise.followed!!.toString()
        exercisecountunfolloed.text = reportModel.exercise.unfollowed!!.toString()
        medicationcountfollowed.text = reportModel.medication.followed!!.toString()
        medicationcountunfollowed.text = reportModel.medication.unfollowed!!.toString()

    }

}