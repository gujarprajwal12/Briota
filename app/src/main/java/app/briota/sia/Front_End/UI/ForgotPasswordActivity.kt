package app.briota.sia.Front_End.UI

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ResendOTPModel
import app.briota.sia.integration_layer.models.User_Detail.ResendOTPRequestModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var back: TextView
    lateinit var BacktoSignin: TextView
    lateinit var Send: Button
    lateinit var editregisteredemail: EditText
    lateinit var txtTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        initView()
        onClick()


    }

    private fun initView() {
        back = findViewById(R.id.Back)
        BacktoSignin = findViewById(R.id.btnBacktoSignin)
        Send = findViewById(R.id.butsend)
        editregisteredemail = findViewById(R.id.editregisteredemail)
        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.reset_your_password)
    }

    private fun onClick() {

        back.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        BacktoSignin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        Send.setOnClickListener {

            var regUsername = editregisteredemail.text.toString().trim()


            var resendOTPModel: ResendOTPModel? = ResendOTPModel()

            var sh2 = getSharedPreferences("Briota", MODE_PRIVATE)

           var lang = sh2.getString("Select_Lang", "da")

            if(lang!!.equals("")) {
                if (txtTitle.text == "Reset your Password") {
                    lang = sh2.getString("Select_Lang", "aa")
                }
                else
                {
                    lang = sh2.getString("Select_Lang", "da")

                }
            }

            resendOTPModel!!.email = regUsername
            resendOTPModel.language_id = lang


            resendOtp(resendOTPModel)

            if (regUsername.isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.EnterEmailId)
                )
                editregisteredemail.requestFocus()
                return@setOnClickListener
            } else if (editregisteredemail.text.toString().trim().equals("")) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.enter_registered_email)
                )
                editregisteredemail.requestFocus()
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(editregisteredemail.text.toString())
                    .matches()
            ) {
                Utility.sharedInstance.showToastError(
                    this,
                    resources.getString(R.string.InvalidEmailId)
                )
                return@setOnClickListener
            }

        }


    }

    private fun resendOtp(resendOTPModel: ResendOTPModel) {

        RetrofitClient.getRetrofitInstance.resendOTPAPI(resendOTPModel)
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Utility.sharedInstance.dismissProgressDialog()
                    Utility.sharedInstance.showToastError(
                        this@ForgotPasswordActivity,
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

                        var resendOTPRequestModel: ResendOTPRequestModel =
                            gson.fromJson(json, ResendOTPRequestModel::class.java)

                        if (resendOTPRequestModel.status == true) {
                            openAct()
                        } else {

                            Utility.sharedInstance.showToastError(
                                this@ForgotPasswordActivity,
                                resources.getString(R.string.Thisemailisnotregistered)
                            )

                        }
                    }

                    Utility.sharedInstance.dismissProgressDialog()
                }
            })
    }

    private fun openAct() {
        val intent = Intent(this, ForgotPasswordRest::class.java)
        intent.putExtra("username", editregisteredemail.text.toString().trim())
        startActivity(intent)
        finish()
    }


}
