package app.briota.sia.Front_End.UI


import android.app.Dialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.GetDailyDairyModels.GetDailyDairyData
import app.briota.sia.integration_layer.models.User_Detail.PostDailyDairy
import app.briota.sia.integration_layer.models.User_Detail.PutDailyDairyModel
import app.briota.sia.integration_layer.models.User_Detail.SignUpResponseModel
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_daily_dairy.*
import kotlinx.android.synthetic.main.daily_dairy_toolbar.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


class DailyDairyActivity : AppCompatActivity() {

    var dialog: Dialog? = null
    var compactCalendar: CompactCalendarView? = null
    private val dateFormatMonth: SimpleDateFormat =
        SimpleDateFormat("MMMM- yyyy", Locale.getDefault())


    var Medication: Boolean? = null

    var Exercise: Boolean? = null

    var Diet: Boolean? = null

    val now: LocalDateTime = LocalDateTime.now()
    var start_date: String? = null
    var end_date: String? = null
    var start_date_for_post: String? = null
    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    var output = formatter.format(now)

    var formatter1 = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
    var output1 = formatter1.format(now)

    var clicked_Date : Date? = null
    var current_Date : Date? = null
    var date : String? = null

    private val dateFormatForMonth = SimpleDateFormat("MMM - yyyy", Locale.getDefault())

   var  eventtimeinmillsecond: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_dairy)

        initView()

        onClick()
    }

    fun initView() {
        diary_title.text = resources.getString(R.string.Dailydiary)



        compactCalendar = findViewById<View>(R.id.compactcalendar_view) as CompactCalendarView
        compactCalendar!!.setUseThreeLetterAbbreviation(true)
        compactCalendar!!.showCalendar()

        txtMonth.setText(dateFormatForMonth.format(compactCalendar!!.getFirstDayOfCurrentMonth()))

    }



    fun onClick() {
        back_tolayout.setOnClickListener {
            val intent = Intent(this,PatientProfileActivity::class.java)
            startActivity(intent)
            finish()
        }



        compactCalendar!!.setListener(object : CompactCalendarView.CompactCalendarViewListener {



            override fun onDayClick(dateClicked: Date) {




                clicked_Date = dateClicked


                val myDate = Date()
                val calendar = Calendar.getInstance()
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
                calendar.setTime(myDate);
                current_Date = myDate
                Log.d(TAG, "onDayClick: "+current_Date)

                // EEE MMM dd HH:mm:ss zzzz yyyy

                date = "$clicked_Date"
                var spf = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH)
                val newDate = spf.parse(date)
                spf = SimpleDateFormat("yyyy-MM-dd")
                date = spf.format(newDate)
                println(date)


                if(clicked_Date!! < current_Date)
                {
                    btnUpdateyourdailydairy.visibility = View.VISIBLE
                    lay_DailyDairy.visibility = View.VISIBLE
                    lay_Futuretxt.visibility = View.GONE
                }
                else
                {
                    btnUpdateyourdailydairy.visibility = View.GONE
                    lay_DailyDairy.visibility = View.GONE
                    lay_Futuretxt.visibility = View.VISIBLE
                }



                start_date =date+"T00:00:01.000Z"
                Log.d(TAG, "onDayClick: "+start_date)
                print(start_date)

                end_date =date+"T23:59:59.704Z"

                start_date_for_post = date

//                start_date =
//                    dateClicked.toInstant().atZone(ZoneOffset.UTC).plusMinutes(330).toString()
//                        .substring(0, 10) + "T00:00:01.000Z"



//
//                end_date =
//                    dateClicked.toInstant().atZone(ZoneOffset.UTC).plusMinutes(330).toString()
//                        .substring(0, 10) + "T23:59:59.704Z"


//                start_date_for_post =
//                    dateClicked.toInstant().atZone(ZoneOffset.UTC).plusMinutes(330).toString()
//                        .substring(0, 10)
                Log.d(TAG, "onDayClick: " + start_date_for_post)
                print(start_date_for_post)
                print(start_date_for_post)

                   // event method call
                eventtimeinmillsecond =
                    dateClicked.toInstant().atZone(ZoneOffset.UTC).plusMinutes(330).toEpochSecond().toString()+"000"


                Log.d(TAG, "onDayClick: " + date)

                Log.d(TAG, "onDayClick: " + date)



                var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                var token = sh.getString("bearer_token", "")
                Log.d(TAG, "onResume: ++++" + token)

                var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
                var myUserId = uId.getString("user_Id", "")!!.toInt()
                Log.d(TAG, "onResponse: +++++++" + myUserId)


                getDailyDairyApi(token!!, myUserId, start_date!!, end_date!!)

                var dateclick = dateClicked.toLocaleString()


                datefordailydiary.text = dateclick.toString().dropLast(11)

                var sharedPreferences: SharedPreferences =
                    this@DailyDairyActivity.getSharedPreferences("Briota", MODE_PRIVATE)
                var clickdate = sharedPreferences.edit()
                clickdate.putString("dateformat", dateclick)
                clickdate.putString("dateclick", start_date)
                clickdate.putString("dateclickend", end_date)
                clickdate.apply()




            }


            override fun onMonthScroll(firstDayOfNewMonth: Date?) {


                txtMonth.text = dateFormatMonth.format(firstDayOfNewMonth)

            }
        })

        datefordailydiary.text = output1.toString().substring(0, 11)



        btnUpdateyourdailydairy.setOnClickListener {


            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(true)
            dialog!!.setContentView(R.layout.updateyourdialydiary)
            dialog!!.show()



            var timefordairyupdaute : TextView = dialog!!.findViewById(R.id.timefordairyupdaute)




            var sh = getSharedPreferences("Briota", MODE_PRIVATE)
            var clickdate = sh.getString("dateformat", "")

            if (clickdate.equals("")) {

                timefordairyupdaute.text = output.toString().substring(0, 10)

            } else {

                timefordairyupdaute.text = clickdate.toString().dropLast(11)

                var sharedPreferences: SharedPreferences =
                    this@DailyDairyActivity.getSharedPreferences("Briota", MODE_PRIVATE)
                var clickdate = sharedPreferences.edit()
                clickdate.putString("dateformat", "")
                clickdate.apply()

            }
            var dietbtnno: Button = dialog!!.findViewById(R.id.dietbtnno)
            var dietbtnyes: Button = dialog!!.findViewById(R.id.dietbtnyes)
            var exercisebtnyes: Button = dialog!!.findViewById(R.id.exercisebtnyes)
            var exercisebtnno: Button = dialog!!.findViewById(R.id.exercisebtnno)
            var medicationsbtnyes: Button = dialog!!.findViewById(R.id.medicationsbtnyes)
            var medicationsbtnno: Button = dialog!!.findViewById(R.id.medicationsbtnno)

            var saveupdatemedication: Button = dialog!!.findViewById(R.id.saveupdatemedication)

            dietbtnno.setOnClickListener {

                Diet = false
                dietbtnno.setBackgroundResource(R.drawable.btn_no)
                dietbtnyes.setBackgroundResource(R.drawable.whitebutton)

                saveupdatemedication.isEnabled = true
            }

            dietbtnyes.setOnClickListener {

                Diet = true

                dietbtnno.setBackgroundResource(R.drawable.whitebuttonredborder)
                dietbtnyes.setBackgroundResource(R.drawable.btn_yes)
                saveupdatemedication.isEnabled = true

            }

            exercisebtnno.setOnClickListener {

                Exercise = false
                exercisebtnno.setBackgroundResource(R.drawable.btn_no)
                exercisebtnyes.setBackgroundResource(R.drawable.whitebutton)
                saveupdatemedication.isEnabled = true
            }

            exercisebtnyes.setOnClickListener {

                Exercise = true

                exercisebtnno.setBackgroundResource(R.drawable.whitebuttonredborder)
                exercisebtnyes.setBackgroundResource(R.drawable.btn_yes)
                saveupdatemedication.isEnabled = true

            }


            medicationsbtnno.setOnClickListener {

                Medication = false
                medicationsbtnno.setBackgroundResource(R.drawable.btn_no)
                medicationsbtnyes.setBackgroundResource(R.drawable.whitebutton)
                saveupdatemedication.isEnabled = true
            }

            medicationsbtnyes.setOnClickListener {

                Medication = true
                medicationsbtnno.setBackgroundResource(R.drawable.whitebuttonredborder)
                medicationsbtnyes.setBackgroundResource(R.drawable.btn_yes)
                saveupdatemedication.isEnabled = true

            }





            saveupdatemedication.setOnClickListener {

                val myDate = Date()
                val calendar = Calendar.getInstance()
                calendar.time = myDate
                val time = calendar.time
                val outputFmt = SimpleDateFormat("'T'HH:mm:ss")
                val dateAsString = outputFmt.format(time)
                print(dateAsString)
                Log.d(TAG, "onDayClick: " + dateAsString)
                print(dateAsString)

                var fromDate : String
                if(date != null) {
                    fromDate = "$date$dateAsString"
                }
                else
                {
                    fromDate =  now.toString().substring(0, 10) + "T00:00:01.704"
                }

                var postDailyDairy: PostDailyDairy? = PostDailyDairy()


                var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
                var myUserId = sh.getString("user_Id", "")
                Log.d(TAG, "onResponse: +++++++" + myUserId)


                postDailyDairy!!.patient_id = myUserId!!.toIntOrNull()
                postDailyDairy.medication = Medication
                postDailyDairy.exercise = Exercise
                postDailyDairy.diet = Diet

                postDailyDairy.curr_date = now.toString().substring(0, 10) + "T23:59:59.704"

                postDailyDairy.added_date = fromDate


                postDailyDairyAPI(postDailyDairy)


                dialog!!.dismiss()

                dialog = Dialog(this)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setCancelable(true)
                dialog!!.setContentView(R.layout.sucess_dialog_reminder_dailydieary)
                dialog!!.show()

                var btnokdilaydiary: Button = dialog!!.findViewById(R.id.btnokdilaydiary)

                btnokdilaydiary.setOnClickListener {
                    var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                    var token = sh.getString("bearer_token", "")
                    Log.d(ContentValues.TAG, "onResume: ++++" + token)

                    var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
                    var myUserId = uId.getString("user_Id", "")!!.toInt()
                    Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)

                    Log.d(TAG, "onResume: " + end_date)

                    Log.d(TAG, "onResume: " + start_date)

                    getDailyDairyApi(token!!, myUserId, start_date!!, end_date!!)

                    dialog!!.dismiss()
                }

            }


        }
    }


    private fun postDailyDairyAPI(postDailyDairy: PostDailyDairy) {

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + newtoken)

        var gson = Gson()
        var obj = JSONObject()
        var json = gson.toJson(postDailyDairy)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(ContentValues.TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.DailyDairyPostApi("Bearer " + newtoken, postDailyDairy)
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
                    Log.d(ContentValues.TAG, "++++++++++" + SignUpResponseModel())
                    if (response.code() == 201) {


                        try {


                            var gson: Gson = Gson()
                            var json: String = gson.toJson(response.body())

                            Log.d(TAG, "onResponse: " + gson)
                            Log.d(TAG, "onResponse: " + json)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t
                            )
                        }
                        Utility.sharedInstance.dismissProgressDialog()
                        if (response.body().toString()
                                .equals("update the daily diary.")
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@DailyDairyActivity,
                                "" + response.body()
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                    if (response.code() == 400) {

                        editDailyDairyInfo()
                    }
                }
            })

    }


    private fun editDailyDairyInfo() {


        var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = sh.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)

        var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh1.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + newtoken)


        var Id = sh1.getInt("diaryId", 0)
        Log.d(ContentValues.TAG, "onResume: ++++" + Id)

        var sh2 = getSharedPreferences("Briota", MODE_PRIVATE)

        var f = sh2.getString("dateclick", "")
        Log.d(TAG, "editDailyDairyInfo: " + f)

        var s = sh2.getString("dateclickend", "")
        Log.d(TAG, "editDailyDairyInfo: " + s)


        val myDate = Date()
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("UTC")
        calendar.time = myDate
        val time = calendar.time
        val outputFmt = SimpleDateFormat("'T'HH:mm:ss")
        val dateAsString = outputFmt.format(time)
        print(dateAsString)
        Log.d(TAG, "onDayClick: " + dateAsString)
        print(dateAsString)


        var putDailyDairy: PutDailyDairyModel? = PutDailyDairyModel()

        putDailyDairy!!.patient_id = myUserId
        putDailyDairy.medication = Medication
        putDailyDairy.exercise = Exercise
        putDailyDairy.diet = Diet
        putDailyDairy.curr_date = now.toString().substring(0, 10) + "T23:59:59.704"

        if(start_date_for_post != null) {
            putDailyDairy.added_date = "${start_date_for_post!!.substring(0, 10)}$dateAsString"
        }
        else
        {
            putDailyDairy.added_date = now.toString().substring(0, 10) + "T00:00:01.704"

        }

        Log.d(TAG, "editDailyDairyInfo: " + start_date_for_post)

        RetrofitClient.getRetrofitInstance.putDailyDairy(
            "Bearer " + newtoken,
            putDailyDairy,
            Id
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(ContentValues.TAG, "Failed::" + t)

                    Utility.sharedInstance.dismissProgressDialog()
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {

                    Log.d(
                        ContentValues.TAG,
                        "++++++++++++++++++++++++++++++++" + response.toString()
                    )
                    Log.d(ContentValues.TAG, "++++++++++" + SignUpResponseModel())
                    if (response.code() == 200) {


                        if (response.body().toString()
                                .equals("Update the Daily diary info")
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@DailyDairyActivity,
                                "" + response.body()
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                }
            })


    }


    override fun onResume() {
        super.onResume()


        if(start_date == null){

            var sh = getSharedPreferences("Briota", MODE_PRIVATE)
            var token = sh.getString("bearer_token", "")
            Log.d(ContentValues.TAG, "onResume: ++++" + token)

            var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
            var myUserId = uId.getString("user_Id", "")!!.toInt()
            Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)

            var end_date = now.toString().substring(0, 10) + "T23:59:59.704"

            Log.d(TAG, "onResume: " + end_date)

            val start_date = now.toString().substring(0, 10) + "T00:00:01.704"

            Log.d(TAG, "onResume: " + start_date)

            getDailyDairyApi(token!!, myUserId, start_date, end_date)
        }


    }


    private fun getDailyDairyApi(
        token: String,
        myUserId: Int,
        start_date: String,
        end_date: String
    ) {

        RetrofitClient.getRetrofitInstance.getDailyDairyApi(
            "Bearer " + token,
            myUserId,
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

                        Log.d(TAG, "onResponse: +++++++" + response.body())


                        try {

                            var gson = Gson()
                            var json: String = gson.toJson(response.body())


                            Log.d(TAG, "onResponse: " + json)


                            val getDailyDairyData: GetDailyDairyData =
                                gson.fromJson(json, GetDailyDairyData::class.java)

                            if (getDailyDairyData.status == 202) {

                                medicationimg.setImageResource(R.color.white)
                                dietimg.setImageResource(R.color.white)
                                exerciseimg.setImageResource(R.color.white)

                            }

                            var id = getDailyDairyData.data!![0].id

                            Log.d(TAG, "onResponse: " + id)
                            print(id)

                            var sharedPreferences: SharedPreferences =
                                this@DailyDairyActivity.getSharedPreferences("Briota", MODE_PRIVATE)
                            var myEdit = sharedPreferences.edit()
                            myEdit.putInt("diaryId", id!!)
                            myEdit.apply()


                            Log.d(TAG, "onResponse: " + getDailyDairyData)


                            Log.d(TAG, "onResponse: " + getDailyDairyData)

                            SetDailyDataDairy(getDailyDairyData)

                            //event fun working
                             //      event(getDailyDairyData)

                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

                    } else {
                        Utility.sharedInstance.showToastError(
                            this@DailyDairyActivity,
                            response.body().toString()
                        )


                    }


                }
            })


    }

    private fun SetDailyDataDairy(dailyDairyData: GetDailyDairyData) {


        if (dailyDairyData.data!![0].medication == true) {
            medicationimg!!.setImageResource(R.drawable.rightlogo)

        } else {
            medicationimg!!.setImageResource(R.drawable.wronglogo)
        }

        if (dailyDairyData.data[0].diet == false) {
            dietimg.setImageResource(R.drawable.wronglogo)

        } else {
            dietimg.setImageResource(R.drawable.rightlogo)
        }

        if (dailyDairyData.data[0].exercise == false) {
            exerciseimg.setImageResource(R.drawable.wronglogo)

        } else {
            exerciseimg.setImageResource(R.drawable.rightlogo)
        }
    }

    private fun event(dailyDairyData: GetDailyDairyData) {



        var eventconversion = dailyDairyData.data!!

        for (i in 0 until eventconversion.size){

            var eventadddateconv = dailyDairyData.data[i].added_date

            val myDate = eventadddateconv


            val df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date: Date? = df.parse(myDate)

            var datetomil = date!!.toInstant().atZone(ZoneOffset.UTC).plusMinutes(350).toEpochSecond().toString()+"000"

            Log.d(TAG, "event: "+ datetomil)

            try {

                if (dailyDairyData.data!![i].medication == true) {

                    val ev2 = Event(Color.GREEN, datetomil.toLong())
                    compactCalendar!!.addEvent(ev2)

                } else {
                    val ev2 = Event(Color.RED, datetomil.toLong())
                    compactCalendar!!.addEvent(ev2)
                }
                if (dailyDairyData.data[i].exercise == true) {
                    val ev2 = Event(Color.GREEN, datetomil.toLong())
                    compactCalendar!!.addEvent(ev2)

                } else {
                    val ev2 = Event(Color.RED, datetomil.toLong())
                    compactCalendar!!.addEvent(ev2)
                }
                if (dailyDairyData.data[i].diet == true) {
                    val ev2 = Event(Color.GREEN, datetomil.toLong())
                    compactCalendar!!.addEvent(ev2)

                } else {
                    val ev2 = Event(Color.RED, datetomil.toLong())
                    compactCalendar!!.addEvent(ev2)
                }




            }
            catch (e : Exception){

                Log.d(TAG, "event: " + e)
            }

        }




    }

}





















