package app.briota.sia.Front_End.UI

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ChangePasswordModel
import app.briota.sia.integration_layer.models.User_Detail.OTPVerifyModel
import app.briota.sia.integration_layer.models.User_Detail.ResendOTPModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class ForgotPasswordRest : AppCompatActivity() {

    lateinit var back: TextView
    lateinit var Reset: Button
    lateinit var editVerificationCode: EditText
    lateinit var editPassword: EditText
    lateinit var editConfirmPassword: EditText
    lateinit var email: String
    lateinit var txtTitle: TextView
    lateinit var txtResend: TextView


    val passwordPattern: Pattern =
        Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,32}$")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_rest)

        initView()
        onClick()

        email = intent.getStringExtra("username")!!

    }


    private fun initView() {
        back = findViewById(R.id.Back)
        Reset = findViewById(R.id.Reset)
        editVerificationCode = findViewById(R.id.editVerificationCode)
        editPassword = findViewById(R.id.editPassword)
        editConfirmPassword = findViewById(R.id.editConfirmPassword)
        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.reset_your_password)

        txtResend = findViewById(R.id.txtResend)

    }

    private fun onClick() {

        back.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }


        txtResend.setOnClickListener {

            var resendOTPModel: ResendOTPModel? = ResendOTPModel()

            var sh2 = getSharedPreferences("Briota", MODE_PRIVATE)

            var lang = sh2.getString("Select_Lang", "da")

            if (lang!!.equals("")) {
                if (txtTitle.text == "Reset your Password") {
                    lang = sh2.getString("Select_Lang", "aa")
                } else {
                    lang = sh2.getString("Select_Lang", "da")
                }
            }
            resendOTPModel!!.email = email
            resendOTPModel.language_id = lang


            resendOtp(resendOTPModel)

        }


        Reset.setOnClickListener {


            var otp = editVerificationCode.text.toString().trim()
            var password = editPassword.text.toString().trim()
            var confirmPassword = editConfirmPassword.text.toString().trim()

            var changePasswordModel: ChangePasswordModel? = ChangePasswordModel()

            changePasswordModel!!.otp = otp
            changePasswordModel.new_password = password
            changePasswordModel.confirm_password = confirmPassword
            changePasswordModel.email = email




            if (editVerificationCode.text.toString().trim().equals("")) {
                Utility.sharedInstance.showToastError(this, resources.getString(R.string.EnterOtp))
                editVerificationCode.requestFocus()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.EnterPassword)
                )
                editPassword.requestFocus()
                return@setOnClickListener
            } else if (!passwordPattern.matcher(password).matches()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.Passwordscontainmin8charactersCapitalLetterNumberSpecialCharacter)
                )
                return@setOnClickListener
            } else if (confirmPassword.isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.EnterConfirmPassword)
                )
                editConfirmPassword.requestFocus()
                return@setOnClickListener
            } else if (!password.equals(confirmPassword)) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.PasswordandConfirmPassworddoesnotmatch)
                )
                return@setOnClickListener
            } else {
                changePassword(changePasswordModel)
            }


        }
    }


    private fun changePassword(changePasswordModel: ChangePasswordModel) {

        RetrofitClient.getRetrofitInstance.changePasswordAPI(changePasswordModel)
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Utility.sharedInstance.showToastError(
                        this@ForgotPasswordRest,
                        resources.getString(R.string.Invalidcode)
                    )
                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    Log.e(ContentValues.TAG, "" + response.toString())
                    if (response.code() == 200) {

                        var gson: Gson = Gson()
                        var json: String = gson.toJson(response.body())

                        var otpVerifyModel: OTPVerifyModel =
                            gson.fromJson(json, OTPVerifyModel::class.java)

                        if (otpVerifyModel.status == true) {
                            openAct()
                        } else {
                            Utility.sharedInstance.showToastError(
                                this@ForgotPasswordRest,
                                resources.getString(R.string.InvalidOTP)
                            )
                        }
                    } else {

                        Utility.sharedInstance.dismissProgressDialog()


                    }
                }
            })

    }

    private fun openAct() {
        val intent = Intent(this, PasswordResetSuccessful::class.java)
        startActivity(intent)
        finish()
    }

    private fun resendOtp(resendOTPModel: ResendOTPModel) {

        RetrofitClient.getRetrofitInstance.resendOTPAPI(resendOTPModel)
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Utility.sharedInstance.showToastError(
                        this@ForgotPasswordRest,
                        resources.getString(R.string.reset_your_password)
                    )
                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    Log.e(ContentValues.TAG, "" + response.toString())
                    if (response.code() == 200) {

                        Utility.sharedInstance.showToastSuccess(
                            this@ForgotPasswordRest,
                            resources.getString(R.string.OTPhasbeensenttoyoumailinbox)
                        )

                    }

                    Utility.sharedInstance.dismissProgressDialog()
                }
            })
    }

}
