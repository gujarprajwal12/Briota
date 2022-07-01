package app.briota.sia.Front_End.UI


import android.app.*
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.view.adapter.ReminderListAdapter
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ReminderModel.*
import app.briota.sia.integration_layer.models.User_Detail.SignUpResponseModel
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.peak_reading_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ReminderActivity : AppCompatActivity() {


    var dialog: Dialog? = null
    var mReminderListAdapter: ReminderListAdapter? = null
    var mListCities = ArrayList<String>()

    var medicationStatus: Boolean = false
    var exerciseStatus: Boolean = false
    var dietStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        initView()

        onClick()

    }


    fun initView() {

        txtTitle.text = resources.getString(R.string.Reminders)

        createNotificationChannel()

    }


    fun onClick() {

        back_tol.setOnClickListener {
            onBackPressed()
        }

        floatingActionButtonforreminder.setOnClickListener {

            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(true)
            dialog!!.setContentView(R.layout.alaram_dialog)
            dialog!!.show()


            var btn_save_reminder: Button = dialog!!.findViewById(R.id.btn_save_reminder)
            var rg_Medication: RadioGroup = dialog!!.findViewById(R.id.rg_Medication)
            var btn_Radio_Medication: RadioButton = dialog!!.findViewById(R.id.btn_Radio_Medication)
            var rg_Exercise: RadioGroup = dialog!!.findViewById(R.id.rg_Exercise)
            var btn_Radio_Exercise: RadioButton = dialog!!.findViewById(R.id.btn_Radio_Exercise)
            var rg_Diet: RadioGroup = dialog!!.findViewById(R.id.rg_Diet)
            var btn_Radio_Diet: RadioButton = dialog!!.findViewById(R.id.btn_Radio_Diet)

            var timePicker: TimePicker = dialog!!.findViewById(R.id.timePicker)
            timePicker.setIs24HourView(true)








            btn_Radio_Medication.setOnClickListener {
                rg_Exercise.clearCheck()
                rg_Diet.clearCheck()
                medicationStatus = true
            }

            btn_Radio_Exercise.setOnClickListener {
                rg_Medication.clearCheck()
                rg_Diet.clearCheck()
                exerciseStatus = true
            }

            btn_Radio_Diet.setOnClickListener {
                rg_Medication.clearCheck()
                rg_Exercise.clearCheck()
                dietStatus = true
            }


            btn_save_reminder.setOnClickListener {

                if (btn_Radio_Medication.isChecked == false && btn_Radio_Exercise.isChecked == false && btn_Radio_Diet.isChecked == false) {
                    Utility.sharedInstance.showToastError(
                        this,
                        resources.getString(R.string.pleaseselectabovetitle)
                    )
                } else {

                    var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
                    var myUserId = sh.getString("user_Id", "")
                    Log.d(TAG, "onResponse: +++++++" + myUserId)


                    var timePicker: TimePicker = dialog!!.findViewById(R.id.timePicker)
                    timePicker.setIs24HourView(true)

                    val ReminderHour = timePicker.hour
                    val ReminderMinute = timePicker.minute
                    val ReminderTime = "$ReminderHour:$ReminderMinute"

                    Log.d(TAG, "onClick: " + ReminderTime)
                    print(ReminderTime)

                    var postReminderModel: PostReminderModel? = PostReminderModel()
                    postReminderModel!!.patient_id = myUserId!!.toInt()
                    postReminderModel.medication = medicationStatus
                    postReminderModel.exercise = exerciseStatus
                    postReminderModel.diet = dietStatus
                    postReminderModel.added_time = ReminderTime


                    postReminderapi(postReminderModel)

                    dialog!!.dismiss()

                }
            }


        }

    }


    override fun onResume() {
        super.onResume()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + token)

        var sh1 = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = sh1.getString("user_Id", "")
        Log.d(TAG, "onResponse: +++++++" + myUserId)


        getReminderList(token!!, myUserId!!.toInt())

    }

    private fun getTime(): Long {

        val timePicker: TimePicker = dialog!!.findViewById(R.id.timePicker)
        val datePicker: DatePicker = dialog!!.findViewById(R.id.datePicker)


        val minute = timePicker.minute
        val hour = timePicker.hour
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }


    fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    private fun postReminderapi(postReminderModel: PostReminderModel) {

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + newtoken)


        var gson = Gson()
        var obj = JSONObject()
        var json = gson.toJson(postReminderModel)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.postReminderAPI(
            "Bearer " + newtoken,
            postReminderModel
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(TAG, "Failed::" + t)

                    Utility.sharedInstance.dismissProgressDialog()
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {

                    if (response.code() == 201) {

                        Log.d(TAG, "onResponse: " + response.body())
                        try {
                            var gson: Gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var postReminderResponseModel: PostReminderResponseModel =
                                gson.fromJson(json, PostReminderResponseModel::class.java)

                            Log.d(TAG, "onResponse: " + postReminderResponseModel)
                            print(postReminderResponseModel)

                            var reminderID = postReminderResponseModel.id

                            Log.d(TAG, "onResponse: " + reminderID)
                            print(reminderID)


                            scheduleNotification(reminderID!!)


                            recreate()


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t
                            )
                        }

                    }
                    if (response.code() == 400) {
                        Utility.sharedInstance.showToastError(
                            applicationContext,
                            resources.getString(R.string.patienthasreminder)
                        )
                    }
                }
            })

    }

    private fun scheduleNotification(remId: Int) {
        val intent = Intent(applicationContext, Notification::class.java)

        var title: String? = null

        if (medicationStatus == true) {
            title = resources.getString(R.string.medicationreminder)

        } else if (exerciseStatus == true) {
            title = resources.getString(R.string.exercisereminder)
        } else {
            title = resources.getString(R.string.dietreminder)
        }

        intent.putExtra(titleExtra, title)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            remId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val time = getTime()
        Log.d(TAG, "scheduleNotification: " + time)
        print(time)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        // showAlert(time, title)

        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        val myTime = timeFormat.format(date)


    }


    private fun getReminderList(token: String, userId: Int) {

        RetrofitClient.getRetrofitInstance.getReminderApi("Bearer " + token, userId)
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

                            var getReminderResponseModel: GetReminderResponseModel =
                                gson.fromJson(json, GetReminderResponseModel::class.java)

                            Log.d(TAG, "onResponse: " + getReminderResponseModel)
                            print(getReminderResponseModel)
                            setReminderData(getReminderResponseModel)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@ReminderActivity,
                            "Get Profile Info not called"
                        )


                    }
                }
            })


    }

    private fun setReminderData(reminderResponseModel: GetReminderResponseModel) {

        var reminderList: ArrayList<data>?
        reminderList = reminderResponseModel.data

        Log.d(TAG, "setReminderData: " + reminderList!!.size)

        if (reminderList.size > 0) {
            alarmRecyclerview.visibility = View.VISIBLE
            empty_list_txt_for_reminder.visibility = View.GONE
        } else {
            alarmRecyclerview.visibility = View.GONE
            empty_list_txt_for_reminder.visibility = View.VISIBLE

        }


        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        alarmRecyclerview.layoutManager = linearLayoutManager


        mReminderListAdapter = ReminderListAdapter(this, reminderList)
        alarmRecyclerview.adapter = mReminderListAdapter

        var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //   Toast.makeText(this@ReminderActivity, "on Move", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                Utility.sharedInstance.showToastSuccess(
                    applicationContext,
                    resources.getString(R.string.reminderdeletedsuccesssfully)
                )
                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.adapterPosition
                val reminderID = reminderList.get(position).id

                Log.d(TAG, "onSwiped: " + reminderID)
                print(reminderID)


                var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                var newtoken = sh.getString("bearer_token", "")
                Log.d(TAG, "onResume: ++++" + newtoken)



                RetrofitClient.getRetrofitInstance.removeReminder(
                    "Bearer $newtoken",
                    reminderID!!
                )
                    .enqueue(object : Callback<Any> {
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                            Utility.sharedInstance.showToastError(
                                this@ReminderActivity,
                                "OnF"
                            )
                            Log.e(TAG, "Failed::" + t)
                            Utility.sharedInstance.dismissProgressDialog()
                        }

                        override fun onResponse(
                            call: Call<Any>,
                            response: Response<Any>
                        ) {

                            Utility.sharedInstance.dismissProgressDialog()
                            if (response.code() == 200) {
                                Utility.sharedInstance.dismissProgressDialog()
                                Log.e(TAG, "status 200" + response.body())

                                val intent = Intent(applicationContext, Notification::class.java)

                                var title: String? = null

                                if (medicationStatus == true) {
                                    title = resources.getString(R.string.medicationreminder)

                                } else if (exerciseStatus == true) {
                                    title = resources.getString(R.string.exercisereminder)
                                } else {
                                    title = resources.getString(R.string.dietreminder)
                                }

                                intent.putExtra(titleExtra, title)

                                val pendingIntent = PendingIntent.getBroadcast(
                                    applicationContext,
                                    reminderID,
                                    intent,
                                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                                )

                                val alarmManager =
                                    getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                alarmManager.cancel(pendingIntent)

//                              var mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                                mNotificationManager.cancel(reminderID) // Notification ID to cancel
//
                                reminderList.removeAt(position)

                                mReminderListAdapter!!.notifyDataSetChanged()

                                var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                                var token = sh.getString("bearer_token", "")
                                Log.d(TAG, "onResume: ++++" + token)

                                var sh1 = getSharedPreferences("USER_ID", MODE_PRIVATE)
                                var myUserId = sh1.getString("user_Id", "")
                                Log.d(TAG, "onResponse: +++++++" + myUserId)


                                getReminderList(token!!, myUserId!!.toInt())


                            } else {
                                Utility.sharedInstance.dismissProgressDialog()
                                Log.e(TAG, "::else part:::" + response.body())
                            }

                        }
                    })

            }


            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val p = Paint()
                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {


                    //Drawing for Swife Right

                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3
                    if (dX > 0) {

                    } else {
                        //Drawing for Swife Left
                        p.color = Color.parseColor("#D32F2F")
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.dustbin)
                        val icon_dest = RectF(
                            itemView.right.toFloat() - 2 * width,
                            itemView.top.toFloat() + width,
                            itemView.right.toFloat() - width,
                            itemView.bottom.toFloat() - width
                        )
                        c.drawBitmap(icon, null, icon_dest, p)
                    }
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(alarmRecyclerview)


    }

    fun updateReminder(
        id: Int?,
        medStatus: Boolean,
        exStatus: Boolean,
        dStatus: Boolean,
        time: String
    ) {


        var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = sh.getString("user_Id", "")
        Log.d(TAG, "onResponse: +++++++" + myUserId)

        var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh1.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + token)

        var updateReminderRequestModel: UpdateReminderRequestModel? = UpdateReminderRequestModel()

        updateReminderRequestModel!!.patient_id = myUserId!!.toInt()
        updateReminderRequestModel.medication = medStatus
        updateReminderRequestModel.exercise = exStatus
        updateReminderRequestModel.diet = dStatus
        updateReminderRequestModel.added_time = time


        RetrofitClient.getRetrofitInstance.updateReminder(
            "Bearer $token",
            updateReminderRequestModel,
            id
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(TAG, "Failed::" + t)

                    Utility.sharedInstance.dismissProgressDialog()
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.code() == 200) {


                        try {
                            var gson: Gson = Gson()
                            var json: String = gson.toJson(response.body())


                            recreate()
                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                }
            })


    }


}

