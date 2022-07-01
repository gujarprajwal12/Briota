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
import com.google.gson.Gson

import app.briota.sia.integration_layer.models.User_Detail.PostMedcialPatienSpiroReading

import org.json.JSONObject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyZoneReadingSpirometerActivity : AppCompatActivity() {

    lateinit var buttonspirometer: Button
    lateinit var Back: TextView
    var editFEV1s: TextView? = null
    lateinit var txtTitle: TextView
    var editFVCs: EditText? = null
    var editSpO2s: EditText? = null
    var editTemperatures: EditText? = null
    var editPulseRates: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_zone_reading_spirometer)


        initView()
        onClick()
    }


    private fun initView() {

        buttonspirometer = findViewById(R.id.buttonspirometer)
        Back = findViewById(R.id.Back)
        editFEV1s = findViewById(R.id.editFEV1s)
        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.my_readings)
        editFVCs = findViewById(R.id.editFVCs)
        editPulseRates = findViewById(R.id.editPulseRates)
        editSpO2s = findViewById(R.id.editSpO2s)
        editTemperatures = findViewById(R.id.editTemperatures)
    }

    private fun onClick() {


        Back.setOnClickListener {

            var intent = Intent(this, DailyZoneMyReadingsActivity::class.java)
            startActivity(intent)
        }

        buttonspirometer.setOnClickListener {


            val Fev1 = editFEV1s!!.text.toString().trim()

            if (Fev1.isEmpty()) {
                Utility.sharedInstance.showToastError(this, resources.getString(R.string.ENTERFEV1))
                editFEV1s!!.requestFocus()
                return@setOnClickListener
            }

            var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
            var myUserId = sh.getString("user_Id", "")!!.toInt()
            Log.d(TAG, "onResponse: +++++++" + myUserId)

            var sharedPreferences1: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)

            var selectedzone = sharedPreferences1.getString("grenncared", "")

            Log.d(TAG, "onClick: " + selectedzone)

            var fev1 = editFEV1s!!.text.toString()
            var fev = editFVCs!!.text.toString().trim()
            var spo2 = editSpO2s!!.text.toString()
            var pulse = editPulseRates!!.text.toString()
            var temperature = editTemperatures!!.text.toString()


            var postMedicalPatienSpiroReadin: PostMedcialPatienSpiroReading? =
                PostMedcialPatienSpiroReading()


            postMedicalPatienSpiroReadin!!.dailyZone = selectedzone
            postMedicalPatienSpiroReadin.fev1 = fev1.toIntOrNull()
            postMedicalPatienSpiroReadin.fvc = fev.toIntOrNull()
            postMedicalPatienSpiroReadin.spo2 = spo2.toIntOrNull()
            postMedicalPatienSpiroReadin.pulse = pulse.toIntOrNull()
            postMedicalPatienSpiroReadin.temperature = temperature.toIntOrNull()
            postMedicalPatienSpiroReadin.patientId = myUserId


            postMedicalPatientPeakReading(postMedicalPatienSpiroReadin)

            var intent =
                Intent(this, app.briota.sia.Front_End.UI.DailyZoneMyReadingSaveActivity::class.java)
            startActivity(intent)


        }

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

    private fun postMedicalPatientPeakReading(postMedcialPatienSpiroReading: PostMedcialPatienSpiroReading) {

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + newtoken)

        var gson = Gson()
        var obj = JSONObject()
        var json = gson.toJson(postMedcialPatienSpiroReading)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(ContentValues.TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.medicalPatientReading(
            "Bearer " + newtoken,
            postMedcialPatienSpiroReading
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
                                this@DailyZoneReadingSpirometerActivity,
                                "" + response.body()
                            )
                        }
                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                }
            })

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
}