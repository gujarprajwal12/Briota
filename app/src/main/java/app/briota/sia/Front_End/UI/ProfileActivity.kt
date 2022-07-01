package app.briota.sia.Front_End.UI


import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.*
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.PostLifestyleRequest.PostLifestyleRequestModel
import com.briota.sia.integration_layer.models.User_Detail.GetPatientAllDetails.GetPatientAllDetailsResponseModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern


class ProfileActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var button: Button
    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var profile_name: TextView
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editmobilenumber1: EditText
    lateinit var spinner1: Spinner
    lateinit var edituserId: EditText
    lateinit var editcontactemergency: EditText
    lateinit var editselectyourage: EditText
    lateinit var editselectyourHeight: EditText
    lateinit var editselectyourWeight: EditText
    lateinit var spinner: Spinner
    lateinit var editselectyourCity: EditText
    lateinit var textforotherthanastma: TextView

    var lifestyle_smoker: Boolean = false
    var lifestyle_tocaccoornictoin: Boolean = false

    // Post Medical API validation
    var medDiagnosis: String? = null
    var othDiagnosis: Boolean = false
    lateinit var othDiagnosisSpecify: String
    var medMedication: Boolean = false
    lateinit var medicationSpecify: String
    lateinit var medObservations: String
    lateinit var editSpecify: EditText
    lateinit var editSpecify1: EditText
    lateinit var observationformedical: EditText
    var medSymtomList = java.util.ArrayList<Int>()
    var medTriggerList = java.util.ArrayList<Int>()
    lateinit var btn_Radio_Astama: RadioButton
    lateinit var btn_Radio_COPD: RadioButton
    lateinit var autoCompleteGender: AutoCompleteTextView
    lateinit var autoCompleteEthnicity: AutoCompleteTextView
    lateinit var autoCompleteSmoker: AutoCompleteTextView
    lateinit var autoCompleteNicotine: AutoCompleteTextView
    lateinit var autoCompleteExercise: AutoCompleteTextView
    lateinit var editSymptoms: EditText
    lateinit var editTrigger: EditText
    lateinit var othSymptoms: EditText
    lateinit var btn_Radio_Yes: RadioButton
    lateinit var btn_Radio_No: RadioButton
    lateinit var btn_Radio_Yes1: RadioButton
    lateinit var btn_Radio_No1: RadioButton
    lateinit var radiolayout: LinearLayout
    lateinit var btn_Radio_Yes2: RadioButton
    lateinit var btn_Radio_No2: RadioButton
    lateinit var btn_Radio_Yes3: RadioButton
    lateinit var btn_Radio_No3: RadioButton

    //var editethnicity: EditText? = null
    lateinit var editethnicity: EditText
    lateinit var rg_COPD: RadioGroup
    lateinit var rg_Astama: RadioGroup
    lateinit var rg_Yes: RadioGroup
    lateinit var rg_No: RadioGroup
    lateinit var rg_Yes1: RadioGroup
    lateinit var rg_No1: RadioGroup
    lateinit var rg_Yes2: RadioGroup
    lateinit var rg_No2: RadioGroup
    lateinit var rg_Yes3: RadioGroup
    lateinit var rg_No3: RadioGroup
    val numPattern: Pattern =
        Pattern.compile("^[+][0-9]{10,13}\$")


    lateinit var txtTitle: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.complete_your_profile)

        val back = findViewById<TextView>(R.id.Back)
        edituserId = findViewById(R.id.editUserID)
        profile_name = findViewById(R.id.profile_name)
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editmobilenumber1 = findViewById(R.id.editmobilenumber1)
        editselectyourage = findViewById(R.id.editslelectyourage)
        editselectyourCity = findViewById(R.id.editEnterYourAddress)
        editselectyourHeight = findViewById(R.id.editSelectYourHeight)
        editselectyourWeight = findViewById(R.id.editSelectYourWeight)
        editcontactemergency = findViewById(R.id.editmobilenumberforemergency)
        editSpecify = findViewById(R.id.editSpecify)
        editSpecify1 = findViewById(R.id.editSpecify1)
        observationformedical = findViewById(R.id.observationformedical)
        btn_Radio_Astama = findViewById(R.id.btn_Radio_Astama)
        btn_Radio_COPD = findViewById(R.id.btn_Radio_COPD)
        autoCompleteGender = findViewById(R.id.autoCompleteGender)
        autoCompleteEthnicity = findViewById(R.id.autoCompleteEthnicity)
        autoCompleteSmoker = findViewById(R.id.autoCompleteSmoker)
        autoCompleteNicotine = findViewById(R.id.autoCompleteNicotine)
        autoCompleteExercise = findViewById(R.id.autoCompleteExercise)
        editSymptoms = findViewById(R.id.symtoms1)
        editTrigger = findViewById(R.id.trigger)
        btn_Radio_Yes = findViewById(R.id.btn_Radio_Yes)
        btn_Radio_No = findViewById(R.id.btn_Radio_No)
        btn_Radio_Yes1 = findViewById(R.id.btn_Radio_Yes1)
        btn_Radio_No1 = findViewById(R.id.btn_Radio_No1)
        radiolayout = findViewById(R.id.otherthenastmaradio)
        btn_Radio_Yes2 = findViewById(R.id.btn_Radio_Yes2)
        btn_Radio_No2 = findViewById(R.id.btn_Radio_No2)
        btn_Radio_Yes3 = findViewById(R.id.btn_Radio_Yes3)
        btn_Radio_No3 = findViewById(R.id.btn_Radio_No3)
        editethnicity = findViewById(R.id.editethnicity)
        othSymptoms = findViewById(R.id.editsymtoms)
        rg_COPD = findViewById(R.id.rg_COPD)
        rg_Astama = findViewById(R.id.rg_Astama)
        rg_Yes = findViewById(R.id.rg_Yes)
        rg_No = findViewById(R.id.rg_No)
        rg_Yes1 = findViewById(R.id.rg_Yes1)
        textforotherthanastma = findViewById(R.id.textforotherthanastma)

        rg_No1 = findViewById(R.id.rg_No1)
        rg_Yes2 = findViewById(R.id.rg_Yes2)
        rg_No2 = findViewById(R.id.rg_No2)
        rg_Yes3 = findViewById(R.id.rg_Yes3)
        rg_No3 = findViewById(R.id.rg_No3)





        back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)

            var sharedPreferences: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putString("check_navigation", "")
            myEdit.putString("checkNav", "")
            myEdit.commit()


            startActivity(intent)
        }


        val lay1 = findViewById<LinearLayout>(R.id.lay1)
        val lay2 = findViewById<LinearLayout>(R.id.lay2)
        val lay3 = findViewById<LinearLayout>(R.id.lay3)


        lay1.visibility = View.VISIBLE
        lay2.visibility = View.GONE
        lay3.visibility = View.GONE


        // Layout 1 Button
        val btnNext1 = findViewById<Button>(R.id.btnNext1)


        // Layout 2 Button
        val btnNext2 = findViewById<Button>(R.id.btnNext2)


        // Layout 3 Button
        val btnNext3 = findViewById<Button>(R.id.btnNext3)


        val Medicalinfo = findViewById<TextView>(R.id.Medicalinfo)

        val Lifestyleinfo = findViewById<TextView>(R.id.Lifestyleinfo)

        val Profileinfo = findViewById<TextView>(R.id.Profileinfo)


        val butnprofileinfo = findViewById<Button>(R.id.butnprofileinfo)

        val btnmedicalinfo = findViewById<Button>(R.id.btnmedicalinfo)

        val btnlifestyleinfo = findViewById<Button>(R.id.btnlifestyleinfo)

        imageView = findViewById(R.id.profileimaglog)

        spinner1 = findViewById(R.id.spinner_ethnicity)


        val otl1 = View.OnTouchListener { v, event ->
            // the listener has consumed the event
            autoCompleteEthnicity.showDropDown()
            true
        }

        autoCompleteEthnicity.setOnTouchListener(otl1)



        autoCompleteEthnicity.setOnItemClickListener { parent, view, position, id ->


            val value = (parent).adapter.getItem(position)
            val index1 = id + 7
            val index2 = (parent).adapter.getItem(position)
            Log.d(TAG, "onCreate: +++++" + value)


            var idethnicity = index1


            //   Toast.makeText(getApplicationContext(),  ethnicity[position], Toast.LENGTH_LONG).show();

            if (value.equals("Other") || value.equals("Andet")) {
                val editethnicity = findViewById<EditText>(R.id.editethnicity)
                editethnicity.visibility = View.VISIBLE
            } else {
                val editethnicity = findViewById<EditText>(R.id.editethnicity)
                editethnicity.visibility = View.GONE
                editethnicity.text.clear()

            }


            var sharedPreferences: SharedPreferences =
                this@ProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)

            var myEdit = sharedPreferences.edit()
            myEdit.putString("Ethnicity", index1.toInt().toString())
            myEdit.commit()


        }
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                var idethnicity = spinner1
                    .selectedItemPosition + 1


                //   Toast.makeText(getApplicationContext(),  ethnicity[position], Toast.LENGTH_LONG).show();

                if (idethnicity == 5) {
                    val editethnicity = findViewById<EditText>(R.id.editethnicity)
                    editethnicity.visibility = View.VISIBLE
                } else {
                    val editethnicity = findViewById<EditText>(R.id.editethnicity)
                    editethnicity.visibility = View.GONE

                }


            }
        }


        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        Profileinfo.setOnClickListener {

            var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
            var myUserId = sh.getString("user_Id", "")
            Log.d(TAG, "onResponse: +++++++" + myUserId)


            var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
            var mylang = sh1.getString("Select_Lang", "")
            Log.d(TAG, "onResponse: " + mylang)


            var token = sh1.getString("bearer_token", "")
            Log.d(TAG, "onResume: ++++" + token)

            var lang = sh1.getString("Select_Lang", "").toString()


            if(lang.equals("")) {
                if (txtTitle.text == "Complete your profile(1/3)" || txtTitle.text == "Complete your profile(2/3)" || txtTitle.text == "Complete your profile(3/3)") {

                    lang = sh.getString("Select_Lang", "aa").toString()
                } else {
                    lang = sh.getString("Select_Lang", "da").toString()

                }
            }





            getProfileInfo(token!!, lang, myUserId!!.toInt())


            txtTitle.setText(R.string.complete_your_profile)
            Profileinfo.setTextColor(Color.parseColor("#000000"))
            butnprofileinfo.setBackgroundResource(R.drawable.profile_btn)
            lay1.visibility = View.VISIBLE
            lay2.visibility = View.GONE
            lay3.visibility = View.GONE

            Medicalinfo.setTextColor(Color.parseColor("#d3d3d3"))
            btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn_disable)

            Lifestyleinfo.setTextColor(Color.parseColor("#d3d3d3"))
            btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn_disable)


        }

        Medicalinfo.setOnClickListener {

            var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
            var myUserId = sh.getString("user_Id", "")
            Log.d(TAG, "onResponse: +++++++" + myUserId)


            var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
            var mylang = sh1.getString("Select_Lang", "")
            Log.d(TAG, "onResponse: " + mylang)


            var token = sh1.getString("bearer_token", "")
            Log.d(TAG, "onResume: ++++" + token)

            var lang = sh1.getString("Select_Lang", "").toString()


            if(lang.equals("")) {
                if (txtTitle.text == "Complete your profile(1/3)" || txtTitle.text == "Complete your profile(2/3)" || txtTitle.text == "Complete your profile(3/3)") {

                    lang = sh.getString("Select_Lang", "aa").toString()
                } else {
                    lang = sh.getString("Select_Lang", "da").toString()

                }
            }

            getMedicalInfoApi(token!!, lang!!, myUserId!!.toInt())


            Profileinfo.setTextColor(Color.parseColor("#d3d3d3"))
            butnprofileinfo.setBackgroundResource(R.drawable.profile_btn_disable)

            txtTitle.setText(R.string.complete_your_profile2)
            lay1.visibility = View.GONE
            lay2.visibility = View.VISIBLE
            lay3.visibility = View.GONE

            Medicalinfo.setTextColor(Color.parseColor("#000000"))
            btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn)


            Lifestyleinfo.setTextColor(Color.parseColor("#d3d3d3"))
            btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn_disable)


        }

        Lifestyleinfo.setOnClickListener {

            var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
            var myUserId = sh.getString("user_Id", "")
            Log.d(TAG, "onResponse: +++++++" + myUserId)


            var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
            var mylang = sh1.getString("Select_Lang", "")
            Log.d(TAG, "onResponse: " + mylang)


            var token = sh1.getString("bearer_token", "")
            Log.d(TAG, "onResume: ++++" + token)


            var lang = sh1.getString("Select_Lang", "")

            getLifestyleInfoApi(token!!, myUserId!!.toInt())

            Profileinfo.setTextColor(Color.parseColor("#d3d3d3"))
            butnprofileinfo.setBackgroundResource(R.drawable.profile_btn_disable)
            Medicalinfo.setTextColor(Color.parseColor("#d3d3d3"))
            btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn_disable)

            txtTitle.setText(R.string.complete_your_profile3)
            lay1.visibility = View.GONE
            lay2.visibility = View.GONE
            lay3.visibility = View.VISIBLE
            Lifestyleinfo.setTextColor(Color.parseColor("#000000"))
            btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn)


        }






        btnNext1.setOnClickListener {

            if (editcontactemergency.text.toString().trim().isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Enteryouremergencynumber)
                )
                editcontactemergency.requestFocus()
                return@setOnClickListener

            } else if (!numPattern.matcher(editcontactemergency.text.toString().trim()).matches()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.requriedplusesign)
                )
                return@setOnClickListener
            } else if (editmobilenumber1.text.toString().trim()
                    .equals(editcontactemergency.text.toString().trim())
            ) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.emergency_n_contact_cannot_be_same)
                )
                return@setOnClickListener
            } else if (editselectyourage.text.toString().trim().isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Enteryourage)
                )
                editselectyourage.requestFocus()
                return@setOnClickListener
            } else if (editselectyourHeight.text.toString().trim().isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Enteryourheight)
                )
                editselectyourHeight.requestFocus()
                return@setOnClickListener
            } else if (editselectyourWeight.text.toString().trim().isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Enteryourweight)
                )
                editselectyourWeight.requestFocus()
                return@setOnClickListener
            } else if (autoCompleteGender.text.toString().trim().isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Selectyourgender)
                )
                autoCompleteGender.requestFocus()
                return@setOnClickListener
            } else if (editselectyourCity.text.toString().trim().isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Enteryouraddress)
                )
                editselectyourCity.requestFocus()
                return@setOnClickListener
            } else if (autoCompleteEthnicity.text.toString().trim().isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Selectyourethnicity)
                )
                autoCompleteEthnicity.requestFocus()
                return@setOnClickListener

            } else if (autoCompleteEthnicity.text.toString().trim().equals("Other") ||
                autoCompleteEthnicity.text.toString().trim().equals("Andet")
            ) {
                if (editethnicity.text.toString().trim().isEmpty()) {
                    Utility.sharedInstance.showToastError(
                        this,
                        resources.getString(R.string.please_enter_ethnicity)
                    )
                    editethnicity.requestFocus()
                    return@setOnClickListener
                } else {


                    val p_user_id = edituserId.text.toString().trim()
                    val p_emergency_contact = editcontactemergency.text.toString().trim()
                    val p_age = editselectyourage.text.toString().trim()
                    val p_height = editselectyourHeight.text.toString().trim()
                    val p_weight = editselectyourWeight.text.toString().trim()
                    val p_city = editselectyourCity.text.toString().trim()
                    val p_ethnicity_specify = editethnicity.text.toString().trim()


                    var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                    var p_ethnicityId = sh.getString("Ethnicity", "")
                    var p_gender = sh.getString("Gender", "")


                    var profileInfoPostModel: ProfileInfoPostModel? = ProfileInfoPostModel()

                    profileInfoPostModel!!.user_id = p_user_id
                    profileInfoPostModel.date_of_birth = p_age
                    profileInfoPostModel.emergency_contact = p_emergency_contact
                    profileInfoPostModel.user_id = p_user_id
                    profileInfoPostModel.height = p_height.toIntOrNull()
                    profileInfoPostModel.weight = p_weight.toIntOrNull()
                    profileInfoPostModel.address = p_city
                    profileInfoPostModel.ethnicity_id = p_ethnicityId!!.toIntOrNull()
                    profileInfoPostModel.gender = p_gender
                    profileInfoPostModel.other_ethincity = p_ethnicity_specify


                    postProfileApi(profileInfoPostModel)

                    editPtofileInfo()

                    var sharedPreferences: SharedPreferences =
                        this@ProfileActivity.getSharedPreferences("Patient_age", MODE_PRIVATE)
                    var myEditGender = sharedPreferences.edit()
                    myEditGender.putInt("P_age", p_age.toInt())
                    myEditGender.putString("p_gender", p_gender)
                    myEditGender.apply()


                    Utility.sharedInstance.showProgressDialog(this)

                    txtTitle.setText(R.string.complete_your_profile2)
                    Profileinfo.setTextColor(Color.parseColor("#d3d3d3"))
                    butnprofileinfo.setBackgroundResource(R.drawable.profile_btn_disable)
                    lay1.visibility = View.GONE
                    lay2.visibility = View.VISIBLE
                    lay3.visibility = View.GONE

                    Medicalinfo.setTextColor(Color.parseColor("#000000"))
                    btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn)

                }
            } else {


                val p_user_id = edituserId.text.toString().trim()
                val p_emergency_contact = editcontactemergency.text.toString().trim()
                val p_age = editselectyourage.text.toString().trim()
                val p_height = editselectyourHeight.text.toString().trim()
                val p_weight = editselectyourWeight.text.toString().trim()
                val p_city = editselectyourCity.text.toString().trim()
                val p_ethnicity_specify = editethnicity.text.toString().trim()


                var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                var p_ethnicityId = sh.getString("Ethnicity", "")
                var p_gender = sh.getString("Gender", "")


                var sharedPreferences1: SharedPreferences =
                    this@ProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)
                var myphone = sharedPreferences1.edit()
                myphone.putString("Emergency", editcontactemergency.text.toString().trim())
                myphone.apply()


                var countryCodeemergency = sharedPreferences1.edit()
                countryCodeemergency.putString(
                    "CountyCodeEmergency",
                    editcontactemergency.text.toString().trim()
                )
                countryCodeemergency.apply()


                var profileInfoPostModel: ProfileInfoPostModel? = ProfileInfoPostModel()

                profileInfoPostModel!!.user_id = p_user_id
                profileInfoPostModel.date_of_birth = p_age
                profileInfoPostModel.emergency_contact = p_emergency_contact
                profileInfoPostModel.user_id = p_user_id
                profileInfoPostModel.height = p_height.toIntOrNull()
                profileInfoPostModel.weight = p_weight.toIntOrNull()
                profileInfoPostModel.address = p_city
                profileInfoPostModel.ethnicity_id = p_ethnicityId!!.toIntOrNull()
                profileInfoPostModel.gender = p_gender
                profileInfoPostModel.other_ethincity = p_ethnicity_specify


                postProfileApi(profileInfoPostModel)

                editPtofileInfo()

                var sharedPreferences: SharedPreferences =
                    this@ProfileActivity.getSharedPreferences("Patient_age", MODE_PRIVATE)
                var myEditGender = sharedPreferences.edit()
                myEditGender.putInt("P_age", p_age.toInt())
                myEditGender.putString("p_gender", p_gender)
                myEditGender.apply()


                Utility.sharedInstance.showProgressDialog(this)

                txtTitle.setText(R.string.complete_your_profile2)
                Profileinfo.setTextColor(Color.parseColor("#d3d3d3"))
                butnprofileinfo.setBackgroundResource(R.drawable.profile_btn_disable)
                lay1.visibility = View.GONE
                lay2.visibility = View.VISIBLE
                lay3.visibility = View.GONE

                Medicalinfo.setTextColor(Color.parseColor("#000000"))
                btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn)

            }


        }


//        val rg_COPD = findViewById<RadioGroup>(R.id.rg_COPD)

        //       val rg_Astama = findViewById<RadioGroup>(R.id.rg_Astama)

        btn_Radio_Astama.setOnClickListener {

            if (btn_Radio_COPD.isChecked)
                rg_COPD.clearCheck()

            if (btn_Radio_NONE.isChecked)
                rg_None.clearCheck()


            medDiagnosis = "Asthma"

            var sharedPreferences: SharedPreferences =
                this@ProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putString("Diagnosis", medDiagnosis)

            myEdit.apply()

        }

        btn_Radio_COPD.setOnClickListener {

            if (btn_Radio_Astama.isChecked)
                rg_Astama.clearCheck()

            if (btn_Radio_NONE.isChecked)
                rg_None.clearCheck()


            medDiagnosis = "COPD"

            var sharedPreferences: SharedPreferences =
                this@ProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putString("Diagnosis", medDiagnosis)

            myEdit.apply()


        }
        btn_Radio_NONE.setOnClickListener {

            if (btn_Radio_Astama.isChecked)
                rg_Astama.clearCheck()

            if (btn_Radio_COPD.isChecked)
                rg_COPD.clearCheck()


            medDiagnosis = "None"

            var sharedPreferences: SharedPreferences =
                this@ProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putString("Diagnosis", medDiagnosis)

            myEdit.apply()

        }


//        val btn_Radio_Yes = findViewById<RadioButton>(R.id.btn_Radio_Yes)

        // val btn_Radio_No = findViewById<RadioButton>(R.id.btn_Radio_No)


        btn_Radio_Yes.setOnClickListener {

            if (btn_Radio_No.isChecked)
                rg_No.clearCheck()

            editSpecify.visibility = View.VISIBLE

            val textquestion = findViewById<TextView>(R.id.textforotherthanastma)
            textquestion.visibility = View.VISIBLE



            radiolayout.visibility = View.VISIBLE
            othDiagnosis = true

        }

        btn_Radio_No.setOnClickListener {

            if (btn_Radio_Yes.isChecked)
                rg_Yes.clearCheck()

            editSpecify.visibility = View.GONE
            editSpecify.text.clear()
            editSpecify1.text.clear()


            val textquestion = findViewById<TextView>(R.id.textforotherthanastma)
            textquestion.visibility = View.GONE

            val radiolayout = findViewById<LinearLayout>(R.id.otherthenastmaradio)
            radiolayout.visibility = View.GONE

        }

        //   val btn_Radio_Yes1 = findViewById<RadioButton>(R.id.btn_Radio_Yes1)

        // val btn_Radio_No1 = findViewById<RadioButton>(R.id.btn_Radio_No1)

        btn_Radio_Yes1.setOnClickListener {

            if (btn_Radio_No1.isChecked)
                rg_No1.clearCheck()

            medMedication = true
            editSpecify1.visibility = View.VISIBLE

            textforotherthanastma.visibility = View.VISIBLE
        }

        btn_Radio_No1.setOnClickListener {

            if (btn_Radio_Yes1.isChecked)
                rg_Yes1.clearCheck()

            medMedication = false
            editSpecify1.visibility = View.GONE
        }


        // Get Symptom API Call
        getSymptomsData()

        // Get Triggers API Call
        getTriggersData()




        btnNext2.setOnClickListener {

            if (btn_Radio_Astama.isChecked == false && btn_Radio_COPD.isChecked == false
                && btn_Radio_NONE.isChecked == false
            ) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.PleaseselectyourDiagnosis)
                )
                return@setOnClickListener
            } else if (btn_Radio_Yes.isChecked == true) {
                if (editSpecify.text.toString().trim().isEmpty()) {
                    editSpecify.requestFocus()
                    Utility.sharedInstance.showToastError(
                        this,
                        resources.getString(R.string.pleaseenterspeficyfiled)
                    )
                    return@setOnClickListener
                } else {

                    if (btn_Radio_Yes1.isChecked == true) {
                        if (editSpecify1.text.toString().trim().isEmpty()) {
                            editSpecify1.requestFocus()
                            Utility.sharedInstance.showToastError(
                                this,
                                resources.getString(R.string.pleaseenterspeficyfiled)
                            )
                            return@setOnClickListener
                        } else {
                            if (editSymptoms.text.toString().trim().isEmpty()) {
                                editSymptoms.requestFocus()
                                Utility.sharedInstance.showToastError(
                                    this,
                                    resources.getString(R.string.Pleaseselectsymptoms)
                                )
                                return@setOnClickListener
                            } else if (editTrigger.text.toString().trim().isEmpty()) {
                                editTrigger.requestFocus()
                                Utility.sharedInstance.showToastError(
                                    this,
                                    resources.getString(R.string.PleaseselectTriggers)
                                )
                                return@setOnClickListener
                            } else {

                                var othDiagnosedSpecify = editSpecify.text.toString().trim()
                                var othDiagnosedSpecify1 = editSpecify1.text.toString().trim()
                                var medObservations = observationformedical.text.toString().trim()
                                var othsymptoms = othSymptoms.text.toString().trim()

                                var postMedicalInfo: PostMedicalInfo? = PostMedicalInfo()


                                var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
                                var myUserId = sh.getString("user_Id", "")
                                Log.d(TAG, "onResponse: +++++++" + myUserId)


                                postMedicalInfo!!.diagnosis = medDiagnosis
                                postMedicalInfo.other_diagnosed = othDiagnosis
                                postMedicalInfo.other_diagnosed_specify = othDiagnosedSpecify
                                postMedicalInfo.Medication = medMedication
                                postMedicalInfo.medication_specify = othDiagnosedSpecify1
                                postMedicalInfo.observations = medObservations
                                postMedicalInfo.symptoms = medSymtomList
                                postMedicalInfo.triggers = medTriggerList
                                postMedicalInfo.user_id = myUserId!!.toIntOrNull()
                                postMedicalInfo.other_symptoms = othsymptoms


                                Log.d(TAG, "onCreate: " + medTriggerList)


                                postMedicalAPI(postMedicalInfo)

                                editMedicalInfo()

                                Medicalinfo.setTextColor(Color.parseColor("#d3d3d3"))
                                btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn_disable)
                                Utility.sharedInstance.showProgressDialog(this)

                                txtTitle.setText(R.string.complete_your_profile3)
                                lay1.visibility = View.GONE
                                lay2.visibility = View.GONE
                                lay3.visibility = View.VISIBLE
                                Lifestyleinfo.setTextColor(Color.parseColor("#000000"))
                                btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn)

                            }
                        }
                    } else if (editSymptoms.text.toString().trim().isEmpty()) {
                        editSymptoms.requestFocus()
                        Utility.sharedInstance.showToastError(
                            this,
                            resources.getString(R.string.Pleaseselectsymptoms)
                        )
                        return@setOnClickListener
                    } else if (editTrigger.text.toString().trim().isEmpty()) {
                        editTrigger.requestFocus()
                        Utility.sharedInstance.showToastError(
                            this,
                            resources.getString(R.string.PleaseselectTriggers)
                        )
                        return@setOnClickListener
                    } else {

                        var othDiagnosedSpecify = editSpecify.text.toString().trim()
                        var othDiagnosedSpecify1 = editSpecify1.text.toString().trim()
                        var medObservations = observationformedical.text.toString().trim()
                        var othsymptoms = othSymptoms.text.toString().trim()

                        var postMedicalInfo: PostMedicalInfo? = PostMedicalInfo()


                        var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
                        var myUserId = sh.getString("user_Id", "")
                        Log.d(TAG, "onResponse: +++++++" + myUserId)


                        postMedicalInfo!!.diagnosis = medDiagnosis
                        postMedicalInfo.other_diagnosed = othDiagnosis
                        postMedicalInfo.other_diagnosed_specify = othDiagnosedSpecify
                        postMedicalInfo.Medication = medMedication
                        postMedicalInfo.medication_specify = othDiagnosedSpecify1
                        postMedicalInfo.observations = medObservations
                        postMedicalInfo.symptoms = medSymtomList
                        postMedicalInfo.triggers = medTriggerList
                        postMedicalInfo.user_id = myUserId!!.toIntOrNull()
                        postMedicalInfo.other_symptoms = othsymptoms


                        Log.d(TAG, "onCreate: " + medTriggerList)


                        postMedicalAPI(postMedicalInfo)

                        editMedicalInfo()

                        Medicalinfo.setTextColor(Color.parseColor("#d3d3d3"))
                        btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn_disable)
                        Utility.sharedInstance.showProgressDialog(this)

                        txtTitle.setText(R.string.complete_your_profile3)
                        lay1.visibility = View.GONE
                        lay2.visibility = View.GONE
                        lay3.visibility = View.VISIBLE
                        Lifestyleinfo.setTextColor(Color.parseColor("#000000"))
                        btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn)

                    }
                }


            } else if (editSymptoms.text.toString().trim().isEmpty()) {
                editSymptoms.requestFocus()
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Pleaseselectsymptoms)
                )
                return@setOnClickListener
            } else if (editTrigger.text.toString().trim().isEmpty()) {
                editTrigger.requestFocus()
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.PleaseselectTriggers)
                )
                return@setOnClickListener
            } else {

                var othDiagnosedSpecify = editSpecify.text.toString().trim()
                var othDiagnosedSpecify1 = editSpecify1.text.toString().trim()
                var medObservations = observationformedical.text.toString().trim()
                var othsymptoms = othSymptoms.text.toString().trim()

                var postMedicalInfo: PostMedicalInfo? = PostMedicalInfo()


                var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
                var myUserId = sh.getString("user_Id", "")
                Log.d(TAG, "onResponse: +++++++" + myUserId)


                postMedicalInfo!!.diagnosis = medDiagnosis
                postMedicalInfo.other_diagnosed = othDiagnosis
                postMedicalInfo.other_diagnosed_specify = othDiagnosedSpecify
                postMedicalInfo.Medication = medMedication
                postMedicalInfo.medication_specify = othDiagnosedSpecify1
                postMedicalInfo.observations = medObservations
                postMedicalInfo.symptoms = medSymtomList
                postMedicalInfo.triggers = medTriggerList
                postMedicalInfo.user_id = myUserId!!.toIntOrNull()
                postMedicalInfo.other_symptoms = othsymptoms


                Log.d(TAG, "onCreate: " + medTriggerList)


                postMedicalAPI(postMedicalInfo)

                editMedicalInfo()

                Medicalinfo.setTextColor(Color.parseColor("#d3d3d3"))
                btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn_disable)
                Utility.sharedInstance.showProgressDialog(this)

                txtTitle.setText(R.string.complete_your_profile3)
                lay1.visibility = View.GONE
                lay2.visibility = View.GONE
                lay3.visibility = View.VISIBLE
                Lifestyleinfo.setTextColor(Color.parseColor("#000000"))
                btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn)

            }
        }

        //  val btn_Radio_Yes2 = findViewById<RadioButton>(R.id.btn_Radio_Yes2)

        //val btn_Radio_No2 = findViewById<RadioButton>(R.id.btn_Radio_No2)

//        val rg_Yes2 = findViewById<RadioGroup>(R.id.rg_Yes2)
//
//        val rg_No2 = findViewById<RadioGroup>(R.id.rg_No2)

        btn_Radio_Yes2.setOnClickListener {

            rg_No2.clearCheck()

          //  if (btn_Radio_No2.isChecked)

                lifestyle_smoker = true

            autoCompleteSmoker.visibility = View.VISIBLE

        }

        btn_Radio_No2.setOnClickListener {


            rg_Yes2.clearCheck()

         //   if (btn_Radio_Yes2.isChecked)

                lifestyle_smoker = false

            autoCompleteSmoker.visibility = View.GONE
            autoCompleteSmoker.text.clear()

        }


        //  val btn_Radio_Yes3 = findViewById<RadioButton>(R.id.btn_Radio_Yes3)

        // val btn_Radio_No3 = findViewById<RadioButton>(R.id.btn_Radio_No3)

//        val rg_Yes3 = findViewById<RadioGroup>(R.id.rg_Yes3)
//
//        val rg_No3 = findViewById<RadioGroup>(R.id.rg_No3)

        btn_Radio_Yes3.setOnClickListener {
            rg_No3.clearCheck()

            if (btn_Radio_No3.isChecked)

            lifestyle_tocaccoornictoin = true

            val spinner_smoker = findViewById<Spinner>(R.id.spinner_smoker1)
            spinner_smoker.visibility = View.GONE
            autoCompleteNicotine.visibility = View.VISIBLE
        }

        btn_Radio_No3.setOnClickListener {

            rg_Yes3.clearCheck()

            if (btn_Radio_Yes3.isChecked)

            lifestyle_tocaccoornictoin = false
            val spinner_smoker = findViewById<Spinner>(R.id.spinner_smoker1)
            spinner_smoker.visibility = View.GONE
            autoCompleteNicotine.visibility = View.GONE
            autoCompleteNicotine.text.clear()
        }


        btnNext3.setOnClickListener {

            if (btn_Radio_Yes2.isChecked == true) {
                if (autoCompleteSmoker.text.toString().trim().isEmpty()) {
                    Utility.sharedInstance.showToastError(
                        this,
                        resources.getString(R.string.please_select_below_field)
                    )
                    autoCompleteSmoker.requestFocus()
                    return@setOnClickListener
                } else {
                    if (btn_Radio_Yes3.isChecked == true) {
                        if (autoCompleteNicotine.text.toString().trim().isEmpty()) {
                            Utility.sharedInstance.showToastError(
                                this,
                                resources.getString(R.string.please_select_below_field)
                            )
                            autoCompleteNicotine.requestFocus()
                            return@setOnClickListener
                        } else if (autoCompleteExercise.text.toString().trim().isEmpty()) {
                            Utility.sharedInstance.showToastError(
                                this,
                                resources.getString(R.string.Selectyourexercise)
                            )
                            autoCompleteExercise.requestFocus()
                            return@setOnClickListener
                        } else {


                            var postLifeStyleInfoModel: PostLifeStyleInfoModel? =
                                PostLifeStyleInfoModel()

                            var sh = getSharedPreferences("Briota", MODE_PRIVATE)

                            var L_tobaccofrequency = sh.getString("Tobacco", "")
                            var L_smoke = sh.getString("Smoker", "")
                            var L_exercise = sh.getString("Exercise", "")


                            var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
                            var myUserId = uId.getString("user_Id", "")!!.toInt()
                            Log.d(TAG, "onResponse: +++++++" + myUserId)



                            postLifeStyleInfoModel!!.isSmoker = lifestyle_smoker
                            postLifeStyleInfoModel.tobaccoNicotine = lifestyle_tocaccoornictoin
                            postLifeStyleInfoModel.smokingFrequency = L_smoke
                            postLifeStyleInfoModel.tobaccoFrequency = L_tobaccofrequency
                            postLifeStyleInfoModel.exercise = L_exercise
                            postLifeStyleInfoModel.userId = myUserId


                            postLifestyleApi(postLifeStyleInfoModel)

                            editLifestyleInfo()

                            Utility.sharedInstance.showProgressDialog(this)
                            val intent = Intent(this, HomeActivity::class.java)

                            var sharedPreferences: SharedPreferences =
                                this.getSharedPreferences("Briota", MODE_PRIVATE)
                            var myEdit = sharedPreferences.edit()
                            myEdit.putString("check_navigation", "")
                            myEdit.putString("checkNav", "")
                            myEdit.commit()

                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
            if (btn_Radio_Yes3.isChecked == true) {
                if (autoCompleteNicotine.text.toString().trim().isEmpty()) {
                    Utility.sharedInstance.showToastError(
                        this,
                        "Please enter nicotine specified field"
                    )
                    autoCompleteNicotine.requestFocus()
                    return@setOnClickListener
                } else if (autoCompleteExercise.text.toString().trim().isEmpty()) {
                    Utility.sharedInstance.showToastError(
                        this,
                        resources.getString(R.string.Selectyourexercise)
                    )
                    autoCompleteExercise.requestFocus()
                    return@setOnClickListener
                } else {


                    var postLifeStyleInfoModel: PostLifeStyleInfoModel? = PostLifeStyleInfoModel()

                    var sh = getSharedPreferences("Briota", MODE_PRIVATE)

                    var L_tobaccofrequency = sh.getString("Tobacco", "")
                    var L_smoke = sh.getString("Smoker", "")
                    var L_exercise = sh.getString("Exercise", "")


                    var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
                    var myUserId = uId.getString("user_Id", "")!!.toInt()
                    Log.d(TAG, "onResponse: +++++++" + myUserId)



                    postLifeStyleInfoModel!!.isSmoker = lifestyle_smoker
                    postLifeStyleInfoModel.tobaccoNicotine = lifestyle_tocaccoornictoin
                    postLifeStyleInfoModel.smokingFrequency = L_smoke
                    postLifeStyleInfoModel.tobaccoFrequency = L_tobaccofrequency
                    postLifeStyleInfoModel.exercise = L_exercise
                    postLifeStyleInfoModel.userId = myUserId


                    postLifestyleApi(postLifeStyleInfoModel)

                    editLifestyleInfo()

                    Utility.sharedInstance.showProgressDialog(this)
                    val intent = Intent(this, HomeActivity::class.java)

                    var sharedPreferences: SharedPreferences =
                        this.getSharedPreferences("Briota", MODE_PRIVATE)
                    var myEdit = sharedPreferences.edit()
                    myEdit.putString("check_navigation", "")
                    myEdit.putString("checkNav", "")
                    myEdit.commit()

                    startActivity(intent)
                    finish()
                }
            } else if (autoCompleteExercise.text.toString().trim().isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Selectyourexercise)
                )
                autoCompleteExercise.requestFocus()
                return@setOnClickListener
            } else {


                var postLifeStyleInfoModel: PostLifeStyleInfoModel? = PostLifeStyleInfoModel()

                var sh = getSharedPreferences("Briota", MODE_PRIVATE)

                var L_tobaccofrequency = sh.getString("Tobacco", "")
                var L_smoke = sh.getString("Smoker", "")
                var L_exercise = sh.getString("Exercise", "")


                var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
                var myUserId = uId.getString("user_Id", "")!!.toInt()
                Log.d(TAG, "onResponse: +++++++" + myUserId)



                postLifeStyleInfoModel!!.isSmoker = lifestyle_smoker
                postLifeStyleInfoModel.tobaccoNicotine = lifestyle_tocaccoornictoin
                postLifeStyleInfoModel.smokingFrequency = L_smoke
                postLifeStyleInfoModel.tobaccoFrequency = L_tobaccofrequency
                postLifeStyleInfoModel.exercise = L_exercise
                postLifeStyleInfoModel.userId = myUserId


                postLifestyleApi(postLifeStyleInfoModel)

                editLifestyleInfo()

                Utility.sharedInstance.showProgressDialog(this)
                val intent = Intent(this, HomeActivity::class.java)

                var sharedPreferences: SharedPreferences =
                    this.getSharedPreferences("Briota", MODE_PRIVATE)
                var myEdit = sharedPreferences.edit()
                myEdit.putString("check_navigation", "")
                myEdit.putString("checkNav", "")
                myEdit.commit()

                startActivity(intent)
                finish()
            }
        }


        val Gender = resources.getStringArray(R.array.Gender)
        spinner = findViewById<Spinner>(R.id.spinner_gender)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdowngender, Gender)
        spinner.adapter = arrayAdapter
        autoCompleteGender.setAdapter(arrayAdapter)


        val otl = View.OnTouchListener { v, event ->
            // the listener has consumed the event
            autoCompleteGender.showDropDown()
            true
        }

        autoCompleteGender.setOnTouchListener(otl)



        autoCompleteGender.setOnItemClickListener { parent, view, position, id ->


            val value = (parent).adapter.getItem(position)
            val index1 = id
            val index2 = (parent).adapter.getItem(position)
            Log.d(TAG, "onCreate: +++++" + value)

            var sharedPreferences: SharedPreferences =
                this@ProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)

            var myEdit = sharedPreferences.edit()
            myEdit.putString("Gender", value.toString())
            myEdit.putInt("IdGender", index1.toInt())
            myEdit.commit()


        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var idgender = spinner.selectedItem
                var genderPosition = position


            }
        }


        var smoker = resources.getStringArray(R.array.smoker)
        val spinner4 = findViewById<Spinner>(R.id.spinner_smoker)
        val arrayAdapter4 = ArrayAdapter(this, R.layout.dropdowngender, smoker)
        spinner4.adapter = arrayAdapter4
        autoCompleteSmoker.setAdapter(arrayAdapter4)

        val otl2 = View.OnTouchListener { v, event ->
            // the listener has consumed the event
            autoCompleteSmoker.showDropDown()
            true
        }

        autoCompleteSmoker.setOnTouchListener(otl2)



        autoCompleteSmoker.setOnItemClickListener { parent, view, position, id ->


            val value = (parent).adapter.getItem(position)
            val index1 = id
            val index2 = (parent).adapter.getItem(position)
            Log.d(TAG, "onCreate: +++++" + value)


            var sharedPreferences: SharedPreferences =
                this@ProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)

            var myEdit = sharedPreferences.edit()
            myEdit.putString("Smoker", value.toString())
            myEdit.commit()

        }


        spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var idsmoker = spinner4.selectedItem

            }
        }


        val smoker1 = resources.getStringArray(R.array.smoker1)
        val spinner5 = findViewById<Spinner>(R.id.spinner_smoker1)
        val arrayAdapter5 = ArrayAdapter(this, R.layout.dropdowngender, smoker1)
        spinner5.adapter = arrayAdapter5
        autoCompleteNicotine.setAdapter(arrayAdapter5)

        val otl3 = View.OnTouchListener { v, event ->
            // the listener has consumed the event
            autoCompleteNicotine.showDropDown()
            true
        }

        autoCompleteNicotine.setOnTouchListener(otl3)



        autoCompleteNicotine.setOnItemClickListener { parent, view, position, id ->


            val value = (parent).adapter.getItem(position)
            val index1 = id
            val index2 = (parent).adapter.getItem(position)
            Log.d(TAG, "onCreate: +++++" + value)


            var sharedPreferences: SharedPreferences =
                this@ProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)

            var myEdit = sharedPreferences.edit()
            myEdit.putString("Tobacco", value.toString())
            myEdit.commit()

        }

        spinner5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var idfrequecy = spinner5.selectedItem


            }
        }


        val smokerfrequency = resources.getStringArray(R.array.smokerfrequency)
        val spinner6 = findViewById<Spinner>(R.id.spinner_smokerfrequency)
        val arrayAdapter6 = ArrayAdapter(this, R.layout.dropdowngender, smokerfrequency)
        spinner6.adapter = arrayAdapter6
        autoCompleteExercise.setAdapter(arrayAdapter6)

        val otl4 = View.OnTouchListener { v, event ->
            autoCompleteExercise.showDropDown()
            true
        }

        autoCompleteExercise.setOnTouchListener(otl4)



        autoCompleteExercise.setOnItemClickListener { parent, view, position, id ->


            val value = (parent).adapter.getItem(position)
            val index1 = id
            val index2 = (parent).adapter.getItem(position)
            Log.d(TAG, "onCreate: +++++" + value)


            var sharedPreferences: SharedPreferences =
                this@ProfileActivity.getSharedPreferences("Briota", MODE_PRIVATE)

            var myEdit = sharedPreferences.edit()
            myEdit.putString("Exercise", value.toString())
            myEdit.commit()


        }


        spinner6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var idexerice = spinner6.selectedItem


            }
        }



    }


    private fun editMedicalInfo() {

        var othDiagnosedSpecify = editSpecify.text.toString().trim()
        var othDiagnosedSpecify1 = editSpecify1.text.toString().trim()
        var medObservations = observationformedical.text.toString().trim()
        var othsymptoms = othSymptoms.text.toString().trim()

        var editMedicalInfo: EditMedicalInfo? = EditMedicalInfo()


        var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = sh.getString("user_Id", "")!!.toInt()
        Log.d(TAG, "onResponse: +++++++" + myUserId)

        var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh1.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + newtoken)



        editMedicalInfo!!.diagnosis = medDiagnosis
        editMedicalInfo.other_diagnosed = btn_Radio_Yes.isChecked
        editMedicalInfo.other_diagnosed_specify = othDiagnosedSpecify
        editMedicalInfo.Medication = btn_Radio_Yes1.isChecked
        editMedicalInfo.medication_specify = othDiagnosedSpecify1
        editMedicalInfo.observations = medObservations
        if (medSymtomList.isNotEmpty()) {
            editMedicalInfo.symptoms = medSymtomList
        }
        if (medTriggerList.isNotEmpty()) {
            editMedicalInfo.triggers = medTriggerList
        }
        editMedicalInfo.user_id = myUserId
        editMedicalInfo.other_symptoms = othsymptoms

        Log.d(TAG, "onCreate: " + medTriggerList)

        var sh2 = getSharedPreferences("Briota", MODE_PRIVATE)
        var lang = sh2.getString("Select_Lang", "")

        if(lang.equals(""))
        {
            if(txtTitle.text == "Complete your profile(2/3)")
            {
                lang = sh2.getString("Select_Lang", "aa")
            }
            else
            {
                lang = sh2.getString("Select_Lang", "da")
            }
        }
        Log.d(TAG, "editPtofileInfo: " + lang)


        RetrofitClient.getRetrofitInstance.putMedicalInfo(
            "Bearer " + newtoken,
            editMedicalInfo,
            lang!!,
            myUserId
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(TAG, "Failed::" + t)

                    Utility.sharedInstance.dismissProgressDialog()
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Log.d(TAG, "++++++++++++++++++++++++++++++++" + response.toString())
                    Log.d(TAG, "++++++++++" + SignUpResponseModel())
                    if (response.code() == 200) {


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
                                .equals("An account with the given email already exists.")
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@ProfileActivity,
                                "" + response.body()
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                }
            })


    }

    private fun editLifestyleInfo() {

        var editLifestyleInfo: EditLifestyleInfo? = EditLifestyleInfo()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)

        var L_tobaccofrequency = sh.getString("Tobacco", "")
        var L_smoke = sh.getString("Smoker", "")
        var L_exercise = sh.getString("Exercise", "")


        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = uId.getString("user_Id", "")!!.toInt()
        Log.d(TAG, "onResponse: +++++++" + myUserId)

        var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh1.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + newtoken)


        if (btn_Radio_Yes2.isChecked == true) {
            editLifestyleInfo!!.is_smoker = true
        }
        if (btn_Radio_No2.isChecked == true) {
            editLifestyleInfo!!.is_smoker = false
        }
        if (btn_Radio_Yes3.isChecked == true) {
            editLifestyleInfo!!.tobacco_nicotine = true
        }
        if (btn_Radio_No3.isChecked == true) {
            editLifestyleInfo!!.tobacco_nicotine = false
        }
        if (L_smoke!!.isNotEmpty()) {
            editLifestyleInfo!!.tobacco_frequency = L_tobaccofrequency
        }
        if (L_tobaccofrequency!!.isNotEmpty()) {
            editLifestyleInfo!!.smoking_frequency = L_smoke
        }
        if (L_exercise!!.isNotEmpty()) {
            editLifestyleInfo!!.exercise = L_exercise
        }
        editLifestyleInfo!!.user_id = myUserId

        var lang = sh.getString("Select_Lang", "")
        Log.d(TAG, "editPtofileInfo: " + lang)


        RetrofitClient.getRetrofitInstance.putLifestyleInfo(
            "Bearer $newtoken",
            editLifestyleInfo,
            myUserId
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(TAG, "Failed::" + t)

                    Utility.sharedInstance.dismissProgressDialog()
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Log.d(TAG, "++++++++++++++++++++++++++++++++" + response.toString())
                    Log.d(TAG, "++++++++++" + SignUpResponseModel())
                    if (response.code() == 200) {


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
                                .equals("An account with the given email already exists.")
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@ProfileActivity,
                                "" + response.body()
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                }
            })
    }

    private fun editPtofileInfo() {

        var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = sh.getString("user_Id", "")
        Log.d(TAG, "onResponse: +++++++" + myUserId)


        var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
        var p_ethnicityId = sh1.getString("Ethnicity", "")
        var p_gender = sh1.getString("Gender", "")
        var newtoken = sh1.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + newtoken)


        val editProfileInfoModel: EditProfileInfoModel = EditProfileInfoModel()

        val p_user_id = edituserId.text.toString().trim()
        val p_emergency_contact = editcontactemergency.text.toString().trim()
        val p_age = editselectyourage.text.toString().trim()
        val p_height = editselectyourHeight.text.toString().trim()
        val p_weight = editselectyourWeight.text.toString().trim()
        val p_city = editselectyourCity.text.toString().trim()
        val p_ethnicity_specify = editethnicity.text.toString().trim()

        editProfileInfoModel.user_id = myUserId!!.toInt()
        editProfileInfoModel.date_of_birth = p_age.toInt()
        editProfileInfoModel.emergency_contact = p_emergency_contact

        editProfileInfoModel.height = p_height.toIntOrNull()
        editProfileInfoModel.weight = p_weight.toIntOrNull()
        editProfileInfoModel.address = p_city
        editProfileInfoModel.ethnicity_id = p_ethnicityId!!.toIntOrNull()
        editProfileInfoModel.gender = p_gender
        editProfileInfoModel.other_ethincity = p_ethnicity_specify
        Log.d(TAG, "editPtofileInfo: " + p_ethnicity_specify)
        Log.d(TAG, "editPtofileInfo: " + editProfileInfoModel.other_ethincity)

        var sh2 = getSharedPreferences("Briota", MODE_PRIVATE)
        var lang = sh2.getString("Select_Lang", "")
        Log.d(TAG, "editPtofileInfo: " + lang)

        RetrofitClient.getRetrofitInstance.putProfileInfo(
            "Bearer $newtoken",
            editProfileInfoModel,
            lang!!,
            myUserId
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(TAG, "Failed::" + t)

                    Utility.sharedInstance.dismissProgressDialog()
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Log.d(TAG, "++++++++++++++++++++++++++++++++" + response.toString())
                    Log.d(TAG, "++++++++++" + SignUpResponseModel())
                    if (response.code() == 200) {


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
                                .equals("An account with the given email already exists.")
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@ProfileActivity,
                                "" + response.body()
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                }
            })

    }

    private fun postMedicalAPI(postMedicalInfo: PostMedicalInfo) {

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + newtoken)

        var gson = Gson()
        var obj = JSONObject()
        var json = gson.toJson(postMedicalInfo)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.medicalInfoPostApi("Bearer " + newtoken, postMedicalInfo)
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
                            var gson: Gson = Gson()
                            var json: String = gson.toJson(response.body())

                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t
                            )
                        }
                        Utility.sharedInstance.dismissProgressDialog()
                        if (response.body().toString()
                                .equals("An account with the given email already exists.")
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@ProfileActivity,
                                "" + response.body()
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                }
            })

    }

    private fun postLifestyleApi(postLifeStyleInfoModel: PostLifeStyleInfoModel) {

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + newtoken)

        var gson = Gson()
        var obj = JSONObject()
        var json = gson.toJson(postLifeStyleInfoModel)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.lifestyleInfoPostApi(
            "Bearer " + newtoken,
            postLifeStyleInfoModel
        )
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
                            var gson: Gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var postLifestyleRequestModel: PostLifestyleRequestModel =
                                gson.fromJson(json, PostLifestyleRequestModel::class.java)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t
                            )
                        }
                        Utility.sharedInstance.dismissProgressDialog()
                        if (response.body().toString()
                                .equals("An account with the given email already exists.")
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@ProfileActivity,
                                "" + response.body()
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }

                    }
                }
            })


    }

    fun postProfileApi(profileInfoPostModel: ProfileInfoPostModel) {

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var newtoken = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + newtoken)


        var gson = Gson()
        var obj = JSONObject()
        var json = gson.toJson(profileInfoPostModel)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.profilePostApi(
            "Bearer " + newtoken,
            profileInfoPostModel
        )
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


                        //Get Triggers API Call
                        getTriggersData()
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
                                .equals("An account with the given email already exists.")
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@ProfileActivity,
                                "" + response.body()
                            )
                        }

                    }
                    if (response.code() == 400) {
                        getTriggersData()
                    } else {
                        response.errorBody()?.let { Log.d("error", it.string()) }
                    }
                }
            })

    }

    private fun getTriggersData() {

        var patient_Gender: String

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + token)

        var age = getSharedPreferences("Patient_age", MODE_PRIVATE)
        var patientGender = age.getInt("P_age", 0)
        var patient_gender = age.getString("p_gender", "")
        var pat_gender = age.getString("Patient_Gender", "")
        Log.d(TAG, "getTriggersData: " + patient_gender)

        Log.d(TAG, "getTriggersData: " + patientGender)

        var lang = sh.getString("Select_Lang", "").toString()

        if(lang.equals("")) {
            if (txtTitle.text == "Complete your profile(1/3)" || txtTitle.text == "Complete your profile(2/3)" || txtTitle.text == "Complete your profile(3/3)") {

                lang = sh.getString("Select_Lang", "aa").toString()
            } else {
                lang = sh.getString("Select_Lang", "da").toString()

            }
        }


        if (patient_gender.equals("")) {
            patient_Gender = pat_gender!!
        } else {
            patient_Gender = patient_gender!!
        }


        RetrofitClient.getRetrofitInstance.getTriggerApi(
            token!!,
            lang!!,
            patient_Gender,
            patientGender
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {

                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {

                    if (response.code() == 200) {
                        try {

                            var gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var getTriggerResponseModel: GetTriggerResponseModel =
                                gson.fromJson(json, GetTriggerResponseModel::class.java)


                            setTriggersData(getTriggerResponseModel)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }


                    }


                }


            })


    }


    fun getSymptomsData() {


        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + token)

        var lang = sh.getString("Select_Lang", "").toString()

        if(lang!!.equals("")) {
            if (txtTitle.text == "Complete your profile(1/3)" || txtTitle.text == "Complete your profile(2/3)" || txtTitle.text == "Complete your profile(3/3)") {

                lang = sh.getString("Select_Lang", "aa").toString()
            } else {
                lang = sh.getString("Select_Lang", "da").toString()

            }
        }


        RetrofitClient.getRetrofitInstance.getSymptomsApi("Bearer " + token!!, lang)
            .enqueue(object : Callback<Any> {


                override fun onResponse(call: Call<Any>, response: Response<Any>) {

                    if (response.code() == 200) {

                        Log.d(TAG, "onResponse: +++++++" + response.body())
                        //       Utility.sharedInstance.showToastSuccess(this@ProfileActivity,"Success")


                        try {

                            var gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var getSymptomsResponseModel: GetSymptomsResponseModel =
                                gson.fromJson(json, GetSymptomsResponseModel::class.java)


                            setSymptomsData(getSymptomsResponseModel)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

                    }


                }

                override fun onFailure(call: Call<Any>, t: Throwable) {

                }


            })

    }


    fun getEthnicityData(userlang: String) {

        RetrofitClient.getRetrofitInstance.getEthnicityApi(userlang)
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {

                    if (response.code() == 200) {

                        Log.d(TAG, "onResponse: +++++++" + response.body())
                        //       Utility.sharedInstance.showToastSuccess(this@ProfileActivity,"Success")


                        try {

                            var gson = Gson()
                            var json: String = gson.toJson(response.body())


                            var getEthnicityResponseModel: GetEthnicityResponseModel =
                                gson.fromJson(json, GetEthnicityResponseModel::class.java)

                            setEthnicityData(getEthnicityResponseModel)

                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {


                    }
                }
            })


    }

    private fun setEthnicityData(getEthnicityResponseModel: GetEthnicityResponseModel) {

        var nameList = getEthnicityResponseModel.Description!!

        val arr = arrayOfNulls<String>(nameList.size)

        for (i in nameList.indices) {
            arr[i] = getEthnicityResponseModel.Description[i].name
        }

        print(arr)

        var arrayAdapter1 = ArrayAdapter(this@ProfileActivity, R.layout.dropdowngender, arr)
        spinner1.adapter = arrayAdapter1
        autoCompleteEthnicity.setAdapter(arrayAdapter1)

    }

    private fun setSymptomsData(symptomsResponseModel: GetSymptomsResponseModel) {

        var textView: TextView? = null
        lateinit var selectedsymtoms: BooleanArray
        var symtomsList = java.util.ArrayList<Int>()


        var symptomList = symptomsResponseModel.Description!!

        val symptomArray = arrayOfNulls<String>(symptomList.size)


        for (i in symptomList.indices) {
            symptomArray[i] = symptomsResponseModel.Description[i].name
        }


        // initialize selected language array
        selectedsymtoms = BooleanArray(symptomArray.size)

        //  textView = findViewById(R.id.symtoms1) as EditText


//        val otlsymptoms = View.OnTouchListener { v, event ->
//            // the listener has consumed the event
//
//            true
//        }
//
//        editSymptoms.setOnTouchListener(otlsymptoms)
//

        val checkedItems = BooleanArray(symptomArray.size)
        editSymptoms.setOnClickListener(View.OnClickListener { // Initial // ize alert dialog


            val builder =
                android.app.AlertDialog.Builder(this@ProfileActivity, R.style.Theme_AppCompat_Light)

            // set title
            builder.setTitle(resources.getString(R.string.select_your_symptoms))
            // set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                symptomArray, selectedsymtoms
            ) { dialogInterface, i, b ->
                // check condition
                if (b) {
                    // when checkbox selected
                    // Add position in lang list
                    symtomsList.add(i)
                    medSymtomList.add(i + 1)

                    // Sort array list
                    Collections.sort(symtomsList)


                } else {
                    // when checkbox unselected
                    // Remove position from symtomsList
                    symtomsList.remove(Integer.valueOf(i))
                    medSymtomList.remove(Integer.valueOf(i + 1))

                }

                val editsymtoms = findViewById<EditText>(R.id.editsymtoms)
                editsymtoms.visibility = View.GONE

            }
            builder.setPositiveButton(
                resources.getString(R.string.ok)
            ) { dialogInterface, i -> // Initialize string builder
                val stringBuilder = StringBuilder()
                // use for loop
                for (j in symtomsList.indices) {
                    // concat array value
                    stringBuilder.append(symptomArray[symtomsList[j]])
                    // check condition
                    if (j != symtomsList.size - 1) {
                        // When j value not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ")
                    }

                    if (stringBuilder.contains("Others") || stringBuilder.contains("Andre"))
                    //  if (j != symtomsList.indexOf(10))
                    {
                        val editsymtoms = findViewById<EditText>(R.id.editsymtoms)
                        editsymtoms.visibility = View.VISIBLE
                    } else {
                        val editsymtoms = findViewById<EditText>(R.id.editsymtoms)
                        editsymtoms.visibility = View.GONE
                    }

                }
                // set text on textView
                editSymptoms.setText(stringBuilder.toString())
            }
            builder.setNegativeButton(
                resources.getString(R.string.cancle)
            ) { dialogInterface, i -> // dismiss dialog
                dialogInterface.dismiss()

                val editsymtoms = findViewById<EditText>(R.id.editsymtoms)
                editsymtoms.visibility = View.GONE
            }
            builder.setNeutralButton(
                resources.getString(R.string.ClearAll)
            ) { dialogInterface, i ->
                // use for loop
                for (j in selectedsymtoms.indices) {
                    // remove all selection
                    selectedsymtoms[j] = false
                    // clear language list
                    symtomsList.clear()
                    medSymtomList.clear()
                    // clear text view value
                    editSymptoms.setText("")
                }

                val editsymtoms = findViewById<EditText>(R.id.editsymtoms)
                editsymtoms.visibility = View.GONE

            }


            // show dialog
            builder.show()
        })


    }

    fun setTriggersData(triggerResponseModel: GetTriggerResponseModel) {


        var textView1: TextView? = null
        lateinit var selectedTrigger: BooleanArray
        var TriggerList = java.util.ArrayList<Int>()

//        var  TriggerArray =resources.getStringArray(R.array.trigger)

        var triggerList = triggerResponseModel.Description

        val triggerArray = arrayOfNulls<String>(triggerList!!.size)

        for (i in triggerList.indices) {
            triggerArray[i] = triggerResponseModel.Description!![i].name
        }

// initialize selected language array
        selectedTrigger = BooleanArray(triggerArray.size)

        // textView1 = findViewById(R.id.trigger) as TextView

        editTrigger.setOnClickListener(View.OnClickListener { // Initialize alert dialog


            val builder =
                android.app.AlertDialog.Builder(this@ProfileActivity, R.style.Theme_AppCompat_Light)

            // set title
            builder.setTitle(resources.getString(R.string.select_your_triggers))

            // set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                triggerArray, selectedTrigger
            ) { dialogInterface, i, b ->
                // check condition
                if (b) {
                    // when checkbox selected
                    // Add position in lang list
                    TriggerList.add(i)
                    medTriggerList.add(i + 1)
                    // Sort array list
                    Collections.sort(TriggerList)
                } else {
                    // when checkbox unselected
                    // Remove position from langList
                    TriggerList.remove(Integer.valueOf(i))
                    medTriggerList.remove(Integer.valueOf(i + 1))
                }
            }
            builder.setPositiveButton(
                resources.getString(R.string.ok)
            ) { dialogInterface, i -> // Initialize string builder
                val stringBuilder = StringBuilder()
                // use for loop
                for (j in TriggerList.indices) {
                    // concat array value
                    stringBuilder.append(triggerArray[TriggerList[j]])
                    // check condition
                    if (j != TriggerList.size - 1) {
                        // When j value not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ")
                    }

                }
                // set text on textView
                editTrigger.setText(stringBuilder.toString())
            }
            builder.setNegativeButton(
                resources.getString(R.string.cancle)
            ) { dialogInterface, i -> // dismiss dialog
                dialogInterface.dismiss()
            }
            builder.setNeutralButton(
                resources.getString(R.string.ClearAll)
            ) { dialogInterface, i ->
                // use for loop
                for (j in selectedTrigger.indices) {
                    // remove all selection
                    selectedTrigger[j] = false
                    // clear language list
                    TriggerList.clear()
                    medTriggerList.clear()
                    // clear text view value
                    editTrigger.setText("")
                }
            }
            // show dialog
            builder.show()
        })


    }


    override fun onResume() {
        super.onResume()


        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + token)

        var lang = sh.getString("Select_Lang", "").toString()


        if(lang.equals("")) {
            if (txtTitle.text == "Complete your profile(1/3)" || txtTitle.text == "Complete your profile(2/3)" || txtTitle.text == "Complete your profile(3/3)") {

                lang = sh.getString("Select_Lang", "aa").toString()
            } else {
                lang = sh.getString("Select_Lang", "da").toString()

            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            getProfile(token!!)
        }

        CoroutineScope(Dispatchers.IO).launch {
            getEthnicityData(lang)
        }

        // working for the only one time

//        if (autoCompleteEthnicity.text.toString().isNotEmpty()) {
//
//
//        }
//        else  {
//            txtTitle.setText(R.string.complete_your_profile2)
//            Profileinfo.setTextColor(Color.parseColor("#d3d3d3"))
//            butnprofileinfo.setBackgroundResource(R.drawable.profile_btn_disable)
//            lay1.visibility = View.GONE
//            lay2.visibility = View.VISIBLE
//            lay3.visibility = View.GONE
//            Medicalinfo.setTextColor(Color.parseColor("#000000"))
//            btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn)
//
//        }
//     if   (btn_Radio_Astama.isChecked == false && btn_Radio_COPD.isChecked == false){
//
//         Medicalinfo.setTextColor(Color.parseColor("#d3d3d3"))
//         btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn_disable)
//         txtTitle.setText(R.string.complete_your_profile3)
//         lay1.visibility = View.GONE
//         lay2.visibility = View.GONE
//         lay3.visibility = View.VISIBLE
//         Lifestyleinfo.setTextColor(Color.parseColor("#000000"))
//         btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn)
//
//
//     }

        //   if ((editSpecify.text.toString().isNotEmpty()) && (autoCompleteEthnicity.text.toString().isNotEmpty())){

        //   }
//        else  {
//            Medicalinfo.setTextColor(Color.parseColor("#d3d3d3"))
//            btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn_disable)
//            txtTitle.setText(R.string.complete_your_profile3)
//            lay1.visibility = View.GONE
//            lay2.visibility = View.GONE
//            lay3.visibility = View.VISIBLE
//            Lifestyleinfo.setTextColor(Color.parseColor("#000000"))
//            btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn)
//
//        }


//        if (autoCompleteExercise.text.toString().trim().isNotEmpty()) {
//
//        } else
//        {
//
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }


    }


    fun getProfile(token: String) {

        RetrofitClient.getRetrofitInstance.getProfileApi("Bearer " + token)
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

                            var getProfileData: GetProfileData =
                                gson.fromJson(json, GetProfileData::class.java)


                            setDataUser(getProfileData)

                            var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
                            var myUserId = sh.getString("user_Id", "")
                            Log.d(TAG, "onResponse: +++++++" + myUserId)


                            var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
                            var mylang = sh1.getString("Select_Lang", "")
                            Log.d(TAG, "onResponse: " + mylang)

                            var lang: String?
                            if(txtTitle.text == "Complete your profile(1/3)" || txtTitle.text == "Complete your profile(2/3)" || txtTitle.text == "Complete your profile(3/3)") {

                                lang = sh.getString("Select_Lang", "aa").toString()
                            }
                            else
                            {
                                lang = sh.getString("Select_Lang", "da").toString()

                            }


                            var navigate = sh1.getString("check_navigation", "")
                            var navigate1 = sh1.getString("checkNav", "")


                            if (navigate!!.equals("navigation")) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    getAllPatientDetails(token, lang, myUserId!!.toInt())
                                    Log.d(TAG, "onRespone ${Thread.currentThread()}")
                                }
                            } else if (navigate1!!.equals("navigation")) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    getAllPatientDetails(token, lang, myUserId!!.toInt())
                                    Log.d(TAG, "onRespone ${Thread.currentThread()}")

                                }  } else {

                             CoroutineScope(Dispatchers.IO).launch {
                                  getProfileInfo(token, lang, myUserId!!.toInt())

                             }
                                Log.d(TAG, "onRespone ${Thread.currentThread()}")

                                CoroutineScope(Dispatchers.IO).launch {
                                    getMedicalInfoApi(token, lang, myUserId!!.toInt())

                                }
                                Log.d(TAG, "onRespone ${Thread.currentThread()}")

                                CoroutineScope(Dispatchers.IO).launch {
                                    getLifestyleInfoApi(token, myUserId!!.toInt())

                                }
                                Log.d(TAG, "onRespone ${Thread.currentThread()}")

                            }
                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@ProfileActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }

    private fun getAllPatientDetails(token: String?, mylang: String, myId: Int) {

        RetrofitClient.getRetrofitInstance.getPatientPlanDetailsAPI("Bearer " + token, mylang, myId)
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


                            Log.d(TAG, "onResponse: " + json)

                            var getPatientAllDetailsResponseModel: GetPatientAllDetailsResponseModel =
                                gson.fromJson(json, GetPatientAllDetailsResponseModel::class.java)

                            Log.d(TAG, "onResponse: " + getPatientAllDetailsResponseModel)


                            setPatientDetails(getPatientAllDetailsResponseModel)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@ProfileActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }

    private fun setPatientDetails(patientAllDetailsResponseModel: GetPatientAllDetailsResponseModel) {

        if (patientAllDetailsResponseModel.Profile_Data!!.status == false) {
            txtTitle.setText(R.string.complete_your_profile)
            Profileinfo.setTextColor(Color.parseColor("#000000"))
            butnprofileinfo.setBackgroundResource(R.drawable.profile_btn)
            lay1.visibility = View.VISIBLE
            lay2.visibility = View.GONE
            lay3.visibility = View.GONE

            Medicalinfo.setTextColor(Color.parseColor("#d3d3d3"))
            btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn_disable)

            Lifestyleinfo.setTextColor(Color.parseColor("#d3d3d3"))
            btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn_disable)

        } else if (patientAllDetailsResponseModel.Medical_Data!!.status == false) {
            Profileinfo.setTextColor(Color.parseColor("#d3d3d3"))
            butnprofileinfo.setBackgroundResource(R.drawable.profile_btn_disable)

            txtTitle.setText(R.string.complete_your_profile2)
            lay1.visibility = View.GONE
            lay2.visibility = View.VISIBLE
            lay3.visibility = View.GONE

            Medicalinfo.setTextColor(Color.parseColor("#000000"))
            btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn)


            Lifestyleinfo.setTextColor(Color.parseColor("#d3d3d3"))
            btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn_disable)

        } else if (patientAllDetailsResponseModel.Lifestyle_Data!!.status == false) {
            Profileinfo.setTextColor(Color.parseColor("#d3d3d3"))
            butnprofileinfo.setBackgroundResource(R.drawable.profile_btn_disable)
            Medicalinfo.setTextColor(Color.parseColor("#d3d3d3"))
            btnmedicalinfo.setBackgroundResource(R.drawable.profile_btn_disable)

            txtTitle.setText(R.string.complete_your_profile3)
            lay1.visibility = View.GONE
            lay2.visibility = View.GONE
            lay3.visibility = View.VISIBLE
            Lifestyleinfo.setTextColor(Color.parseColor("#000000"))
            btnlifestyleinfo.setBackgroundResource(R.drawable.profile_btn)

        } else {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

            var sharedPreferences: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putString("check_navigation", "")
            myEdit.putString("checkNav", "")
            myEdit.commit()

            finish()
        }

    }


    private fun setDataUser(getProfileData: GetProfileData) {

        profile_name.text =
            getProfileData.data!![0].first_name + " " + getProfileData.data[0].last_name
        editName.setText(getProfileData.data[0].first_name + " " + getProfileData.data[0].last_name)
        editEmail.setText(getProfileData.data[0].email)
        editmobilenumber1.setText(getProfileData.data[0].phone_number)
        edituserId.setText(getProfileData.data[0].user_id!!.toString())


        var sharedPreferences: SharedPreferences =
            this@ProfileActivity.getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myId = sharedPreferences.edit()
        myId.putString("user_Id", getProfileData.data[0].user_id!!.toString())
        myId.putString(
            "user_name",
            getProfileData.data[0].first_name + " " + getProfileData.data[0].last_name
        )

        myId.apply()


    }

    private fun getProfileInfo(token: String, mylang: String, myUserId: Int) {

        RetrofitClient.getRetrofitInstance.getProfileInfoApi("Bearer " + token, mylang, myUserId)
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

                            var getProfileInfoData: GetProfileInfoData =
                                gson.fromJson(json, GetProfileInfoData::class.java)

                            Log.d(TAG, "onResponse: +++++" + getProfileInfoData)


                            var gender = getProfileInfoData.data!!.gender

                            var sharedPreferences: SharedPreferences =
                                this@ProfileActivity.getSharedPreferences(
                                    "Patient_age",
                                    MODE_PRIVATE
                                )
                            var myEditGender = sharedPreferences.edit()
                            myEditGender.putString("Patient_Gender", gender)
                            myEditGender.apply()






                            setPatientData(getProfileInfoData)

                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@ProfileActivity,
                            "Get Profile Info not called"
                        )


                    }
                }
            })


    }

    private fun getMedicalInfoApi(token: String, mylang: String, myUserId: Int) {


        RetrofitClient.getRetrofitInstance.getMedicalInfoApi("Bearer " + token, mylang, myUserId)
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

                            var getMedicalData: GetMedicalData =
                                gson.fromJson(json, GetMedicalData::class.java)

                            Log.d(TAG, "onResponse: +++++" + getMedicalData)


                            setMedicalData(getMedicalData)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@ProfileActivity,
                            "Get Profile Info not called"
                        )


                    }
                }
            })


    }

    private fun setMedicalData(medicalData: GetMedicalData) {

        observationformedical.setText(medicalData.data!!.observations)

        val symtom_str: String = java.lang.String.join(",", medicalData.data.symptoms)
        val trigger_str: String = java.lang.String.join(",", medicalData.data.triggers)

        editSymptoms.setText(symtom_str)
        editTrigger.setText(trigger_str)



        if (medicalData.data.symptoms!!.contains("Others") || medicalData.data.symptoms!!.contains("Andre")) {
            othSymptoms.visibility = View.VISIBLE

            othSymptoms.setText(medicalData.data.other_symptoms)
        }
        if (medicalData.data.diagnosis!!.equals("Asthma")) {
            btn_Radio_Astama.isChecked = true

        } else if (medicalData.data.diagnosis!!.equals("COPD")) {
            btn_Radio_COPD.isChecked = true
        } else {
            btn_Radio_NONE.isChecked = true
        }
        if (medicalData.data.other_diagnosed == true) {

            btn_Radio_Yes.isChecked = true
            rg_No.clearCheck()
            editSpecify.visibility = View.VISIBLE
            editSpecify.setText(medicalData.data.other_diagnosed_specify)
            radiolayout.visibility = View.VISIBLE

            if (medicalData.data.Medication == true) {
                btn_Radio_Yes1.isChecked = true
                rg_No1.clearCheck()

                textforotherthanastma.visibility = View.VISIBLE
                editSpecify1.visibility = View.VISIBLE
                editSpecify1.setText(medicalData.data.medication_specify)
            } else {
                rg_Yes1.clearCheck()

                textforotherthanastma.visibility = View.GONE
                btn_Radio_No1.isChecked = true
                editSpecify1.visibility = View.GONE

            }

        } else {
            rg_Yes.clearCheck()
            btn_Radio_No.isChecked = true
            editSpecify.visibility = View.GONE
            radiolayout.visibility = View.GONE

        }


    }

    private fun getLifestyleInfoApi(token: String, myUserId: Int) {

        RetrofitClient.getRetrofitInstance.getLifestyleInfoApi("Bearer " + token, myUserId)
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

                            var getLifestyleData: GetLifestyleData =
                                gson.fromJson(json, GetLifestyleData::class.java)

                            Log.d(TAG, "onResponse: +++++" + getLifestyleData)


                            setLifestyleData(getLifestyleData)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@ProfileActivity,
                            "Get Profile Info not called"
                        )


                    }
                }
            })


    }

    private fun setLifestyleData(lifestyleData: GetLifestyleData) {

        if (lifestyleData.data!!.is_smoker == true) {

            btn_Radio_Yes2.isChecked = true
            btn_Radio_No2.isChecked = false
            autoCompleteSmoker.visibility = View.VISIBLE
            autoCompleteSmoker.setText(lifestyleData.data.smoking_frequency, false)
        } else {
            btn_Radio_Yes2.isChecked = false
            btn_Radio_No2.isChecked = true
            autoCompleteSmoker.visibility = View.GONE

        }
        if (lifestyleData.data.tobacco_nicotine == true) {
            btn_Radio_Yes3.isChecked = true
            btn_Radio_No3.isChecked = false
            autoCompleteNicotine.visibility = View.VISIBLE
            autoCompleteNicotine.setText(lifestyleData.data.tobacco_frequency, false)

        } else {
            btn_Radio_Yes3.isChecked = false
            btn_Radio_No3.isChecked = true
            autoCompleteNicotine.visibility = View.GONE

        }
        autoCompleteExercise.setText(lifestyleData.data.exercise.toString(), false)
        Log.d(TAG, "setLifestyleData: " + lifestyleData.data.exercise)


    }

    private fun setPatientData(getProfileInfoData: GetProfileInfoData) {

        editcontactemergency.setText(getProfileInfoData.data!!.emergency_contact)
        editselectyourage.setText(getProfileInfoData.data.date_of_birth.toString())
        editselectyourHeight.setText(getProfileInfoData.data.height)
        editselectyourWeight.setText(getProfileInfoData.data.weight)
        editselectyourCity.setText(getProfileInfoData.data.address)
        autoCompleteGender.setText(getProfileInfoData.data.gender, false)
        autoCompleteEthnicity.setText(getProfileInfoData.data.ethnicity_id, false)
        if (getProfileInfoData.data.ethnicity_id.equals("Other") ||
            getProfileInfoData.data.ethnicity_id.equals("Andet")
        ) {
            editethnicity.visibility = View.VISIBLE
            editethnicity.setText(getProfileInfoData.data.other_ethincity)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }

    }
}




