package app.briota.sia.Front_End.UI

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.SignInResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class SignInActivity : AppCompatActivity() {

    var userName: String? = ""
    var passWord: String? = ""

    var TAG = "::Sign In ACT::"
    var dialog: Dialog? = null
    lateinit var editUSERNAME: EditText

    var Tag: String = ":::Briota:::"
    var nav: String = "navigation"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        initView()
        onClick()
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


    // for all views intialization
    fun initView() {

        editUSERNAME = findViewById(R.id.editUSERNAME)
    }

    // function  for click items
    fun onClick() {

        val back = findViewById<TextView>(R.id.Back)
        back.setOnClickListener {

            val intent = Intent(this, SelectLanguage::class.java)

            startActivity(intent)
        }


        val forgot = findViewById<TextView>(R.id.txtForgotPass)
        forgot.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }


        val btnLogin = findViewById<Button>(R.id.butLogin)

        btnLogin.setOnClickListener {


            val editPassword = findViewById<EditText>(R.id.editPassword)


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
                tv_alert.text = resources.getString(R.string.InternetConnection)

                txtDialogMessage.text =
                    resources.getString(R.string.PleaseCheckYourInternetConnection)

                butYes.setOnClickListener {
                    dialog!!.dismiss()
                }
                return@setOnClickListener


            } else if (editUSERNAME.text.toString().trim().equals("")) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.enter_registered_email)
                )
                editUSERNAME.requestFocus()
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(editUSERNAME.text.toString()).matches()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.InvalidEmailId)
                )
                return@setOnClickListener
            } else if (editPassword.text.toString().trim().equals(""))
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.EnterPassword)
                )
            else {
                userName = editUSERNAME.text.toString().trim()
                passWord = editPassword.text.toString().trim()


                Utility.sharedInstance.showDialog(this)



                login(userName, passWord)
            }


        }


        val btnSignUp = findViewById<TextView>(R.id.txtSignUP)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)

            startActivity(intent)
        }


    }




    override fun onResume() {
        super.onResume()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var mytoken = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + mytoken)


        if (mytoken!!.isNotEmpty()) {

            var sharedPreferences: SharedPreferences =
                this@SignInActivity.getSharedPreferences("Briota", MODE_PRIVATE)
            var myNav = sharedPreferences.edit()
            myNav.putString("checkNav", nav)
            myNav.commit()

            val i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    // Fun Node JS API for calling login API


    fun login(email: String?, password: String?) {
        RetrofitClient.getRetrofitInstance.signin(email, password)
            .enqueue(object : Callback<SignInResponseModel> {

                override fun onFailure(call: Call<SignInResponseModel>, t: Throwable) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Utility.sharedInstance.showToastError(
                        this@SignInActivity,
                        resources.getString(R.string.InvalidEmailorpassword)
                    )
                }

                override fun onResponse(
                    call: Call<SignInResponseModel>,
                    response: Response<SignInResponseModel>
                ) {
                    Log.e(TAG, "" + response.toString())
                    if (response.code() == 200) {


                        var signInResponseModel: SignInResponseModel? = SignInResponseModel()
                        signInResponseModel = response.body()!!

                        // Parse  token here
                        var btoken: String = signInResponseModel.token!!
                        var roleID: Int = signInResponseModel.role_id!!


                        var sharedPreferences: SharedPreferences =
                            this@SignInActivity.getSharedPreferences("Briota", MODE_PRIVATE)
                        var myEdit = sharedPreferences.edit()
                        myEdit.putString("bearer_token", btoken)
                        myEdit.putString("check_navigation", nav)
                        Log.d(TAG, "onResponse: +++++" + btoken)
                        myEdit.putString("username", userName)
                        myEdit.putString("password", passWord)

                        myEdit.apply()

                        Utility.sharedInstance.dismissProgressDialog()


                        var intent = Intent(this@SignInActivity, ProfileActivity::class.java)

                        startActivity(intent)

                    } else if (response.code() == 401) {


                        Utility.sharedInstance.showToastError(
                            this@SignInActivity,
                            resources.getString(R.string.Loginfailed)
                        )

                        Utility.sharedInstance.dismissProgressDialog()

                    }
                    if (response.code() == 400) {
                        openAct()
                        Utility.sharedInstance.dismissProgressDialog()
                    }
                }
                // }
            })
    }

    private fun openAct() {


        val intent = Intent(this, VerificationActivity::class.java)
        startActivity(intent)
        finish()

    }

    override fun onBackPressed(){
        return
    }

}




