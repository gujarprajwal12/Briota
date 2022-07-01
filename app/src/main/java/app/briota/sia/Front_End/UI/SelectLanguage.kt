package app.briota.sia.Front_End.UI

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle

import android.util.Log

import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.briota.sia.R
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Boolean
import java.util.*


class SelectLanguage : AppCompatActivity() {
    var prevStarted = "true"

    lateinit var btn_Radio_English: RadioButton
    lateinit var txt_Language: TextView
    val cfg = Configuration()

    lateinit var btn_Radio_Danish: RadioButton
    lateinit var radio_group_English: RadioGroup
    lateinit var radio_group_Danish: RadioGroup
    lateinit var btnNext: Button


    override fun onResume() {
        super.onResume()
        val sharedpreferences = getSharedPreferences("Briota", MODE_PRIVATE)
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            val editor = sharedpreferences.edit()
            editor.putBoolean(prevStarted, Boolean.TRUE)
            editor.apply()
        } else {
            moveToSecondary()
        }

        var getLanguage = intent.getStringExtra("chosenLanguage")


        if (getLanguage == "English"){

            btn_Radio_English.isChecked = true
            if (btn_Radio_Danish.isChecked)
                radio_group_Danish.clearCheck()
            cfg.setLocale(Locale.ENGLISH)
            resources.updateConfiguration(cfg, resources.displayMetrics)
        }

        if (getLanguage == "Danish"){
            if(btn_Radio_English.isChecked)
             radio_group_English.clearCheck()

            btn_Radio_Danish.isChecked = true
            cfg.setLocale(Locale.forLanguageTag("da"))
            resources.updateConfiguration(cfg, resources.displayMetrics)


        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)


        initView()

        onClick()

//        val btn_Radio_French = findViewById<RadioButton>(R.id.btn_Radio_French)
//        val btn_Radio_Italian = findViewById<RadioButton>(R.id.btn_Radio_Italian)
//        val btn_Radio_German = findViewById<RadioButton>(R.id.btn_Radio_German)
//        val btn_Radio_Spanish = findViewById<RadioButton>(R.id.btn_Radio_Spanish)


//        val radio_group_French = findViewById<RadioGroup>(R.id.radio_group_French)
//        val radio_group_Italian = findViewById<RadioGroup>(R.id.radio_group_Italian)
//        val radio_group_German = findViewById<RadioGroup>(R.id.radio_group_German)
//        val radio_group_Spanish = findViewById<RadioGroup>(R.id.radio_group_Spanish)


//        btn_Radio_French.setOnClickListener{
//            if(btn_Radio_English.isChecked)
//                radio_group_English.clearCheck()
//
//            if(btn_Radio_Italian.isChecked)
//                radio_group_Italian.clearCheck()
//
//            if(btn_Radio_German.isChecked)
//                radio_group_German.clearCheck()
//
//            if(btn_Radio_Spanish.isChecked)
//                radio_group_Spanish.clearCheck()
//
//            if(btn_Radio_Danish.isChecked)
//                radio_group_Danish.clearCheck()
//
//            val cfg = Configuration()
//            cfg.setLocale(Locale.FRENCH)
//            resources.updateConfiguration(cfg, resources.displayMetrics)
//
//        }
//
//
//
//        btn_Radio_Italian.setOnClickListener{
//            if(btn_Radio_English.isChecked)
//                radio_group_English.clearCheck()
//
//            if(btn_Radio_French.isChecked)
//                radio_group_French.clearCheck()
//
//            if(btn_Radio_German.isChecked)
//                radio_group_German.clearCheck()
//
//            if(btn_Radio_Spanish.isChecked)
//                radio_group_Spanish.clearCheck()
//
//            if(btn_Radio_Danish.isChecked)
//                radio_group_Danish.clearCheck()
//
//            val cfg = Configuration()
//            cfg.setLocale(Locale.ITALIAN)
//            resources.updateConfiguration(cfg, resources.displayMetrics)
//
//
//
//        }
//
//
//
//        btn_Radio_German.setOnClickListener{
//            if(btn_Radio_English.isChecked)
//                radio_group_English.clearCheck()
//
//            if(btn_Radio_Italian.isChecked)
//                radio_group_Italian.clearCheck()
//
//            if(btn_Radio_French.isChecked)
//                radio_group_French.clearCheck()
//
//            if(btn_Radio_Spanish.isChecked)
//                radio_group_Spanish.clearCheck()
//
//            if(btn_Radio_Danish.isChecked)
//                radio_group_Danish.clearCheck()
//
//
//            val cfg = Configuration()
//            cfg.setLocale(Locale.GERMANY)
//            resources.updateConfiguration(cfg, resources.displayMetrics)
//
//
//        }
//
//
//        btn_Radio_Spanish.setOnClickListener{
//            if(btn_Radio_English.isChecked)
//                radio_group_English.clearCheck()
//
//            if(btn_Radio_Italian.isChecked)
//                radio_group_Italian.clearCheck()
//
//            if(btn_Radio_German.isChecked)
//                radio_group_German.clearCheck()
//
//            if(btn_Radio_French.isChecked)
//                radio_group_French.clearCheck()
//
//            if(btn_Radio_Danish.isChecked)
//                radio_group_Danish.clearCheck()
//
//            val cfg = Configuration()
//            cfg.setLocale(Locale.forLanguageTag("es"))
//            resources.updateConfiguration(cfg, resources.displayMetrics)
//        }
//


    }

    fun onClick() {

        btn_Radio_English.setOnClickListener {
//          if(btn_Radio_French.isChecked)
//              radio_group_French.clearCheck()

//          if(btn_Radio_Italian.isChecked)
//              radio_group_Italian.clearCheck()
//
//          if(btn_Radio_German.isChecked)
//              radio_group_German.clearCheck()
//
//          if(btn_Radio_Spanish.isChecked)
//              radio_group_Spanish.clearCheck()

            if (btn_Radio_Danish.isChecked)
                radio_group_Danish.clearCheck()

            txt_Language.text = "Select Language"
            btnNext.text = "Next"

            cfg.setLocale(Locale.ENGLISH)
            resources.updateConfiguration(cfg, resources.displayMetrics)

            var sharedPreferences: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putInt("selectlang", 1)
            myEdit.putString("Select_Lang", "aa")
            myEdit.apply()
            Log.d(TAG, "onClick: " + myEdit)
            myEdit.commit()


        }


        btn_Radio_Danish.setOnClickListener {
            if (btn_Radio_English.isChecked)
                radio_group_English.clearCheck()

            txt_Language.text = "Vælg sprog"
            btnNext.text = "Næste"

//            if(btn_Radio_Italian.isChecked)
//                radio_group_Italian.clearCheck()
//
//            if(btn_Radio_German.isChecked)
//                radio_group_German.clearCheck()
//
//            if(btn_Radio_Spanish.isChecked)
//                radio_group_Spanish.clearCheck()
//
//            if(btn_Radio_French.isChecked)
//                radio_group_French.clearCheck()


            txt_Language.text = "Vælg sprog"
            btnNext.text = "Næste"

            cfg.setLocale(Locale.forLanguageTag("da"))
            resources.updateConfiguration(cfg, resources.displayMetrics)


            var sharedPreferences: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putInt("selectlang", 2)
            myEdit.putString("Select_Lang", "da")
            Log.d(TAG, "onClick: " + myEdit)
            myEdit.apply()
            myEdit.commit()

        }

        btnNext.setOnClickListener {


            var sharedPreferences1: SharedPreferences =
                this.getSharedPreferences("OneTime", MODE_PRIVATE)
            var myEdit1 = sharedPreferences1.edit()
            myEdit1.putString("oneTimeView","OneTime")
            myEdit1.apply()


            if (btn_Radio_English.isChecked == false && btn_Radio_Danish.isChecked == false) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.SelectLanguage)
                )
                return@setOnClickListener
            }

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun initView() {
        btn_Radio_English = findViewById(R.id.btn_Radio_English)
        btn_Radio_Danish = findViewById(R.id.btn_Radio_Danish)
        txt_Language = findViewById(R.id.txt_Language)
        txt_Language.text = resources.getString(R.string.SelectLanguage)
        radio_group_English = findViewById(R.id.radio_group_English)
        radio_group_Danish = findViewById(R.id.radio_group_Danish)
        btnNext = findViewById(R.id.btnNext)


    }

    fun moveToSecondary() {
        // use an intent to travel from one activity to another.
        val intent = Intent(this, SignInActivity::class.java)

        startActivity(intent)
    }

    override fun onBackPressed(){
        return

    }

}

