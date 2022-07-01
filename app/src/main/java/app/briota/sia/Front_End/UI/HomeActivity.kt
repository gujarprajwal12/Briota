package app.briota.sia.Front_End.UI

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.GetActionPlanListModel
import app.briota.sia.integration_layer.models.User_Detail.AirQualityResponseModel.AirQualityResponseModel
import app.briota.sia.integration_layer.models.User_Detail.AllergyOutlookResponseModel.AllergyOutlookResponseModel
import app.briota.sia.integration_layer.models.User_Detail.GetPatientPeakReading
import app.briota.sia.integration_layer.models.User_Detail.GetProfileData
import app.briota.sia.integration_layer.models.User_Detail.GetProfileInfoData
import app.briota.sia.integration_layer.models.User_Detail.WeatherResponseModel.WeatherResponseModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.usercentrics.sdk.*
import eu.findair.findairble.managers.FindAirSDK
import eu.findair.findairble.utils.FindAirSDKBuilder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {


    lateinit var navprofile: ImageView

    lateinit var textaddyourdailyzone: TextView

    lateinit var profiletoolbar: ImageView

    lateinit var currentwethercard: CardView
    lateinit var current_Air_Quality: CardView
    lateinit var current_Allergy_Outlook: CardView
    lateinit var emmergencynumber: Button
    lateinit var emmergencyambulancenumber: Button
    var REQUEST_PHONE_CALL = 1
    lateinit var person_name: TextView
    lateinit var btngreen: Button
    lateinit var btnyellow: Button
    lateinit var btnred: Button
    lateinit var sos: ImageView
    private lateinit var layout: View

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private var latitudeLabel: String? = null
    private var longitudeLabel: String? = null
    private var latitudeText: TextView? = null
    private var longitudeText: TextView? = null

    private var banner: UsercentricsBanner? = null
    var myLatitude: String? = null
    var myLongitude: String? = null


    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    var dialog: Dialog? = null


    var findAirSDK: FindAirSDK? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val options = UsercentricsOptions(settingsId = "I7Q9Y7kpm")
        Usercentrics.initialize(this, options)



        initView()
        onClick()
    }


    fun initView() {

        navprofile = findViewById(R.id.nav_profile)
        textaddyourdailyzone = findViewById(R.id.textaddyourdailyzone)
        currentwethercard = findViewById(R.id.currentwethercard)
        current_Air_Quality = findViewById(R.id.current_Air_Quality)
        current_Allergy_Outlook = findViewById(R.id.current_Allergy_Outlook)
        emmergencynumber = findViewById(R.id.emmergencynumber)
        emmergencyambulancenumber = findViewById(R.id.emmergencyambulancenumber)
        person_name = findViewById(R.id.person_name)
        btngreen = findViewById(R.id.btngreen)
        btnyellow = findViewById(R.id.btnyellow)
        btnred = findViewById(R.id.btnred)
        sos = findViewById(R.id.sos)

        var sh = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var user = sh.getString("user_name", "")

        latitudeLabel = resources.getString(R.string.latitudeBabel)
        longitudeLabel = resources.getString(R.string.longitudeBabel)
        latitudeText = findViewById<View>(R.id.latitudeText) as TextView
        longitudeText = findViewById<View>(R.id.longitudeText) as TextView
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    fun onClick() {

        daily_zone_card.setOnClickListener {



            var intent = Intent(this, MyDailyZoneActivity::class.java)
            startActivity(intent)
            Utility.sharedInstance.showProgressDialog(this)

        }

        emmergencynumber.setOnClickListener {


            callemmergencynumber()
            //checkPhonePermission()

        }

        emmergencyambulancenumber.setOnClickListener {


            callemmergencyambulancenumber()
            //checkPhonePermission()
        }

        sos.setOnClickListener {



            try {

                dialog = Dialog(this)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setCancelable(false)
                dialog!!.setContentView(R.layout.sos)
                dialog!!.show()


                var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
                var emergernyphone = sh1.getString("Emergency", "")
                Log.d(TAG, "onClick: " + emergernyphone)

                var butYes: Button = dialog!!.findViewById(R.id.butYEs)
                var butNo: Button = dialog!!.findViewById(R.id.butNO)
                var tv_alert: TextView = dialog!!.findViewById(R.id.tv_alert)
                tv_alert.text = resources.getString(R.string.Areyousure)
                var sosnumber1: TextView = dialog!!.findViewById(R.id.sosnumber1)
                sosnumber1.text = emergernyphone



                butNo.setOnClickListener {
                    dialog!!.dismiss()
                }
                butYes.setOnClickListener {
                    Utility.sharedInstance.showProgressDialog(this)
                    callPhone()

                    dialog!!.dismiss()

                }

            } catch (e: Exception) {
                Log.d(TAG, "onClick: " + e)
            }

        }

        allactionplan_card.setOnClickListener {

//            var intent = Intent( this, MyActionPlanActivity::class.java)
//            startActivity(intent)
//            Utility.sharedInstance.showProgressDialog(this)
        }



        btngreen.setOnClickListener {



            var intent = Intent(this, AddImageActivity::class.java)
            startActivity(intent)
            Utility.sharedInstance.showProgressDialog(this)
        }

        btnyellow.setOnClickListener {


            var intent = Intent(this, AddImageActivity::class.java)
            startActivity(intent)
            Utility.sharedInstance.showProgressDialog(this)
        }

        btnred.setOnClickListener {


            var intent = Intent(this, AddImageActivity::class.java)
            startActivity(intent)
            Utility.sharedInstance.showProgressDialog(this)
        }


        back_tol.setOnClickListener {


            val intent = Intent(this, PatientProfileActivity::class.java)
            startActivity(intent)
        }

        textaddyourdailyzone.setOnClickListener {


            var intent = Intent(this, MyDailyZoneActivity::class.java)
            startActivity(intent)
            Utility.sharedInstance.showProgressDialog(this)

        }


        current_Allergy_Outlook.setOnClickListener {

            checkPermissionlocationforallegy(
                Manifest.permission.ACCESS_FINE_LOCATION,
                REQUEST_PERMISSIONS_REQUEST_CODE
            )


        }

        currentwethercard.setOnClickListener {


            checkPermissionlocationforweather(
                Manifest.permission.ACCESS_FINE_LOCATION,
                REQUEST_PERMISSIONS_REQUEST_CODE
            )


        }


        current_Air_Quality.setOnClickListener {

            checkPermissionlocationforairquality(
                Manifest.permission.ACCESS_FINE_LOCATION,
                REQUEST_PERMISSIONS_REQUEST_CODE
            )

        }


    }

    private fun checkPermissionlocationforallegy(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {

            currentAllergyOutlook()

        }
    }

    private fun checkPermissionlocationforweather(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {

            currentWeatherAPI()

        }
    }

    private fun checkPermissionlocationforairquality(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            currentAirQualityAPI()

        }
    }

    fun callPhone() {

        var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)

        var emergernyphone = sh1.getString("Emergency", "")

        val intent = Intent(Intent.ACTION_DIAL)
        //intent.data = Uri.parse("tel: " + txt_phone.text.toString() )
        intent.data = Uri.parse("tel: " + emergernyphone.toString().trim())
        startActivity(intent)

        Log.d(TAG, "callPhone: " + emergernyphone)

        Log.d(TAG, "callPhone: " + intent.data)


        var emergencycountrycode = sh1.getString("CountyCodeEmergency ", "")
        Log.d(TAG, "callPhone: " + emergencycountrycode)

    }


    fun callemmergencynumber() {


        val intent = Intent(Intent.ACTION_DIAL)
        //intent.data = Uri.parse("tel: " + txt_phone.text.toString() )
        intent.data = Uri.parse("tel: " + 1813)
        startActivity(intent)

    }

    fun callemmergencyambulancenumber() {

        val intent = Intent(Intent.ACTION_DIAL)
        //intent.data = Uri.parse("tel: " + txt_phone.text.toString() )
        intent.data = Uri.parse("tel: " + 112)
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()


        Usercentrics.isReady({ status ->
            if (status.shouldCollectConsent) {
                showFirstLayer()
            } else {

//                Utility.sharedInstance.showToastError(
//                    this@HomeActivity,
//                    "you deney the request "
//                )



            }
        }, { error ->


            // Handle non-localized error
        })

        if (!isNetworkAvailable == true) {

            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width =
                WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.common_dialog)
            dialog!!.show()

            var butYes: Button = dialog!!.findViewById(R.id.butYEs)
            var butNo: Button = dialog!!.findViewById(R.id.butNO)
            var tv_alert: TextView = dialog!!.findViewById(R.id.tv_alert)
            var txtDialogMessage: TextView = dialog!!.findViewById(R.id.txtDialogMessage)
            butYes.text = "Ok"
            butNo.visibility = View.GONE
            tv_alert.text = "Internet Connection"
            txtDialogMessage.text = "Please Check Your Internet Connection"

            butYes.setOnClickListener {
                dialog!!.dismiss()
            }

        }

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + token)

        var lang = sh.getString("Select_Lang", "").toString()

        var sh2 = getSharedPreferences("OneTime", MODE_PRIVATE)
        var oneTimeview = sh2.getString("oneTimeView","")

        if(oneTimeview.equals("OneTime")) {
            createToastFunc()

            var sharedPreferences1: SharedPreferences =
                this.getSharedPreferences("OneTime", MODE_PRIVATE)
            var myEdit1 = sharedPreferences1.edit()
            myEdit1.clear()
            myEdit1.commit()


        }
        else
        {
          //  Utility.sharedInstance.showToastError(this,"Error")
        }


        getProfile(token!!)

        var sh5 = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myuserId = sh5.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + myuserId)

        getSpiroReading(token, myuserId)


        getActionPlanList(token, myuserId)

        Utility.sharedInstance.dismissProgressDialog()


    }




    private fun showFirstLayer(
        layout: UsercentricsLayout = UsercentricsLayout.Popup(PopupPosition.BOTTOM),
        settings: BannerSettings? = null
    ) {
        // Launch Usercentrics Banner with your settings
        banner = UsercentricsBanner(this, settings).also {
            it.showFirstLayer(
                layout = layout,
                callback = ::handleUserResponse
            )
        }
    }

    private fun handleUserResponse(userResponse: UsercentricsConsentUserResponse?) {
        userResponse ?: return

        println("Consents -> ${userResponse.consents}")
        println("User Interaction -> ${userResponse.userInteraction}")
        println("Controller Id -> ${userResponse.controllerId}")


        //applyConsent(userResponse.consents)

    }

    private fun applyConsent(consents: List<UsercentricsServiceConsent>) {

        Utility.sharedInstance.showToastError(
            this@HomeActivity,
            "you deny the request "
        )
        // https://docs.usercentrics.com/cmp_in_app_sdk/latest/apply_consent/apply-consent/#apply-consent-to-each-service
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

                            Utility.sharedInstance.dismissProgressDialog()


                            var sh1 = getSharedPreferences("Briota", MODE_PRIVATE)
                            var mylang = sh1.getString("Select_Lang", "")

                            if(mylang!!.equals("")) {
                                if (textaddyourdailyzone.text == "My Daily Zone is") {
                                    mylang = sh1.getString("Select_Lang", "aa")
                                } else {
                                    mylang = sh1.getString("Select_Lang", "da")

                                }
                            }
                            Log.d(TAG, "onResponse: " + mylang)

                            getProfileInfo(token, mylang!!, myUserId!!.toInt())


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@HomeActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }

    private fun setDataUser(getProfileData: GetProfileData) {


        var sharedPreferences: SharedPreferences =
            this.getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myId = sharedPreferences.edit()
        myId.putString("onlyname", getProfileData.data!![0].first_name)
        myId.putString("user_Id", getProfileData.data[0].user_id!!.toString())
        myId.putString(
            "user_name",
            getProfileData.data[0].first_name + " " + getProfileData.data[0].last_name
        )
        myId.apply()


        var sh1 = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var nameofclient = sh1.getString("onlyname", "")
        Log.d(TAG, "initView: " + nameofclient)
        person_name.text = nameofclient

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

                            Log.d(TAG, "onResponse: " + json)
                            Log.d(TAG, "onResponse: " + getPatientPeakReading)

                            if (getPatientPeakReading.data!![0].dailyZone.equals("Green")) {
                                textaddyourdailyzone.setText(R.string.my_daily_zone_is)
                                zone_icon.setImageResource(R.drawable.greensymextrasmall)
                            }
                            if (getPatientPeakReading.data!![0].dailyZone.equals("Yellow")) {
                                textaddyourdailyzone.setText(R.string.my_daily_zone_is)
                                zone_icon.setImageResource(R.drawable.yellowextrasmall)
                            }
                            if (getPatientPeakReading.data!![0].dailyZone.equals("Red")) {
                                textaddyourdailyzone.setText(R.string.my_daily_zone_is)
                                zone_icon.setImageResource(R.drawable.redsymextrasmall)
                            }

                            Utility.sharedInstance.dismissProgressDialog()

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

    private fun getActionPlanList(newtoken: String, userId: Int) {

        RetrofitClient.getRetrofitInstance.geActionPlanList("Bearer " + newtoken, userId)
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

                            var getActionplanListModel: GetActionPlanListModel =
                                gson.fromJson(json, GetActionPlanListModel::class.java)



                            if (getActionplanListModel.data!![0].doctor_id == 0) {
                                All_Action_Plans.setText(R.string.created_by_myself)
                                creator_dash_date.text =
                                    getActionplanListModel.data!![0].date!!.substring(0, 10)
                            } else {
                                All_Action_Plans.text =
                                    resources.getString(R.string.created_by_Dr) + " " + getActionplanListModel.data!![0].doctor_name
                            }


                            try {

                                val dateStr = getActionplanListModel.data!![0].date
                                val df =
                                    SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.ENGLISH)
                                df.timeZone = TimeZone.getTimeZone("UTC")
                                val date: Date? = df.parse(dateStr)
                                df.timeZone = TimeZone.getDefault()
                                val df3 = SimpleDateFormat("dd-MM-yyyy hh:mm a")
                                val formattedDate: String = df3.format(date)
                                creator_dash_date.text = formattedDate

                            } catch (e: Exception) {
                                Log.d(ContentValues.TAG, "setReading: " + e)
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
                            this@HomeActivity,
                            response.body().toString()
                        )


                    }
                }
            })


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
                                this@HomeActivity.getSharedPreferences("Patient_age", MODE_PRIVATE)
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
                            this@HomeActivity,
                            "Get Profile Info not called"
                        )


                    }
                }
            })


    }

    private fun setPatientData(getProfileInfoData: GetProfileInfoData) {


        var sharedPreferences1: SharedPreferences =
            this@HomeActivity.getSharedPreferences("Briota", MODE_PRIVATE)
        var myphone = sharedPreferences1.edit()
        myphone.putString("Emergency", getProfileInfoData.data!!.emergency_contact)
        myphone.apply()


    }

    val isNetworkAvailable: Boolean
        @SuppressLint("ServiceCast")
        get() {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val capabilities =
                        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    if (capabilities != null) {
                        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            return true
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            return true
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                            return true
                        }
                    }
                }
            }
            return false
        }


    public override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions()
            }
        }
        else {
            getLastLocation()
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result

                myLatitude = lastLocation!!.latitude.toString()
                myLongitude = lastLocation!!.longitude.toString()

                Log.d(TAG, "getLastLocation: " + myLatitude)
                Log.d(TAG, "getLastLocation: " + myLongitude)

                latitudeText!!.text = latitudeLabel + ": " + myLatitude
                longitudeText!!.text = longitudeLabel + ": " + myLongitude

            } else {
                Log.w(TAG, "getLastLocation:exception", task.exception)
                showMessage("No location detected. Make sure location is enabled on the device.")
            }
        }
    }

    private fun showMessage(string: String) {
        val container = findViewById<View>(R.id.linearLayout)
        if (container != null) {

        }
    }



    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this@HomeActivity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

                View.OnClickListener {
                    startLocationPermissionRequest()
                }
        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }
                else -> {

                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                Build.DISPLAY, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }

                }
            }
        }
    }


    private fun currentWeatherAPI() {


        Log.d(TAG, "currentWeatherAPI: " + myLatitude)
        Log.d(TAG, "currentWeatherAPI: " + myLongitude)

        try {
            RetrofitClient.getRetrofitInstance.getCurrentWeather(
                "fd2662c2703d3f13606076adcbd08d631163fb16645b896b67825b666733163a",
                myLatitude!!, myLongitude!!
            )
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if (response.code() == 200) {
                            var gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var weatherResponseModel: WeatherResponseModel =
                                gson.fromJson(json, WeatherResponseModel::class.java)

                            Log.d(TAG, "onResponse: " + weatherResponseModel)


                            setWeatherData(weatherResponseModel)
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Utility.sharedInstance.showToastError(this@HomeActivity, "Failed")
                    }
                })
        } catch (t: Throwable) {
            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width =
                WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.common_dialog)
            dialog!!.show()

            var butYes: Button = dialog!!.findViewById(R.id.butYEs)
            var butNo: Button = dialog!!.findViewById(R.id.butNO)
            var tv_alert: TextView = dialog!!.findViewById(R.id.tv_alert)
            var txtDialogMessage: TextView = dialog!!.findViewById(R.id.txtDialogMessage)
            butYes.text = resources.getString(R.string.ok1)
            butNo.visibility = View.GONE
            tv_alert.text = resources.getString(R.string.location)
            txtDialogMessage.text =
                resources.getString(R.string.PleasegotoSettingsandturnonthepermissions)

            butYes.setOnClickListener {
                var sharedPreferences1: SharedPreferences =
                    this.getSharedPreferences("OneTime", MODE_PRIVATE)
                var myEdit1 = sharedPreferences1.edit()
                myEdit1.clear()
                myEdit1.commit()

                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),0)
                dialog!!.dismiss()
            }
        }


    }


    private fun currentAirQualityAPI() {


        try {
            RetrofitClient.getRetrofitInstance.getAirQuality(
                "fd2662c2703d3f13606076adcbd08d631163fb16645b896b67825b666733163a",
                myLatitude!!, myLongitude!!
            )
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if (response.code() == 200) {
                            var gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var airQualityResponseModel: AirQualityResponseModel =
                                gson.fromJson(json, AirQualityResponseModel::class.java)

                            Log.d(TAG, "onResponse: " + airQualityResponseModel)

                            setAirQualityData(airQualityResponseModel)
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Utility.sharedInstance.showToastError(this@HomeActivity, "Failed")
                    }
                })
        } catch (t: Throwable) {
            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width =
                WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.common_dialog)
            dialog!!.show()

            var butYes: Button = dialog!!.findViewById(R.id.butYEs)
            var butNo: Button = dialog!!.findViewById(R.id.butNO)
            var tv_alert: TextView = dialog!!.findViewById(R.id.tv_alert)
            var txtDialogMessage: TextView = dialog!!.findViewById(R.id.txtDialogMessage)
            butYes.text = resources.getString(R.string.ok1)
            butNo.visibility = View.GONE
            tv_alert.text = resources.getString(R.string.location)
            txtDialogMessage.text =
                resources.getString(R.string.PleasegotoSettingsandturnonthepermissions)

            butYes.setOnClickListener {

                var sharedPreferences1: SharedPreferences =
                    this.getSharedPreferences("OneTime", MODE_PRIVATE)
                var myEdit1 = sharedPreferences1.edit()
                myEdit1.clear()
                myEdit1.commit()

                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),0)
                dialog!!.dismiss()
            }
        }


    }


    private fun currentAllergyOutlook() {
        try {


            RetrofitClient.getRetrofitInstance.getAllergyOutlook(
                "fd2662c2703d3f13606076adcbd08d631163fb16645b896b67825b666733163a",
                myLatitude!!, myLongitude!!
            )
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if (response.code() == 200) {

                            var gson = Gson()
                            var json: String = gson.toJson(response.body())

                            var allergyOutlookResponseModel: AllergyOutlookResponseModel =
                                gson.fromJson(json, AllergyOutlookResponseModel::class.java)

                            Log.d(TAG, "onResponse: " + allergyOutlookResponseModel)

                            setAllergyOutlookData(allergyOutlookResponseModel)
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Utility.sharedInstance.showToastError(this@HomeActivity, "Failed")
                    }
                })
        } catch (t: Throwable) {
            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width =
                WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.common_dialog)
            dialog!!.show()

            var butYes: Button = dialog!!.findViewById(R.id.butYEs)
            var butNo: Button = dialog!!.findViewById(R.id.butNO)
            var tv_alert: TextView = dialog!!.findViewById(R.id.tv_alert)
            var txtDialogMessage: TextView = dialog!!.findViewById(R.id.txtDialogMessage)
            butYes.text = resources.getString(R.string.ok1)
            butNo.visibility = View.GONE
            tv_alert.text = resources.getString(R.string.location)
            txtDialogMessage.text =
                resources.getString(R.string.PleasegotoSettingsandturnonthepermissions)

            butYes.setOnClickListener {

                var sharedPreferences1: SharedPreferences =
                    this.getSharedPreferences("OneTime", MODE_PRIVATE)
                var myEdit1 = sharedPreferences1.edit()
                myEdit1.clear()
                myEdit1.commit()

                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),0)
                dialog!!.dismiss()
            }
        }

    }


    private fun setWeatherData(weatherResponseModel: WeatherResponseModel) {

        val myDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = myDate
        val time = calendar.time
        val outputFmt = SimpleDateFormat("hh:mm a")
        val dateAsString = outputFmt.format(time)
        print(dateAsString)
        Log.d(ContentValues.TAG, "onDayClick: " + dateAsString)
        print(dateAsString)

        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(
            weatherResponseModel.data!!.lat!!,
            weatherResponseModel.data!!.lng!!,
            1
        )
        val cityName = addresses[0].locality
        val stateName = addresses[0].getAddressLine(1)
        val countryName = addresses[0].countryName

        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(true)
        dialog!!.setContentView(R.layout.weatherpopup1)
        dialog!!.show()


        var btncancle3: ImageView = dialog!!.findViewById(R.id.btncanclewether1)
        var weather_Time: TextView = dialog!!.findViewById(R.id.weather_Time)
        var weather_City: TextView = dialog!!.findViewById(R.id.weather_City)
        var weather_Cloudy: TextView = dialog!!.findViewById(R.id.weather_Cloudy)
        var weather_HumidityRating: TextView = dialog!!.findViewById(R.id.weather_Rating)
        var weather_Windspeed: TextView = dialog!!.findViewById(R.id.weather_Windspeed)
        var weather_temperature: TextView = dialog!!.findViewById(R.id.weather_temperature)

        weather_Time.text = dateAsString
        weather_City.text = "$cityName,$countryName"
        weather_Cloudy.text = weatherResponseModel.data!!.summary
        weather_HumidityRating.text = weatherResponseModel.data!!.humidity.toString() + " %"
        weather_Windspeed.text = weatherResponseModel.data!!.windSpeed.toString() + " km/hr"
        weather_temperature.text = weatherResponseModel.data!!.temperature.toString() + " °F"




        btncancle3.setOnClickListener {

            dialog!!.dismiss()

        }




    }

    private fun setAirQualityData(airQualityResponseModel: AirQualityResponseModel) {


        val myDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = myDate
        val time = calendar.time
        val outputFmt = SimpleDateFormat("hh:mm a")
        val dateAsString = outputFmt.format(time)
        print(dateAsString)
        Log.d(ContentValues.TAG, "onDayClick: " + dateAsString)
        print(dateAsString)


        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(
            airQualityResponseModel.stations!![0].lat!!,
            airQualityResponseModel.stations!![0].lng!!,
            1
        )
        val cityName = addresses[0].locality
        val stateName = addresses[0].getAddressLine(1)
        val countryName = addresses[0].countryName


        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(true)
        dialog!!.setContentView(R.layout.airqualitypopup1)
        dialog!!.show()



        var air_quality_City: TextView = dialog!!.findViewById(R.id.air_quality_City)
        var air_quality_Time: TextView = dialog!!.findViewById(R.id.air_quality_Time)
        var btncancle2: ImageView = dialog!!.findViewById(R.id.btncancleairquality1)
        var air_quality_info: TextView = dialog!!.findViewById(R.id.air_quality_Info)
        var air_quality_Rating: TextView = dialog!!.findViewById(R.id.air_quality_rating)
        var forgoodairquality: TextView = dialog!!.findViewById(R.id.forgoodairquality)

        air_quality_Time.text = dateAsString
        air_quality_City.text = "$cityName,$countryName"
        air_quality_info.text = airQualityResponseModel.stations!![0].aqiInfo!!.category
        air_quality_Rating.text = airQualityResponseModel.stations!![0].AQI!!.toString() + " AQI"


     if(air_quality_info.text == "Good") {

         forgoodairquality.setTextColor(resources.getColor(R.color.text_green))
             forgoodairquality.text = resources.getString(R.string.AirqualityissatisfactoryAirpollutionposeslittleornorisk)

     }  else if (air_quality_info.text == "Moderate"){
         forgoodairquality.setTextColor(resources.getColor(R.color.text_yellow))
         forgoodairquality.text = resources.getString(R.string.AirqualityisacceptableYouneedtoprotectyourselfmakesureusethprotectivemask)

     } else if (air_quality_info.text == "Unhealthy"){
         forgoodairquality.setTextColor(resources.getColor(R.color.text_red))
         forgoodairquality.text = resources.getString(R.string.AirqualityisunhealthyStayindoorsifyoucanwiththewindowsanddoorsclosed)
     } else {
         forgoodairquality.setTextColor(resources.getColor(R.color.text_red))
         forgoodairquality.text = resources.getString(R.string.AirqualityisunhealthyStayindoorsifyoucanwiththewindowsanddoorsclosed)
     }


        btncancle2.setOnClickListener {

            dialog!!.dismiss()

        }

    }


    private fun setAllergyOutlookData(allergyOutlookResponseModel: AllergyOutlookResponseModel) {

        val myDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = myDate
        val time = calendar.time
        val outputFmt = SimpleDateFormat("hh:mm a")
        val dateAsString = outputFmt.format(time)
        print(dateAsString)
        Log.d(ContentValues.TAG, "onDayClick: " + dateAsString)
        print(dateAsString)


        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(
            allergyOutlookResponseModel.lat!!,
            allergyOutlookResponseModel.lng!!,
            1
        )
        val cityName = addresses[0].locality
        val stateName = addresses[0].getAddressLine(1)
        val countryName = addresses[0].countryName


        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(true)
        dialog!!.setContentView(R.layout.allergyoutlookpopup1)
        dialog!!.show()


        var btncancle1: ImageView = dialog!!.findViewById(R.id.btncancleallergy1)
        var allergy_Time: TextView = dialog!!.findViewById(R.id.allergy_Time)
        var allergy_city: TextView = dialog!!.findViewById(R.id.allergy_city)
        var grass_Pollen_status: TextView = dialog!!.findViewById(R.id.grass_Pollen_status)
        var tree_Pollen_status: TextView = dialog!!.findViewById(R.id.tree_Pollen_status)
        var ragweed_Pollen_status: TextView = dialog!!.findViewById(R.id.ragweed_Pollen_status)


        allergy_city.text = "$cityName,$countryName"
        allergy_Time.text = dateAsString
        grass_Pollen_status.text =
            allergyOutlookResponseModel.data!![0].Risk!!.grass_pollen.toString()
        tree_Pollen_status.text =
            allergyOutlookResponseModel.data!![0].Risk!!.tree_pollen.toString()
        ragweed_Pollen_status.text =
            allergyOutlookResponseModel.data!![0].Risk!!.weed_pollen.toString()

        btncancle1.setOnClickListener {

            dialog!!.dismiss()

        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0) {
            recreate()
                 }
        else
        {
            Utility.sharedInstance.showToastError(this,"Please turn on location to see weather details")
        }
    }

    private fun createToastFunc() {

         AppDemo()
    }

    
    fun AppDemo() {
        MaterialTapTargetSequence()
//            .addPrompt(
//                MaterialTapTargetPrompt.Builder(this@HomeActivity)
//                    .setTarget(findViewById<View>(R.id.sos))
//                    .setPrimaryText("SOS")
//                    .setSecondaryText("Here you can see all you menu like Profile , Cart , CP Wallet , Facebook , Youtube, etc. ")
//                    .setFocalPadding(R.dimen.dimen10sp)
//                    .setAutoDismiss(true)
//
//
//            )
//            .addPrompt(
//                MaterialTapTargetPrompt.Builder(this@HomeActivity)
//                    .setTarget(findViewById<View>(R.id.nav_profile))
//                    .setPrimaryText("Profile")
//                    .setSecondaryText("Here you can find all new coursed list you can contact us Email, and Apply online Course  ")
//                    .setFocalPadding(R.dimen.dimen10sp)
//                    .setIcon(R.drawable.icon_nav)
//                    .create(), 2000
//
//            )
            .addPrompt(
                MaterialTapTargetPrompt.Builder(this@HomeActivity)
                    .setTarget(findViewById<View>(R.id.textaddyourdailyzone))
                    .setPrimaryText(resources.getString(R.string.DailyZoneonbording))
                    .setSecondaryText(resources.getString(R.string.Checkyourdailyzonehereonbording))
                    .setFocalPadding(R.dimen.dimen10sp)
                    .setBackgroundColour(resources.getColor(R.color.TextBlue))
                    .setAutoDismiss(true)


            )
            .addPrompt(
                MaterialTapTargetPrompt.Builder(this@HomeActivity)
                    .setTarget(findViewById<View>(R.id.All_Action_Plans))
                    .setPrimaryText(resources.getString(R.string.ActionPlanonbording))
                    .setSecondaryText(resources.getString(R.string.Youcanvieworcreateyouractionplanhereonbording ))
                    .setFocalPadding(R.dimen.dimen10sp)
                    .setIcon(R.drawable.myactionnootbook)
                    .setBackgroundColour(resources.getColor(R.color.TextBlue))
                    .setAutoDismiss(true)


            )
            .addPrompt(
                MaterialTapTargetPrompt.Builder(this@HomeActivity)
                    .setTarget(findViewById<View>(R.id.currentwethercard))
                    .setPrimaryText(resources.getString(R.string.Weatheronbording))
                    .setSecondaryText(resources.getString(R.string.Getcurrentweatherupdatesonbording))
                    .setFocalPadding(R.dimen.dimen10sp)
                    .setIcon(R.drawable.weatherhd1)
                    .setBackgroundColour(resources.getColor(R.color.TextBlue))
                    .setAutoDismiss(true)
                    .create(), 2000

            )
            .addPrompt(
                MaterialTapTargetPrompt.Builder(this@HomeActivity)
                    .setTarget(findViewById<View>(R.id.current_Air_Quality))
                    .setPrimaryText(resources.getString(R.string.AirQualityonbording))
                    .setSecondaryText(resources.getString(R.string.Checairqualityheretoprotectyoufromunhealthyorpollutedaironbording))
                    .setFocalPadding(R.dimen.dimen10sp)
                    .setIcon(R.drawable.airhd1)
                    .setBackgroundColour(resources.getColor(R.color.TextBlue))
                    .setAutoDismiss(true)
                    .setBackgroundColour(resources.getColor(R.color.TextBlue))
                    .create(), 2000

            )
            .addPrompt(
                MaterialTapTargetPrompt.Builder(this@HomeActivity)
                    .setTarget(findViewById<View>(R.id.current_Allergy_Outlook))
                    .setPrimaryText(resources.getString(R.string.AllergyOutlookonbordin))
                    .setSecondaryText(resources.getString(R.string.Checkoutyourlocalallergyoutlookheeonbordin))
                    .setAnimationInterpolator(LinearOutSlowInInterpolator())
                    .setFocalPadding(R.dimen.dimen10sp)
                    .setIcon(R.drawable.allergyhd1)
                    .setBackgroundColour(resources.getColor(R.color.TextBlue))
                    .setAutoDismiss(true)
                    .create(), 2000
            )
            .show()
    }

}



