package app.briota.sia.Front_End.UI

import android.app.Dialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.*
import app.briota.sia.R
import kotlinx.android.synthetic.main.activity_daily_dairy_medications.*
import kotlinx.android.synthetic.main.daily_dairy_toolbar.*

class DailyDairyMedicationsActivity : AppCompatActivity() {

    var dialog: Dialog? = null
    lateinit var ctrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_dairy_medications)


        ctrl = intent.getStringExtra("navController").toString()
        Log.d(TAG, "onCreate: " + ctrl)
        Log.d(TAG, "onCreate: " + ctrl)

        if (ctrl.equals("Exercise")) {
            txt_med_question.text = resources.getString(R.string.did_you_follow_your_exercise_plan)
        }

        initView()

        onClick()

    }

    fun initView() {
        if (ctrl.equals("Medications")) {
            diary_title.text = resources.getString(R.string.medications)
        } else {
            diary_title.text = resources.getString(R.string.exercise_plan)
        }
    }

    fun onClick() {

        back_tolayout.setOnClickListener {
            onBackPressed()
        }

        btn_med_yes.setOnClickListener {

            diaryUpdated()
        }

        btn_med_no.setOnClickListener {

            if (ctrl.equals("Medications")) {
                dialog = Dialog(this)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.attributes.width =
                    WindowManager.LayoutParams.MATCH_PARENT
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setCancelable(false)
                dialog!!.setContentView(R.layout.enter_med_popup)
                dialog!!.show()


                var btn_save: Button = dialog!!.findViewById(R.id.btn_save)

                btn_save.setOnClickListener {

                    dialog!!.dismiss()
                    diaryUpdated()
                }
            } else {

                dialog = Dialog(this)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.attributes.width =
                    WindowManager.LayoutParams.MATCH_PARENT
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setCancelable(false)
                dialog!!.setContentView(R.layout.missed_exercise_popup)
                dialog!!.show()


                var btn_save_exercise: Button = dialog!!.findViewById(R.id.btn_save_exercise)

                btn_save_exercise.setOnClickListener {

                    dialog!!.dismiss()
                    diaryUpdated()
                }
            }

        }


    }

    private fun diaryUpdated() {

        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.action_plan_added_popup)
        dialog!!.show()

        var img: ImageView = dialog!!.findViewById(R.id.thums_up)
        var txt: TextView = dialog!!.findViewById(R.id.txt_success)
        var btn_ok: Button = dialog!!.findViewById(R.id.btn_ok)

        btn_ok.text = resources.getString(R.string.back)
        img.setImageResource(R.drawable.logo_success)
        txt.text = resources.getString(R.string.your_daily_dairy_is_updated)
        txt.setTextColor(resources.getColor(R.color.TextBlue))

        btn_ok.setOnClickListener {
            dialog!!.dismiss()
        }

    }
}