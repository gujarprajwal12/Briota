package app.briota.sia.Front_End.UI

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ResendOTPModel
import app.briota.sia.integration_layer.models.User_Detail.VerifyOTPModel
import com.google.android.gms.auth.api.phone.SmsRetriever
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern


class VerificationActivity : AppCompatActivity() {

    lateinit var setPIN1: EditText
    lateinit var setPIN2: EditText
    lateinit var setPIN3: EditText
    lateinit var setPIN4: EditText
    lateinit var txtOtp: TextView
    var myemail: String? = null
    var newemail: String? = null
    lateinit var txt_email: TextView
    lateinit var my_email: String
    lateinit var txtTitle: TextView
    lateinit var resend_OTP: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)


        initView()
        onClick()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        newemail = sh.getString("username", "")

        myemail = sh.getString("signup_username", "")

        if (myemail == null) {
            my_email = newemail.toString()
        } else {
            my_email = myemail.toString()
        }

        txt_email.text = my_email


    }


    fun initView() {

        setPIN1 = findViewById(R.id.setPIN1)
        setPIN2 = findViewById(R.id.setPIN2)
        setPIN3 = findViewById(R.id.setPIN3)
        setPIN4 = findViewById(R.id.setPIN4)
        txtOtp = findViewById(R.id.txtOtp)
        txt_email = findViewById(R.id.txt_email)
        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.Verify_Email_ID)

        resend_OTP = findViewById(R.id.resend_OTP)
    }

    // function  for click items
    fun onClick() {

        val back = findViewById<TextView>(R.id.Back)
        back.setOnClickListener {
            onBackPressed()
        }

        setPIN1.addTextChangedListener {
            if (setPIN1.text.toString().length == 1) {
                setPIN2.requestFocus()
            }
        }
        setPIN2.addTextChangedListener {
            if (setPIN2.text.toString().length == 1) {
                setPIN3.requestFocus()
            }
        }
        setPIN3.addTextChangedListener {
            if (setPIN3.text.toString().length == 1) {
                setPIN4.requestFocus()
            }
        }


        resend_OTP.setOnClickListener {

            var resendOTPModel: ResendOTPModel? = ResendOTPModel()

            var sh2 = getSharedPreferences("Briota", MODE_PRIVATE)

            var lang = sh2.getString("Select_Lang", "")

            if(lang!!.equals("")) {
                if (txtTitle.text == "Verify Email ID") {
                    lang = sh2.getString("Select_Lang", "aa")
                } else {
                    lang = sh2.getString("Select_Lang", "da")

                }
            }

            resendOTPModel!!.email = my_email
            resendOTPModel.language_id = lang


            resendOtp(resendOTPModel)


        }


        val verify = findViewById<Button>(R.id.butverify1)

        verify.setOnClickListener {

            txtOtp.text = setPIN1.text.toString().trim() + setPIN2.text.toString()
                .trim() + setPIN3.text.toString().trim() +
                    setPIN4.text.toString().trim()
            if (txtOtp.text.toString().trim().equals("") || txtOtp.text.toString()
                    .trim().length < 4
            )
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.InvalidOTP)
                )
            else {
                Utility.sharedInstance.showProgressDialog(this)

                var verifyOTPModel: VerifyOTPModel? = VerifyOTPModel()
                verifyOTPModel!!.otp = txtOtp.text.toString().trim()
                verifyOTPModel.email = my_email



                txtOtp(verifyOTPModel)
            }

        }


    }

    fun txtOtp(verifyOTPModel: VerifyOTPModel) {

        RetrofitClient.getRetrofitInstance.verifyOTPApi(verifyOTPModel)
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Utility.sharedInstance.showToastError(
                        this@VerificationActivity,
                        resources.getString(R.string.InvalidOTP)
                    )
                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    Log.e(TAG, "" + response.toString())
                    if (response.code() == 200) {

                        openAct()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@VerificationActivity,
                            resources.getString(R.string.InvalidOTP)
                        )
                        Utility.sharedInstance.dismissProgressDialog()

                    }
                }
            })
    }

    private fun openAct() {
        val intent = Intent(this, VerifySuccessActivity::class.java)

        startActivity(intent)
        finish()
    }

    private fun resendOtp(resendOTPModel: ResendOTPModel) {

        RetrofitClient.getRetrofitInstance.resendOTPAPI(resendOTPModel)
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Utility.sharedInstance.showToastError(
                        this@VerificationActivity,
                        resources.getString(R.string.InvalidOTP)
                    )
                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    Log.e(ContentValues.TAG, "" + response.toString())
                    if (response.code() == 200) {

                        Utility.sharedInstance.showToastSuccess(
                            this@VerificationActivity,
                            resources.getString(R.string.OTPhasbeensenttoyourmailinbox)
                        )

                    }

                    Utility.sharedInstance.dismissProgressDialog()
                }
            })
    }

}