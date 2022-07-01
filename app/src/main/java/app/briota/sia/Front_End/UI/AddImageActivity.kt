package app.briota.sia.Front_End.UI


import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.view.adapter.ImageUploadListAdapter
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.GetActionPlanListModel
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.data
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GetActionPlanDetailsModel
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.PostActionPlanRequestModel
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.PostActionPlanWithMedModel
import app.briota.sia.integration_layer.models.User_Detail.SignUpResponseModel
import app.briota.sia.integration_layer.models.User_Detail.UploadImage.UploadImageResponseModel
import app.briota.sia.integration_layer.utilities.MyApplication
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_image.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Part
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class AddImageActivity : AppCompatActivity() {

    var dialog: Dialog? = null
    lateinit var uploadImageList: RecyclerView
    var mImageUploadListAdapter: ImageUploadListAdapter? = null
    var galImage: String? = null
    var galleryImage: Image? = null
    var camImage: String? = null
    var s3_file_path: String? = null
    var filePath: String? = null
    var bitmap: Bitmap? = null


    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val STORAGE_PERMISSION_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        initView()

        onClick()


    }


    fun initView() {

        txtTitle.text = resources.getString(R.string.add_my_action_plan)
        uploadImageList = findViewById(R.id.uploadImageList)
    }

    fun onClick() {
        back_tol.setOnClickListener {
            val intent = Intent(this, PatientProfileActivity::class.java)
            startActivity(intent)
            finish()
        }


        btnPickImage.setOnClickListener {


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
                    STORAGE_PERMISSION_CODE
                )
            }
            butCamera.setOnClickListener {
                checkPermissioncammera(
                    Manifest.permission.CAMERA,
                    CAMERA_PERMISSION_CODE
                )
            }


            btncanlcecammera.setOnClickListener {
                dialog!!.dismiss()
            }
        }


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
        if (requestCode == CAMERA_PERMISSION_CODE) {
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
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
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


    override fun onResume() {
        super.onResume()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + token)

        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = uId.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)

        getActionPlanList(token!!, myUserId)

    }


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun getRealPathFromURI(tempUri1: Uri): String {

        val proj = arrayOf(Images.Media.DATA)
        val loader = CursorLoader(this, tempUri1, proj, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result

    }

    private fun getImageUri1(applicationContext: Context?, captureImage: Bitmap): Any {

        val bytes = ByteArrayOutputStream()
        captureImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            Images.Media.insertImage(
                applicationContext!!.contentResolver,
                captureImage,
                "Title",
                null
            )

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


        val fileToUpload: MultipartBody.Part =
            createFormData("photo", "$dates.$fileExtension", requestFile)
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

                postMedicationActionPlan()

            }

            override fun onFailure(call: Call<UploadImageResponseModel?>, t: Throwable) {
            }
        })


    }


    private fun postMedicationActionPlan() {

        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = uId.getString("user_Id", "")!!.toInt()


        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var diagnosis = sh.getString("Diagnosis", "")

        var patientId = sh.getInt("myId", 0)

        val postActionPlanWithMedModel: PostActionPlanWithMedModel = PostActionPlanWithMedModel()

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

        RetrofitClient.getRetrofitInstance.actionMedPlanAPI(
            "Bearer " + mytoken,
            postActionPlanWithMedModel
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
                                this@AddImageActivity.getSharedPreferences("Briota", MODE_PRIVATE)
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
                                this@AddImageActivity,
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

            var sh = getSharedPreferences("Briota", MODE_PRIVATE)
            var token = sh.getString("bearer_token", "")
            Log.d(TAG, "onResume: ++++" + token)

            var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
            var myUserId = uId.getString("user_Id", "")!!.toInt()
            Log.d(TAG, "onResponse: +++++++" + myUserId)

            getActionPlanList(token!!, myUserId)

            dialog!!.dismiss()

        }

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

                            var myId = getActionplanListModel.data!![0].patient_id_id

                            var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                            var mytoken = sh.getString("bearer_token", "")
                            Log.d(ContentValues.TAG, "onResume: ++++" + mytoken)

                            getActionPlanDetails(mytoken, myId!!.toInt())

                            setData(getActionplanListModel)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

                    } else {
                        Utility.sharedInstance.showToastError(
                            this@AddImageActivity,
                            response.body().toString()
                        )


                    }
                }
            })


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


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

                    } else {
                        Utility.sharedInstance.showToastError(
                            this@AddImageActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }

    private fun setData(getActionplanListModel: GetActionPlanListModel) {

        val imageList: ArrayList<data>
        imageList = getActionplanListModel.data!!

        var getActionPlanDetailsModel: GetActionPlanDetailsModel? = GetActionPlanDetailsModel()

        Log.d(TAG, "setData: " + getActionPlanDetailsModel)
        Log.d(TAG, "setData: " + getActionPlanDetailsModel!!.created_by)
        Log.d(TAG, "setData: " + getActionPlanDetailsModel.status)

        if (imageList.size == 0) {
            uploadImageList.visibility = View.GONE
            txt_empty.visibility = View.VISIBLE
        } else {
            uploadImageList.visibility = View.VISIBLE
            txt_empty.visibility = View.GONE
        }

        uploadImageList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mImageUploadListAdapter = ImageUploadListAdapter(this, imageList)
        uploadImageList.adapter = mImageUploadListAdapter

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

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                Utility.sharedInstance.showToastSuccess(
                    applicationContext,
                    "Action Plan removed successfully"
                )

                val position = viewHolder.adapterPosition
                val imageID = imageList.get(position).id


                var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                var newtoken = sh.getString("bearer_token", "")
                Log.d(TAG, "onResume: ++++" + newtoken)



                RetrofitClient.getRetrofitInstance.removeActionPlan(
                    "Bearer $newtoken",
                    imageID!!
                )
                    .enqueue(object : Callback<Any> {
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                            Utility.sharedInstance.showToastError(
                                this@AddImageActivity,
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


                                imageList.removeAt(position)
                                mImageUploadListAdapter!!.notifyDataSetChanged()

                                var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                                var token = sh.getString("bearer_token", "")
                                Log.d(TAG, "onResume: ++++" + token)

                                var sh1 = getSharedPreferences("USER_ID", MODE_PRIVATE)
                                var myUserId = sh1.getString("user_Id", "")
                                Log.d(TAG, "onResponse: +++++++" + myUserId)


                                getActionPlanList(token!!, myUserId!!.toInt())


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
        itemTouchHelper.attachToRecyclerView(uploadImageList)

    }


}

