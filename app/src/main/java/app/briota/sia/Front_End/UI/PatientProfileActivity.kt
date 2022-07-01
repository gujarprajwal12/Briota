package app.briota.sia.Front_End.UI

import android.app.Dialog
import android.app.NotificationManager
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.SignUpResponseModel
import kotlinx.android.synthetic.main.activity_patient_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class PatientProfileActivity : AppCompatActivity() {

    lateinit var back: TextView
    lateinit var action_plan: TableRow

    lateinit var profile_txt: TableRow
    lateinit var person: TextView
    lateinit var langscreen: TableRow
    lateinit var Remindersrow: TableRow
    lateinit var Reportrow: TableRow
    lateinit var userguide : TableRow

    lateinit var logout : TableRow
    var dialog : Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_profile)

        initView()

        onClick()


    }

    private fun initView() {
        back = findViewById(R.id.Back)
        action_plan = findViewById(R.id.action_plan)
        profile_txt = findViewById(R.id.profile_txt)
        logout = findViewById(R.id.logout)
        person = findViewById(R.id.person)
        langscreen = findViewById(R.id.langscreen)
        Remindersrow = findViewById(R.id.Remindersrow)
        Reportrow = findViewById(R.id.Reportrow)
        userguide = findViewById(R.id.Guide)

        var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var user = sh.getString("user_name", "")

        person.setText(user)

    }

    private fun onClick() {

        back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        Guidance.setOnClickListener {

            val intent = Intent(this,VideoPlayerActivity::class.java)
            startActivity(intent)
        }

        findAir.setOnClickListener {


            val intent = Intent(this,FindAirAddDevice::class.java)
            startActivity(intent)

//            val intent = Intent(this,FindAirActivity::class.java)
//            startActivity(intent)
        }

        userguide.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/gFGM-8X98zs"))
            startActivity(intent)
        }

        profile_txt.setOnClickListener {

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


        langscreen.setOnClickListener {

//            val sharedpreferences = getSharedPreferences("Briota", MODE_PRIVATE)
//            var editor = sharedpreferences.edit()
//            editor.clear()
//            editor.apply()


//            if(lang.text.equals("Change Your Language"))
//            {
//                val intent = Intent( this, SelectLanguage::class.java)
//                intent.putExtra("chosenLanguage","English")
//                startActivity(intent)
//            }
//            else
//            {
//                val intent = Intent( this, SelectLanguage::class.java)
//                intent.putExtra("chosenLanguage","Danish")
//                startActivity(intent)
//
//            }

            val cfg = Configuration()

            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(true)
            dialog!!.setContentView(R.layout.langdialog)
            dialog!!.show()

            var butYes: Button = dialog!!.findViewById(R.id.butYEs)
            var butNo: Button = dialog!!.findViewById(R.id.butNO)
            var tv_alert: TextView = dialog!!.findViewById(R.id.tv_alert)
            var canDialog :ImageView = dialog!!.findViewById(R.id.can_langDialog)
            tv_alert.text = resources.getString(R.string.select_language)


            canDialog.setOnClickListener {
                dialog!!.dismiss()
            }

            butNo.setOnClickListener {
                cfg.setLocale(Locale.ENGLISH)
                resources.updateConfiguration(cfg, resources.displayMetrics)
                recreate()
            }
            butYes.setOnClickListener {

                cfg.setLocale(Locale.forLanguageTag("da"))
                resources.updateConfiguration(cfg, resources.displayMetrics)
                recreate()
            }

        }


        action_plan.setOnClickListener {
            val intent = Intent(this, AddImageActivity::class.java)
            startActivity(intent)
        }

        actionPlanTemplate.setOnClickListener {


            val intent = Intent(this, ActionPlanTemplateActivity::class.java)
            startActivity(intent)


        }

        dailyDiary.setOnClickListener {

            val intent = Intent(this, DailyDairyActivity::class.java)
            startActivity(intent)
        }


        Remindersrow.setOnClickListener {

            val intent = Intent(this, ReminderActivity::class.java)
            startActivity(intent)

        }


        Reportrow.setOnClickListener {

            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)

        }


        logout.setOnClickListener {

            var sh = getSharedPreferences("Briota", MODE_PRIVATE)
            var newtoken = sh.getString("bearer_token", "")
            Log.d(ContentValues.TAG, "onResume: ++++" + newtoken)


            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.common_dialog)
            dialog!!.show()

            var butYes: Button = dialog!!.findViewById(R.id.butYEs)
            var butNo: Button = dialog!!.findViewById(R.id.butNO)
            var tv_alert: TextView = dialog!!.findViewById(R.id.tv_alert)
            tv_alert.text = resources.getString(R.string.Logout)

            butNo.setOnClickListener {
                dialog!!.dismiss()
            }
            butYes.setOnClickListener {

                Utility.sharedInstance.showProgressDialog(this)
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val id = "channel1"
                notificationManager.deleteNotificationChannel(id)
                logoutAPI(newtoken!!)

                dialog!!.dismiss()

            }
        }
    }

    private fun logoutAPI(token: String) {

        RetrofitClient.getRetrofitInstance.postLogoutAPI("Bearer " + token)
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(TAG, "Failed::" + t)

                    Utility.sharedInstance.dismissProgressDialog()
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Log.d(TAG, "++++++++++++++++++++++++++++++++" + response.toString())
                    Log.d(TAG, "++++++++++" + SignUpResponseModel())
                    if (response.code() == 201) {

                        try {
                            openAct()

                        } catch (t: Throwable) {

                        }


                    } else {


                    }
                }
            })

    }

    private fun openAct() {

        Utility.sharedInstance.dismissProgressDialog()
        val i = Intent(this, SignInActivity::class.java)

        var sharedPreferences: SharedPreferences =
            this@PatientProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)
        var myEdit = sharedPreferences.edit()
        myEdit.putString("bearer_token", "")
        myEdit.apply()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var mytoken = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + mytoken)


        startActivity(i)
        finish()

    }


}