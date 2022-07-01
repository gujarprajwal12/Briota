package app.briota.sia.Front_End.UI

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.GetPatientPeakReading
import app.briota.sia.integration_layer.models.User_Detail.PostMedicalPatientPeekReading
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyZoneReadingsPeakFlowMeterActivity : AppCompatActivity() {

    lateinit var buttonpeakflow: Button
    lateinit var Back: TextView
    var editPEF: EditText? = null
    lateinit var txtTitle: TextView
    var editSpO21: EditText? = null
    var editTemperature1: EditText? = null
    var editPulseRate1: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_zone_readings_peak_flow_meter)

        initView()
        onClick()
    }


    private fun initView() {

        buttonpeakflow = findViewById(R.id.buttonpeakflow)
        Back = findViewById(R.id.Back)
        editPEF = findViewById(R.id.editPEF)
        txtTitle = findViewById(R.id.txtTitle)
        editSpO21 = findViewById(R.id.editSpO21)
        editTemperature1 = findViewById(R.id.editTemperature1)
        editPulseRate1 = findViewById(R.id.editPulseRate1)
        txtTitle.text = resources.getString(R.string.my_readings)

    }

    private fun onClick() {


        Back.setOnClickListener {

            var intent = Intent(this, DailyZoneMyReadingsActivity::class.java)
            startActivity(intent)
        }


        buttonpeakflow.setOnClickListener {


            val pef = editPEF!!.text.toString().trim()

            if (pef.isEmpty()) {
                Utility.sharedInstance.showToastError(this, resources.getString(R.string.EnterPEF))
                editPEF!!.requestFocus()
                return@setOnClickListener
            }

            var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
            var myUserId = sh.getString("user_Id", "")!!.toInt()
            Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)


            var sharedPreferences1: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)

            var selectedzone = sharedPreferences1.getString("grenncared", "")

            Log.d(TAG, "onClick: " + selectedzone)


            var Pef = editPEF!!.text.toString().trim()
            var spo2 = editSpO21!!.text.toString().trim()
            var pulse = editPulseRate1!!.text.toString().trim()
            var temperature = editTemperature1!!.text.toString().trim()


            var postMedicalPatienPeakReadin: PostMedicalPatientPeekReading? =
                PostMedicalPatientPeekReading()


            postMedicalPatienPeakReadin!!.dailyZone = selectedzone
            postMedicalPatienPeakReadin.pef = Pef.toIntOrNull()
            postMedicalPatienPeakReadin.fev1 = "".toIntOrNull()
            postMedicalPatienPeakReadin.fvc = "".toIntOrNull()
            postMedicalPatienPeakReadin.pef = Pef.toIntOrNull()
            postMedicalPatienPeakReadin.spo2 = spo2.toIntOrNull()
            postMedicalPatienPeakReadin.pulse = pulse.toIntOrNull()
            postMedicalPatienPeakReadin.temperature = temperature.toIntOrNull()
            postMedicalPatienPeakReadin.patientId = myUserId



            postMedicalPatientPeakReading(postMedicalPatienPeakReadin)

            val intent =
                Intent(this, app.briota.sia.Front_End.UI.DailyZoneMyReadingSaveActivity::class.java)
            startActivity(intent)

        }


    }

    override fun onResume() {
        super.onResume()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + token)

        var sh1 = getSharedPreferences("USER_ID", MODE_PRIVATE)

        var myUserId = sh1.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)


        Integer.parseInt(myUserId.toString())
        getmedicalpeak(token!!, myUserId)


    }

    private fun postMedicalPatientPeakReading(postMedcialPatienPeakReading: PostMedicalPatientPeekReading) {

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + newtoken)

        var gson = Gson()
        var obj = JSONObject()
        var json = gson.toJson(postMedcialPatienPeakReading)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(ContentValues.TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.medicalPatientReadingsprio(
            "Bearer " + newtoken,
            postMedcialPatienPeakReading
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(ContentValues.TAG, "Failed::" + t)

                    Utility.sharedInstance.dismissProgressDialog()
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Log.d(
                        ContentValues.TAG,
                        "++++++++++++++++++++++++++++++++" + response.toString()
                    )

                    if (response.code() == 201) {


                        try {
                            var gson: Gson = Gson()
                            var json: String = gson.toJson(response.body())

                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t
                            )
                        }
                        Utility.sharedInstance.dismissProgressDialog()
                        if (response.body().toString()
                                .equals("you not submit the reading correct.")
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@DailyZoneReadingsPeakFlowMeterActivity,
                                "" + response.body()
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                }
            })

    }

    private fun getmedicalpeak(token: String, userId: Int) {


        RetrofitClient.getRetrofitInstance.getmedicalpeak("Bearer " + token, userId)
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

                            var gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var getPatientPeakReading: GetPatientPeakReading =
                                gson.fromJson(json, GetPatientPeakReading::class.java)

                            Log.d(ContentValues.TAG, "onResponse: +++++" + getPatientPeakReading)


                            setMedicalPeakReading(getPatientPeakReading)

                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

                    } else {
                        Utility.sharedInstance.showToastError(
                            this@DailyZoneReadingsPeakFlowMeterActivity,
                            "Get Peak Reading Info not called"
                        )


                    }
                }
            })


    }


    private fun setMedicalPeakReading(getPatientPeakReading: GetPatientPeakReading) {
        editPEF!!.setText(getPatientPeakReading.data!![0].pef!!)
        editSpO21!!.setText(getPatientPeakReading.data!![0].spo2!!)
        editTemperature1!!.setText(getPatientPeakReading.data!![0].temperature!!)
        editPulseRate1!!.setText(getPatientPeakReading.data!![0].pulse!!)

    }

}