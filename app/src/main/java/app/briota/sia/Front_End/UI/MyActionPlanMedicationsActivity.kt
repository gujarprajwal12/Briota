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
import android.widget.*
import app.briota.sia.Network.retrofit.api.RetrofitClient

import app.briota.sia.R
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.appcompat.app.AppCompatActivity

import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.*
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.GetActionPlanListModel

import app.briota.sia.integration_layer.models.User_Detail.SignUpResponseModel

import kotlinx.android.synthetic.main.activity_my_action_plan_medications.*


class MyActionPlanMedicationsActivity : AppCompatActivity() {

    lateinit var btnsaveactionplan :Button
    lateinit var Back : TextView
    var dialog: Dialog? = null
    lateinit var redzone: LinearLayout
    lateinit var greenyellow : LinearLayout
    lateinit var texpuff : TextView
    lateinit var textmg : TextView
    lateinit var Morning_checkBox_green_zoon : CheckBox
    lateinit var Afternoon_checkBox_grren_zoon : CheckBox
    lateinit var Evening_checkBox_green_zone : CheckBox
    lateinit var Night_checkBox_green_zone : CheckBox
    lateinit var Enter_Medicine_Name_green_zone : EditText
    lateinit var editenter_number_of_puffs_green_zone : EditText

    var btn_Radio_green : RadioButton?  = null
    var btn_Radio_yellow : RadioButton? = null
    var btn_Radio_Red : RadioButton? = null
    var btn_Radio_Daily : RadioButton?  = null
    var btn_Radio_Rescue : RadioButton?  = null
    var btn_Radio_Corticosteroid_tablet : RadioButton?  = null
    var rg_green : RadioGroup? = null
    var rg_yellow : RadioGroup? = null
    var rg_red : RadioGroup? = null
    var rg_Daily : RadioGroup? = null
    var rg_Rescue : RadioGroup? = null
    var rg_Corticosteroid_tablet : RadioGroup? = null
    var zone : String? = null
    var morningStatus : Boolean? = false
    var afternoonStatus : Boolean? = false
    var eveningStatus : Boolean? = false
    var nightStatus : Boolean? = false
    lateinit var  txtTitle: TextView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_action_plan_medications)

        intView()
        onClick()
    }

    fun  intView(){

        btnsaveactionplan = findViewById(R.id.btnsaveactionplan)
        Back = findViewById(R.id.Back)


        btn_Radio_green = findViewById(R.id.btn_Radio_green)
        btn_Radio_yellow = findViewById(R.id.btn_Radio_yellow)
        btn_Radio_Red = findViewById(R.id.btn_Radio_Red)
        btn_Radio_Daily = findViewById(R.id.btn_Radio_Daily)
        btn_Radio_Rescue = findViewById(R.id.btn_Radio_Rescue)
        btn_Radio_Corticosteroid_tablet = findViewById(R.id.btn_Radio_Corticosteroid_tablet)
        rg_green = findViewById(R.id.rg_green)
        rg_yellow = findViewById(R.id.rg_yellow)
        rg_red = findViewById(R.id.rg_Red)
        rg_Daily = findViewById(R.id.rg_Daily)
        rg_Rescue = findViewById(R.id.rg_Rescue)
        rg_Corticosteroid_tablet = findViewById(R.id.rg_Corticosteroid_tablet)
        redzone =findViewById(R.id.redzone)
        greenyellow = findViewById(R.id.greenyellow)
        texpuff = findViewById(R.id.texpuff)
        textmg = findViewById(R.id.textmg)
        Morning_checkBox_green_zoon =findViewById(R.id.Morning_checkBox_green_zoon)
        Afternoon_checkBox_grren_zoon = findViewById(R.id.Afternoon_checkBox_grren_zoon)
        Evening_checkBox_green_zone = findViewById(R.id.Evening_checkBox_green_zone)
        Night_checkBox_green_zone = findViewById(R.id.Night_checkBox_green_zone)
        Enter_Medicine_Name_green_zone = findViewById(R.id.Enter_Medicine_Name_green_zone)
        editenter_number_of_puffs_green_zone = findViewById(R.id.editenter_number_of_puffs_green_zone)


        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.addMedications)

    }

    fun onClick(){

        btn_Radio_green!!.setOnClickListener {

            rg_yellow!!.clearCheck()
            rg_red!!.clearCheck()
            btn_Radio_Rescue!!.visibility = View.GONE
            btn_Radio_Corticosteroid_tablet!!.visibility = View.GONE
            texpuff.visibility = View.VISIBLE
            textmg.visibility = View.GONE

            zone = "Green"

            btn_Radio_Daily!!.visibility = View.VISIBLE
            btn_Radio_Daily!!.isChecked = true



        }

        btn_Radio_Daily!!.setOnClickListener {



               rg_Rescue!!.clearCheck()
               rg_Corticosteroid_tablet!!.clearCheck()

           }

        btn_Radio_Rescue!!.setOnClickListener {



            rg_Daily!!.clearCheck()
            rg_Corticosteroid_tablet!!.clearCheck()

        }

        btn_Radio_Corticosteroid_tablet!!.setOnClickListener {



            rg_Rescue!!.clearCheck()
            rg_Daily!!.clearCheck()

        }

        btn_Radio_oral_Corticosteroid!!.setOnClickListener {

            rg_rescue!!.clearCheck()


        }

        btn_Radio_rescue.setOnClickListener {

            rg_oral_Corticosteroid!!.clearCheck()
        }



        btn_Radio_yellow!!.setOnClickListener {

            rg_green!!.clearCheck()
            rg_red!!.clearCheck()
            greenyellow.visibility = View.VISIBLE

            zone = "Yellow"

            btn_Radio_Daily!!.visibility = View.VISIBLE
            redzone.visibility = View.GONE
            texpuff.visibility = View.VISIBLE
            textmg.visibility = View.GONE
            rg_Daily!!.clearCheck()

            btn_Radio_Daily!!.visibility = View.VISIBLE

            btn_Radio_Rescue!!.visibility = View.VISIBLE
            btn_Radio_Corticosteroid_tablet!!.visibility = View.VISIBLE


            btn_Radio_Daily!!.isChecked = false
            btn_Radio_Rescue!!.isChecked = false
            btn_Radio_Corticosteroid_tablet!!.isChecked = false



        }

        btn_Radio_Red!!.setOnClickListener {

            rg_green!!.clearCheck()
            rg_yellow!!.clearCheck()

            zone = "Red"

            textmg.visibility = View.VISIBLE
            texpuff.visibility = View.GONE

            redzone.visibility = View.VISIBLE
            greenyellow.visibility =View.GONE

        }




        Morning_checkBox_green_zoon.setOnClickListener {

            morningStatus = Morning_checkBox_green_zoon.isChecked
        }

      Afternoon_checkBox_grren_zoon.setOnClickListener {

          afternoonStatus = Afternoon_checkBox_grren_zoon.isChecked
      }

      Evening_checkBox_green_zone.setOnClickListener {

          eveningStatus = Evening_checkBox_green_zone.isChecked
      }

        Night_checkBox_green_zone.setOnClickListener {

            nightStatus = Night_checkBox_green_zone.isChecked
        }


        Back.setOnClickListener {

           onBackPressed()
        }

        btnsaveactionplan.setOnClickListener {

            if (btn_Radio_green!!.isChecked == false && btn_Radio_Red!!.isChecked == false  && btn_Radio_yellow!!.isChecked == false ) {
                Utility.sharedInstance.showToastError(this, resources.getString(R.string.Pleaseselectatleastonezone))
                return@setOnClickListener
            }


            if(Enter_Medicine_Name_green_zone.text.toString().trim().isEmpty())
            {
                Utility.sharedInstance.showToastError(this,resources.getString(R.string.EnterMedicineName))
                Enter_Medicine_Name_green_zone.requestFocus()
                return@setOnClickListener
            } else if(editenter_number_of_puffs_green_zone.text.toString().trim().isEmpty())
            {
                if(btn_Radio_Red!!.isChecked == true) {
                    Utility.sharedInstance.showToastError(this, resources.getString(R.string.EnterNumberofDose))
                }
                else
                {
                    Utility.sharedInstance.showToastError(this, resources.getString(R.string.EnterNumberofPuffs))
                }
                editenter_number_of_puffs_green_zone.requestFocus()
                return@setOnClickListener
            } else if (Morning_checkBox_green_zoon.isChecked == false && Afternoon_checkBox_grren_zoon.isChecked == false &&
                Evening_checkBox_green_zone.isChecked  == false && Night_checkBox_green_zone.isChecked  == false) {

                Utility.sharedInstance.showToastError(this, resources.getString(R.string.Pleaseselectatleastoneofthem))
                Morning_checkBox_green_zoon.requestFocus()
                return@setOnClickListener
            }
            else{


                    postMedicationActionPlan()


            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.addmedcationsuceessfulpopup)
                dialog!!.show()




            var btnaddmoremedicine: Button = dialog!!.findViewById(R.id.btnaddmoremedicine)

             btnaddmoremedicine.setOnClickListener {

                var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                var token = sh.getString("bearer_token", "")
                Log.d(ContentValues.TAG, "onResume: ++++"+token)

                var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
                var myUserId = uId.getString("user_Id", "")!!.toInt()
                Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)

                getActionPlanList(token!!,myUserId)

                 rg_green!!.clearCheck()
                 rg_yellow!!.clearCheck()
                 rg_red!!.clearCheck()
                 rg_Daily!!.clearCheck()
                 rg_Rescue!!.clearCheck()
                 rg_rescue!!.clearCheck()
                 rg_Corticosteroid_tablet!!.clearCheck()
                 rg_oral_Corticosteroid!!.clearCheck()
                 Enter_Medicine_Name_green_zone.text.clear()
                 editenter_number_of_puffs_green_zone.text.clear()
                 Morning_checkBox_green_zoon.isChecked = false
                 Afternoon_checkBox_grren_zoon.isChecked = false
                 Evening_checkBox_green_zone.isChecked = false
                 Night_checkBox_green_zone.isChecked = false


                dialog!!.dismiss()


            }

            var btngotoactionplan: Button = dialog!!.findViewById(R.id.btngotoactionplan)

            btngotoactionplan.setOnClickListener {

                val intent = Intent (this , MyActionPlanActivity::class.java)
                startActivity(intent)


            }
            }

        }


    }


    private fun postMedicationActionPlan() {

        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = uId.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var diagnosis = sh.getString("Diagnosis", "")
        Log.d(ContentValues.TAG, "onResponse: +++++++" + diagnosis)

        var patientId = sh.getInt("myId", 0)

        val medcineName = Enter_Medicine_Name_green_zone.text.toString().trim()
        val puff_number = editenter_number_of_puffs_green_zone.text.toString().trim()

        val postActionPlanWithMedModel : PostActionPlanWithMedModel = PostActionPlanWithMedModel()
        val green : Green = Green()
        val yellow : Yellow = Yellow()
        val red : Red = Red()
        val daily : daily = daily()
        val rescue : rescue = rescue()
        val steroid : steroid = steroid()



        postActionPlanWithMedModel.patient_id = myUserId
        postActionPlanWithMedModel.doctor_id = 0
        postActionPlanWithMedModel.diagnosis = diagnosis
        postActionPlanWithMedModel.dr_phone = ""
        postActionPlanWithMedModel.action_plan_id = patientId
        postActionPlanWithMedModel.green_pef = "0"
        postActionPlanWithMedModel.green_fev = "0"
        postActionPlanWithMedModel.yellow_pef = "0"
        postActionPlanWithMedModel.yellow_fev = "0"
        postActionPlanWithMedModel.red_pef = "0"
        postActionPlanWithMedModel.red_fev = "0"

        if(zone.equals("Green"))
        {

            daily.medicine_name = medcineName
                daily.puff_or_dose = puff_number
            daily.morning = morningStatus
            daily.afternoon = afternoonStatus
            daily.evening = eveningStatus
            daily.night = nightStatus

            rescue.medicine_name = null
            rescue.puff_or_dose = null
            rescue.morning = null
            rescue.afternoon = null
            rescue.evening = null
            rescue.night = null


            postActionPlanWithMedModel.Green = green
            postActionPlanWithMedModel.Yellow = yellow
            postActionPlanWithMedModel.Red = red

            green.daily = arrayListOf(daily)
            green.rescue = arrayListOf()
            yellow.daily = arrayListOf()
            yellow.rescue = arrayListOf()
            yellow.steroid = arrayListOf()
            red.rescue = arrayListOf()
            red.steroid = arrayListOf()

        }


        if(zone.equals("Yellow"))
        {
            if(btn_Radio_Daily!!.isChecked == true) {

                daily.medicine_name = medcineName
                daily.puff_or_dose = puff_number
                daily.morning = morningStatus
                daily.afternoon = afternoonStatus
                daily.evening = eveningStatus
                daily.night = nightStatus

                yellow.daily = arrayListOf(daily)
            }
            else
            {
                yellow.daily = arrayListOf()
            }
            if(btn_Radio_Rescue!!.isChecked == true)
            {
            rescue.medicine_name = medcineName
            rescue.puff_or_dose = puff_number
            rescue.morning = morningStatus
            rescue.afternoon = afternoonStatus
            rescue.evening = eveningStatus
            rescue.night = nightStatus

                yellow.rescue = arrayListOf(rescue)
            }
            else
            {
                yellow.rescue = arrayListOf()
            }
            if(btn_Radio_Corticosteroid_tablet!!.isChecked == true)
            {
                steroid.medicine_name = medcineName
                steroid.puff_or_dose = puff_number
                steroid.morning = morningStatus
                steroid.afternoon = afternoonStatus
                steroid.evening = eveningStatus
                steroid.night = nightStatus

                yellow.steroid = arrayListOf(steroid)
            }
            else
            {
                yellow.steroid = arrayListOf()
            }

            postActionPlanWithMedModel.Green = green
            postActionPlanWithMedModel.Yellow = yellow
            postActionPlanWithMedModel.Red = red
            green.daily = arrayListOf()
            green.rescue = arrayListOf()
            red.rescue = arrayListOf()
            red.steroid = arrayListOf()

        }

        if(zone.equals("Red"))
        {
            if(btn_Radio_rescue!!.isChecked == true) {
                rescue.medicine_name = medcineName
                rescue.puff_or_dose = puff_number
                rescue.morning = morningStatus
                rescue.afternoon = afternoonStatus
                rescue.evening = eveningStatus
                rescue.night = nightStatus

                red.rescue = arrayListOf(rescue)
            }
            else
            {
                red.rescue = arrayListOf()
            }

            if(btn_Radio_oral_Corticosteroid!!.isChecked == true) {
                steroid.medicine_name = medcineName
                steroid.puff_or_dose = puff_number
                steroid.morning = morningStatus
                steroid.afternoon = afternoonStatus
                steroid.evening = eveningStatus
                steroid.night = nightStatus

                red.steroid = arrayListOf(steroid)
            }
            else
            {
                red.steroid = arrayListOf()
            }
            postActionPlanWithMedModel.Green = green
            postActionPlanWithMedModel.Yellow = yellow
            postActionPlanWithMedModel.Red = red

            green.daily = arrayListOf()
            green.rescue = arrayListOf()
            yellow.daily = arrayListOf()
            yellow.rescue = arrayListOf()
            yellow.steroid = arrayListOf()


        }


        var mytoken = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" +mytoken)


        var gson = Gson()
        var  obj= JSONObject()
        var json = gson.toJson(postActionPlanWithMedModel)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(ContentValues.TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.actionMedPlanAPI("Bearer "+mytoken,postActionPlanWithMedModel)
            .enqueue(object: Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(ContentValues.TAG,"Failed::"+t)

                    Utility.sharedInstance.dismissProgressDialog()
                }
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Log.d(ContentValues.TAG, "++++++++++++++++++++++++++++++++" + response.toString())
                    Log.d(ContentValues.TAG, "++++++++++" + SignUpResponseModel())
                    if (response.code() == 200) {



                        try {
                            var gson: Gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var postActionPlanRequestModel : PostActionPlanRequestModel
                            = gson.fromJson(json,PostActionPlanRequestModel::class.java)

                            var action_plan_id = postActionPlanRequestModel.action_plan_id

                            var sharedPreferences: SharedPreferences =
                                this@MyActionPlanMedicationsActivity.getSharedPreferences("Briota", MODE_PRIVATE)
                            var myEdit = sharedPreferences.edit()
                            myEdit.putInt("ID", action_plan_id!!.toInt())
                            Log.d(TAG, "onResponse: +++++" +action_plan_id )


                            myEdit.apply()


                        }
                        catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t
                            )
                        }
                        Utility.sharedInstance.dismissProgressDialog()
                        if(response.body().toString().equals("An account with the given email already exists."))
                        {
                            Utility.sharedInstance.showToastError(this@MyActionPlanMedicationsActivity,""+response.body())
                        }


                    }
                    else{

                        response.errorBody()?.let { Log.d("error", it.string()) }

                    }
                }
            })

    }


    private fun getActionPlanList(newtoken : String,userId : Int) {

        RetrofitClient.getRetrofitInstance.geActionPlanList("Bearer "+newtoken,userId)
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {


                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {

                    if (response.code() == 200) {

                        Log.d(ContentValues.TAG, "onResponse: +++++++"+response.body())


                        try {

                            var gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var getActionplanListModel : GetActionPlanListModel =
                                gson.fromJson(json, GetActionPlanListModel::class.java)

                            var myId = getActionplanListModel.data!![0].id

                            var sharedPreferences: SharedPreferences =
                                this@MyActionPlanMedicationsActivity.getSharedPreferences("Briota", MODE_PRIVATE)
                            var myEdit = sharedPreferences.edit()
                            myEdit.putInt("myId",myId!!.toInt())
                            Log.d(TAG, "onResponse: +++++" + myId)


                            myEdit.apply()

                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@MyActionPlanMedicationsActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }



}

