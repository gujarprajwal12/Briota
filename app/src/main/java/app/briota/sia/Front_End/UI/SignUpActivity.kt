package app.briota.sia.Front_End.UI

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import com.amplifyframework.auth.AuthException
import app.briota.sia.integration_layer.models.User_Detail.SignUpModel
import app.briota.sia.integration_layer.models.User_Detail.SignUpProfileModel
import app.briota.sia.integration_layer.models.User_Detail.SignUpResponseModel
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    lateinit var editFirstName: EditText
    lateinit var edtlastname: EditText
    lateinit var edtphone_number: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var editConfirmPassword: EditText
    lateinit var signup_checkBox: CheckBox
    lateinit var btnSignUp: Button


    val passwordPattern: Pattern =
        Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,32}$")
    val namePattern: Pattern = Pattern.compile("[A-Z][a-z]*")

    val numPattern: Pattern =
        Pattern.compile("^[+][0-9]{10,13}\$")
    var TAG = "::SIGN_UP_ACT::"
    var email = ""
    var phone = ""
    var confirm = "1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initView()
        onClick()
    }

    fun initView() {

        editFirstName = findViewById(R.id.editFirstName)
        edtlastname = findViewById(R.id.editLastName)
        edtphone_number = findViewById(R.id.editmobilenumber)
        editEmail = findViewById(R.id.editEmailID)
        editPassword = findViewById(R.id.editPasswordsignup)
        editConfirmPassword = findViewById(R.id.editConfirmPasswordsignup)
        signup_checkBox = findViewById(R.id.signup_checkBox)
        btnSignUp = findViewById(R.id.butSignUp)


    }
    // function  for click items


    fun onClick() {


        val back = findViewById<TextView>(R.id.Back)

        back.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)

            startActivity(intent)
        }




        btnSignUp.setOnClickListener {


            val myfirstname = editFirstName.text.toString().trim()
            val mylastName = edtlastname.text.toString().trim()
            val myphone_number = edtphone_number.text.toString().trim()
            val myemail = editEmail.text.toString().trim()
            val mypassword = editPassword.text.toString().trim()
            val myconfirmpassword = editConfirmPassword.text.toString().trim()

            var sharedPreferences: SharedPreferences =
                this@SignUpActivity.getSharedPreferences("Briota", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putString("signup_username", editEmail.text.toString().trim())
            myEdit.apply()

            var sh2 = getSharedPreferences("Briota", MODE_PRIVATE)
            var lang = sh2.getString("Select_Lang", "")

            if(lang!!.equals(""))
            {
                if(txt_Welcome.text == "Welcome!")
                {
                    lang = sh2.getString("Select_Lang", "aa")
                }
                else
                {
                    lang = sh2.getString("Select_Lang", "da")
                }
            }


            var signUpModel: SignUpModel? = SignUpModel()
            var signUpProfileModel: SignUpProfileModel? = SignUpProfileModel()


            signUpModel!!.email = myemail
            signUpModel.password = mypassword
            signUpModel.role_id = 3
            signUpModel.language_id = lang

            signUpProfileModel!!.first_name = myfirstname
            signUpProfileModel.last_name = mylastName
            signUpProfileModel.phone_number = myphone_number
            signUpModel.profile = signUpProfileModel


            if (myfirstname.isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this@SignUpActivity,
                    resources.getString(R.string.EnterFirstName)
                )
                editFirstName.requestFocus()
                return@setOnClickListener
            }
            else if (!namePattern.matcher(myfirstname).matches()) {
                Utility.sharedInstance.showToastError(
                    this@SignUpActivity,
                    resources.getString(R.string.firstnameval)
                )
                return@setOnClickListener
            }
            else if (mylastName.isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this@SignUpActivity,
                    resources.getString(R.string.EnterLastName)
                )
                edtlastname.requestFocus()
                return@setOnClickListener
            }
            else if (!namePattern.matcher(mylastName).matches()) {
                Utility.sharedInstance.showToastError(
                    this@SignUpActivity,
                    resources.getString(R.string.lastnameviwthval)
                )
                return@setOnClickListener
            }
            else if (myemail.isEmpty()) {
                Utility.sharedInstance.showToastError(
                    this@SignUpActivity,
                    resources.getString(R.string.EnterEmailId)
                )
                editEmail.requestFocus()
                return@setOnClickListener
            } else
                if (!Patterns.EMAIL_ADDRESS.matcher(myemail).matches()) {
                    Utility.sharedInstance.showToastError(
                        this@SignUpActivity,
                        resources.getString(R.string.InvalidEmailId)
                    )
                    return@setOnClickListener
                } else if (myphone_number.isNotEmpty()) {
                    if (!numPattern.matcher(myphone_number).matches()) {
                        Utility.sharedInstance.showToastError(
                            this,
                            resources.getString(R.string.requriedplusesign)
                        )
                        return@setOnClickListener
                    } else if (mypassword.isEmpty()) {
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            resources.getString(R.string.EnterPassword)
                        )
                        editPassword.requestFocus()
                        return@setOnClickListener
                    } else if (!passwordPattern.matcher(mypassword).matches()) {
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            resources.getString(R.string.Passwordscontainmin8charactersCapitalLetterNumberSpecialCharacter)
                        )
                        return@setOnClickListener
                    } else if (myconfirmpassword.isEmpty()) {
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            resources.getString(R.string.EnterConfirmPassword)
                        )
                        editConfirmPassword.requestFocus()
                        return@setOnClickListener
                    } else if (!mypassword.equals(myconfirmpassword)) {
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            resources.getString(R.string.PasswordandConfirmPassworddoesnotmatch)
                        )
                        return@setOnClickListener
                    } else
                        if (!signup_checkBox.isChecked) {
                            signup_checkBox.error = " "
                            signup_checkBox.requestFocus()
                            return@setOnClickListener
                        } else {

                            // SIGN UP Fun called here
                            Utility.sharedInstance.showDialog(SignupActivity@ this)

                            // check here for signupmodel
                            signUpCall(signUpModel)
                        }

                } else {
                    if (mypassword.isEmpty()) {
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            resources.getString(R.string.EnterPassword)
                        )
                        editPassword.requestFocus()
                        return@setOnClickListener
                    } else if (!passwordPattern.matcher(mypassword).matches()) {
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            resources.getString(R.string.Passwordscontainmin8charactersCapitalLetterNumberSpecialCharacter)
                        )
                        return@setOnClickListener
                    } else if (myconfirmpassword.isEmpty()) {
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            resources.getString(R.string.EnterConfirmPassword)
                        )
                        editConfirmPassword.requestFocus()
                        return@setOnClickListener
                    } else if (!mypassword.equals(myconfirmpassword)) {
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            resources.getString(R.string.PasswordandConfirmPassworddoesnotmatch)
                        )
                        return@setOnClickListener
                    } else
                        if (!signup_checkBox.isChecked) {
                            signup_checkBox.error = " "
                            signup_checkBox.requestFocus()
                            return@setOnClickListener
                        } else {

                            // SIGN UP Fun called here
                            Utility.sharedInstance.showDialog(SignupActivity@ this)

                            // check here for signupmodel
                            signUpCall(signUpModel)
                        }

                }
        }


        var Term = findViewById<TextView>(R.id.text_terms)

        Term.setOnClickListener {
            val intent = Intent(this, TermsNConditionActivity::class.java)

            startActivity(intent)
        }


    }

    fun success() {
        this.runOnUiThread(Runnable {
            val editFirstName = findViewById<EditText>(R.id.editFirstName)
            val editmobileno = findViewById<EditText>(R.id.editmobilenumber)
            val editEmail = findViewById<EditText>(R.id.editEmailID)
            val editPassword = findViewById<EditText>(R.id.editPasswordsignup)

            Toast.makeText(
                this,
                resources.getString(R.string.Youraccountcreatedsuccessfully),
                Toast.LENGTH_SHORT
            ).show()
            editEmail.setText("")
            editPassword.setText("")
            editFirstName.setText("")
            editmobileno.setText("")
        })
    }

    fun failed(error: AuthException) {
        this.runOnUiThread(Runnable {
            Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show()
        })
    }


    //    API CALLING SIGN UP


    fun signUpCall(signUpModel: SignUpModel) {
        // converted model into json string
        var gson = Gson()
        var obj = JSONObject()
        var json = gson.toJson(signUpModel)
        // JSOn string converted into the Object
        try {
            obj = JSONObject(json)
            Log.d(TAG, obj.toString())
        } catch (t: Throwable) {
            //Log.e(TAG, "Could not parse malformed JSON: \"$json\"")
        }

        RetrofitClient.getRetrofitInstance.signUPAPI(signUpModel)
            .enqueue(object : Callback<SignUpResponseModel> {

                override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                    Log.e(TAG, "Failed::" + t)

                    Utility.sharedInstance.dismissProgressDialog()
                }

                override fun onResponse(
                    call: Call<SignUpResponseModel>,
                    response: Response<SignUpResponseModel>
                ) {
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
                                .equals(resources.getString(R.string.Anaccountwiththegivenemailalreadyexists))
                        ) {
                            Utility.sharedInstance.showToastError(
                                this@SignUpActivity,
                                "" + response.body()
                            )
                        }

                        val intent = Intent(this@SignUpActivity, VerificationActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {

                        response.errorBody()?.let { Log.d("error", it.string()) }
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            resources.getString(R.string.Anaccountwiththegivenemailalreadyexists)
                        )
                        Utility.sharedInstance.dismissProgressDialog()
                        Utility.sharedInstance.showToastError(
                            this@SignUpActivity,
                            "" + response.body()
                        )
                        Log.e(TAG, "::else part:::" + response.body())
                    }
                }
            })
    }

}