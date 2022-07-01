package app.briota.sia.Front_End.UI

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.view.adapter.*
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.GetActionPlanListModel
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.data
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GetActionPlanDetailsModel
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.*
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.RedActionPlan.RRescue
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.RedActionPlan.RSteroid
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.YellowActionPlan.YDaily
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.YellowActionPlan.YRescue
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.YellowActionPlan.YSteroid
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.PostActionPlanRequestModel
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.PostActionPlanWithMedModel
import app.briota.sia.integration_layer.models.User_Detail.SignUpResponseModel
import app.briota.sia.integration_layer.models.User_Detail.UploadImage.UploadImageResponseModel
import app.briota.sia.integration_layer.utilities.MyApplication
import com.example.briota.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.Yellow1
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_image.*
import kotlinx.android.synthetic.main.activity_medications.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MedicationsActivity : AppCompatActivity() {


    lateinit var floatingActionButton: FloatingActionButton
    lateinit var txtTitle: TextView
    lateinit var Back: TextView
    var green_recyclerview: RecyclerView? = null
    var yellow_recyclerview: RecyclerView? = null
    var red_recyclerview: RecyclerView? = null
    var yellow_rescue_recyclerview: RecyclerView? = null
    var yellow_steroid_recyclerview: RecyclerView? = null
    var red_steroid_recyclerview: RecyclerView? = null
    var patientID: Int? = null


    var id: Int? = null
    var s3_file_path: String? = null
    var dialog: Dialog? = null

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val STORAGE_PERMISSION_CODE = 101
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medications)



        initView()

        onClick()
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {

            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)


        }
    }

    private fun checkPermissioncammera(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {

            var intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent1, 2)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MedicationsActivity.CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.camerapermissiongranted),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.camerapermissiondenied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else if (requestCode == MedicationsActivity.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.storagepermissiongranted),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.storagepermissiondenied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    fun initView() {

        floatingActionButton = findViewById(R.id.floatingActionButton)
        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.view_action_plan)
//        txtTitle1.text = resources.getString(R.string.delete)
        Back = findViewById(R.id.Back)
        green_recyclerview = findViewById(R.id.green_condition_recyclerview)
        yellow_recyclerview = findViewById(R.id.yellow_condition_recyclerview)
        red_recyclerview = findViewById(R.id.red_condition_recyclerview)
        yellow_rescue_recyclerview = findViewById(R.id.yellow_rescue_recyclerview)
        yellow_steroid_recyclerview = findViewById(R.id.yellow_steroid_recyclerview)
        red_steroid_recyclerview = findViewById(R.id.red_steroid_recyclerview)


        txtTitle1.text = resources.getString(R.string.Edit)

    }


    fun onClick() {


        txtTitle1.setOnClickListener {


            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(true)
            dialog!!.setContentView(R.layout.upload_image_dialog)
            dialog!!.show()

            var butGallery: ImageButton = dialog!!.findViewById(R.id.butGallery)
            var butCamera: ImageButton = dialog!!.findViewById(R.id.butCamera)

            var btncanlcecammera: Button = dialog!!.findViewById(R.id.btncanlcecammera)


            // Set Buttons on Click Listeners
            butGallery.setOnClickListener {
                checkPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    MedicationsActivity.STORAGE_PERMISSION_CODE
                )
            }
            butCamera.setOnClickListener {
                checkPermissioncammera(
                    Manifest.permission.CAMERA,
                    MedicationsActivity.CAMERA_PERMISSION_CODE
                )
            }


            btncanlcecammera.setOnClickListener {
                dialog!!.dismiss()

            }


        }

        floatingActionButton.setOnClickListener {

            var sharedPreferences: SharedPreferences =
                this.getSharedPreferences("Briota", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putInt("myId", 0)

            myEdit.apply()


            val intent = Intent(this, MyActionPlanMedicationsActivity::class.java)
            startActivity(intent)
        }


        Back.setOnClickListener {

            onBackPressed()
        }

    }


    override fun onResume() {
        super.onResume()

        patientID = intent.getIntExtra("id", 0)

        id = patientID

        Log.d(TAG, "onResume: " + id)
        Log.d(TAG, "onResume: " + id)


        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var mytoken = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + mytoken)

        var myId = sh.getInt("ID", 0)
        Log.d(ContentValues.TAG, "onResume: ++++" + myId)

        if (id == 0) {
            id = myId
        }


        getActionPlanDetails(mytoken, id!!)


    }

    private fun getActionPlanDetails(mytoken: String?, myId: Int) {

        RetrofitClient.getRetrofitInstance.geActionPlanDetailsAPI("Bearer " + mytoken, myId)
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

                            var getActionPlanDetailsModel: GetActionPlanDetailsModel =
                                gson.fromJson(json, GetActionPlanDetailsModel::class.java)

                            Log.d(TAG, "onResponse: " + json)

                            Log.d(TAG, "onResponse: " + getActionPlanDetailsModel)

                            setData(getActionPlanDetailsModel)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

                    } else {
                        Utility.sharedInstance.showToastError(
                            this@MedicationsActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }

    private fun setData(actionPlanDetailsModel: GetActionPlanDetailsModel) {


        if (actionPlanDetailsModel.created_by.equals("Myself")) {
            action_plan1.visibility = View.GONE
            action_plan2.visibility = View.VISIBLE

            Picasso.with(applicationContext).load(actionPlanDetailsModel.data!![0].photo_url)
                .into(templateImage)
        } else {
            action_plan1.visibility = View.VISIBLE
            action_plan2.visibility = View.GONE
        }

        // RecyclerView Setup for Green Zone

        val greenData: ArrayList<Green>?
        greenData = actionPlanDetailsModel.Green!!

        Log.e(TAG, "setData: " + greenData.size)

        val dailyData: ArrayList<Daily> = arrayListOf()

        try {

            for (i in greenData.indices) {
                dailyData.addAll(greenData[i].daily!!)
            }


        } catch (e: Exception) {
            Log.d(TAG, "setData: " + e)
        }


        Log.d(TAG, "setData: " + dailyData)
        Log.d(TAG, "setData: " + greenData)

        if (dailyData[0].medicineName.equals("")) {
            greenTxt1.visibility = View.VISIBLE
            greenTxt2.visibility = View.VISIBLE
        } else {
            greenTxt1.visibility = View.GONE
            greenTxt2.visibility = View.GONE
            green_recyclerview!!.adapter = GreenMedicineAdapter(this@MedicationsActivity, dailyData)

            green_recyclerview!!.layoutManager =
                LinearLayoutManager(this@MedicationsActivity, LinearLayoutManager.VERTICAL, false)

        }


        // RecyclerView setup for Yellow Zone


        val yellowData: ArrayList<Yellow1>?
        yellowData = actionPlanDetailsModel.Yellow1!!

        Log.e(TAG, "setData: " + yellowData.size)

        val yellowMedicationDaily: ArrayList<YDaily> = arrayListOf()
        val yellowMedicationRescue: ArrayList<YRescue> = arrayListOf()
        val yellowMedicationSteroid: ArrayList<YSteroid> = arrayListOf()


        try {

            for (i in 0 until yellowData.size - 2) {
                yellowMedicationDaily.addAll(yellowData[0].yellowDaily!!)

            }

        } catch (e: Exception) {
            Log.d(TAG, "setData: " + e)

        }

        try {
            for (i in 1 until yellowData.size - 1) {
                yellowMedicationRescue.addAll(yellowData[1].yellowRescue!!)
            }
        } catch (e: Exception) {
            Log.d(TAG, "setData: " + e)

        }

        try {
            for (i in 2 until yellowData.size) {
                yellowMedicationSteroid.addAll(yellowData[2].yellowSteroid!!)
            }
        } catch (e: Exception) {
            Log.d(TAG, "setData: " + e)

        }

        if (yellowMedicationDaily[0].medicineName.equals("") && yellowMedicationRescue[0].medicineName.equals(
                ""
            ) && yellowMedicationSteroid[0].medicineName.equals("")
        ) {
            yellowTxt1.visibility = View.VISIBLE
            yellowTxt2.visibility = View.VISIBLE
        } else {
            yellowTxt1.visibility = View.GONE
            yellowTxt2.visibility = View.GONE

            yellow_recyclerview!!.adapter =
                YellowMedicineAdapter(this@MedicationsActivity, yellowMedicationDaily)

            yellow_recyclerview!!.layoutManager =
                LinearLayoutManager(this@MedicationsActivity, LinearLayoutManager.VERTICAL, false)


            yellow_rescue_recyclerview!!.adapter =
                YellowRescueAdapter(this@MedicationsActivity, yellowMedicationRescue)

            yellow_rescue_recyclerview!!.layoutManager =
                LinearLayoutManager(this@MedicationsActivity, LinearLayoutManager.VERTICAL, false)


            yellow_steroid_recyclerview!!.adapter =
                YellowSteroidAdapter(this@MedicationsActivity, yellowMedicationSteroid)

            yellow_steroid_recyclerview!!.layoutManager =
                LinearLayoutManager(this@MedicationsActivity, LinearLayoutManager.VERTICAL, false)

        }


        // RecyclerView setup for Red Zone

        val redData: ArrayList<Red1>?
        redData = actionPlanDetailsModel.Red1!!

        Log.e(TAG, "setData: " + redData.size)


        val redMedicationData: ArrayList<RRescue> = arrayListOf()
        val redMedicationSteroid: ArrayList<RSteroid> = arrayListOf()


        try {

            for (i in 1 until redData.size - 1) {
                redMedicationData.addAll(redData[1].redrescue!!)

            }
            Log.d(TAG, "setData: " + redMedicationData.size)
        } catch (e: Exception) {
            Log.d(TAG, "setData: " + e)
        }

        try {

            for (i in 2 until redData.size) {
                redMedicationSteroid.addAll(redData[2].redsteroid!!)

            }
            Log.d(TAG, "setData: " + redMedicationData.size)
        } catch (e: Exception) {
            Log.d(TAG, "setData: " + e)
        }


        if (redMedicationData[0].medicineName.equals("") && redMedicationSteroid[0].medicineName.equals(
                ""
            )
        ) {
            redText1.visibility = View.VISIBLE
            redTxt2.visibility = View.VISIBLE

        } else {
            redText1.visibility = View.GONE
            redTxt2.visibility = View.GONE

            red_recyclerview!!.adapter =
                RedMedicineAdapter(this@MedicationsActivity, redMedicationData)

            red_recyclerview!!.layoutManager =
                LinearLayoutManager(this@MedicationsActivity, LinearLayoutManager.VERTICAL, false)


            red_steroid_recyclerview!!.adapter =
                RedSteroidAdapter(this@MedicationsActivity, redMedicationSteroid)

            red_steroid_recyclerview!!.layoutManager =
                LinearLayoutManager(this@MedicationsActivity, LinearLayoutManager.VERTICAL, false)


        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {

            if (data != null) {

                val selectedImage = data.data

                sendFile(selectedImage!!)
            }

        } else {
            if (data != null) {

                dialog!!.dismiss()
                var captureImage: Bitmap
                captureImage = data.extras?.get("data") as Bitmap
                Log.d(TAG, "onActivityResult: " + captureImage)
                Log.d(TAG, "onActivityResult: " + data.data.toString())

                val tempUri: Uri = getImageUri(applicationContext, captureImage)!!
                Log.d(TAG, "onActivityResult: " + tempUri)
                print(tempUri)

                val tempUri1 = getImageUri1(applicationContext, captureImage)
                Log.d(TAG, "onActivityResult: " + tempUri1)
                print(tempUri1)

                sendFile(tempUri1 as Uri)

            }

        }
    }


    private fun getRealPathFromURI(tempUri1: Uri): String {

        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, tempUri1, proj, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result

    }

    private fun getImageUri1(applicationContext: Context?, captureImage: Bitmap): Any {

        val bytes = ByteArrayOutputStream()
        captureImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(
                applicationContext!!.contentResolver,
                captureImage,
                "Title",
                null
            )

        return Uri.parse(path)
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun sendFile(fileUri: Uri) {

        val file = File(getRealPathFromURI(fileUri))

        Log.d(TAG, "sendFile: " + file)
        print(file)

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var mytoken = sh.getString("bearer_token", "")
        Log.d(TAG, "onResume: ++++" + mytoken)

        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = uId.getString("user_Id", "")!!.toInt()
        Log.d(TAG, "onResponse: +++++++" + myUserId)


        val myApplication = MyApplication()
        var fileName = file.name
        var fileExtension = file.extension
        Log.d(TAG, "sendFile: " + fileName)
        Log.d(TAG, "sendFile: " + fileExtension)
        val strDateFormat = "dd-MM-yyyy hh:mm:ss a"
        val dateFormat: DateFormat = SimpleDateFormat(strDateFormat)
        val dates = SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date())

        val requestFile: RequestBody =
            file.asRequestBody("image/*".toMediaTypeOrNull())

        //mFile = RequestBody.create("image/*".toMediaTypeOrNull(),file.absolutePath)
        val fileToUpload: MultipartBody.Part =
            MultipartBody.Part.createFormData("photo", "$dates.$fileExtension", requestFile)
        Log.d(TAG, "sendFile: " + fileToUpload)
        print(fileToUpload)

        val callbackLogin: Call<UploadImageResponseModel> = myApplication.getAPIInstance()!!
            .uploadImage("Bearer $mytoken", fileToUpload)
        callbackLogin.enqueue(object : Callback<UploadImageResponseModel> {
            override fun onResponse(
                call: Call<UploadImageResponseModel>,
                response: Response<UploadImageResponseModel>
            ) {
                val responseData: UploadImageResponseModel? = response.body()
                Log.d(TAG, "onResponse: " + responseData)
                print(responseData)

                Log.d(TAG, "onResponse: " + response.body())
                print(response.body())

                val gson: Gson = Gson()
                val json: String = gson.toJson(response.body())

                val uploadImageResponseModel: UploadImageResponseModel =
                    gson.fromJson(json, UploadImageResponseModel::class.java)

                s3_file_path = uploadImageResponseModel.s3_file_path
                Log.d(TAG, "onResponse: " + uploadImageResponseModel)

                putMedicationActionPlan()

            }

            override fun onFailure(call: Call<UploadImageResponseModel?>, t: Throwable) {
            }
        })


    }

    private fun putMedicationActionPlan() {

        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = uId.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var diagnosis = sh.getString("Diagnosis", "")
        Log.d(ContentValues.TAG, "onResponse: +++++++" + diagnosis)


        val postActionPlanWithMedModel: PostActionPlanWithMedModel = PostActionPlanWithMedModel()

        postActionPlanWithMedModel.patient_id = myUserId
        postActionPlanWithMedModel.doctor_id = 0
        postActionPlanWithMedModel.diagnosis = diagnosis
        postActionPlanWithMedModel.dr_phone = ""
        postActionPlanWithMedModel.action_plan_id = id
        postActionPlanWithMedModel.green_pef = "0"
        postActionPlanWithMedModel.green_fev = "0"
        postActionPlanWithMedModel.yellow_pef = "0"
        postActionPlanWithMedModel.yellow_fev = "0"
        postActionPlanWithMedModel.red_pef = "0"
        postActionPlanWithMedModel.red_fev = "0"
        postActionPlanWithMedModel.photo_url = s3_file_path
        Log.d(TAG, "postMedicationActionPlan: " + postActionPlanWithMedModel.photo_url)
        print(postActionPlanWithMedModel.photo_url)

        var mytoken = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + mytoken)


        var gson = Gson()
        var obj = JSONObject()
        var json = gson.toJson(postActionPlanWithMedModel)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(ContentValues.TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.updateactionplan(
            "Bearer " + mytoken,
            postActionPlanWithMedModel,
            id!!
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e(ContentValues.TAG, "Failed::" + t)

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

                            var postActionPlanRequestModel: PostActionPlanRequestModel =
                                gson.fromJson(json, PostActionPlanRequestModel::class.java)

                            var action_plan_id = postActionPlanRequestModel.action_plan_id

                            var sharedPreferences: SharedPreferences =
                                this@MedicationsActivity.getSharedPreferences(
                                    "Briota",
                                    MODE_PRIVATE
                                )
                            var myEdit = sharedPreferences.edit()
                            myEdit.putInt("ID", action_plan_id!!.toInt())
                            Log.d(TAG, "onResponse: +++++" + action_plan_id)


                            myEdit.apply()


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
                                this@MedicationsActivity,
                                "" + response.body()
                            )
                        }


                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }

                    }
                }
            })



        dialog!!.dismiss()


        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.action_plan_added_popup)
        dialog!!.show()

        var btn_ok: Button = dialog!!.findViewById(R.id.btn_ok)

        btn_ok.setOnClickListener {

            val intent = Intent(this, AddImageActivity::class.java)
            startActivity(intent)

            dialog!!.dismiss()


        }

    }


}
