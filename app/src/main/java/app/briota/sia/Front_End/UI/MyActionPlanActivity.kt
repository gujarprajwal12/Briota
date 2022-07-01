package app.briota.sia.Front_End.UI

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.view.adapter.CreationListAdapter
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.GetActionPlanListModel
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.data
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GetActionPlanDetailsModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_my_action_plan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyActionPlanActivity : AppCompatActivity() {



    lateinit var  Back : TextView
    lateinit var floatingActionButton : FloatingActionButton
    lateinit var  txtTitle: TextView
    lateinit var  btngreenuser : Button
    lateinit var btnyellowuser : Button
    lateinit var  btnreduser : Button
    lateinit var  btnyellowfordr  : Button
    lateinit var btnredfordr : Button
    lateinit var btngreenfordr : Button
    lateinit var patient_id : String
    lateinit var createdList : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_action_plan)

        intView()
        onClick()

    }



    fun intView () {


        Back = findViewById(R.id.Back)

        floatingActionButton = findViewById(R.id.floatingActionButton)
        txtTitle = findViewById(R.id.txtTitle)
        txtTitle.text = resources.getString(R.string.My_Action_Plan)

        createdList = findViewById(R.id.createdList)

    }


    fun onClick () {

        Back.setOnClickListener {

            val intent = Intent(this , HomeActivity::class.java)
            startActivity(intent)
        }


        floatingActionButton.setOnClickListener {

            val intent = Intent (this , MyActionPlanMedicationsActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++"+token)

        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var myUserId = uId.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + myUserId)

        getActionPlanList(token!!,myUserId)


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

                            var myId = getActionplanListModel.data!![0].patient_id_id

                            var sh = getSharedPreferences("Briota", MODE_PRIVATE)
                            var mytoken = sh.getString("bearer_token", "")
                            Log.d(ContentValues.TAG, "onResume: ++++" +mytoken)

                            getActionPlanDetails(mytoken,myId!!.toInt())

                            setData(getActionplanListModel)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

                    } else {
                        Utility.sharedInstance.showToastError(
                            this@MyActionPlanActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }

    private fun getActionPlanDetails(mytoken: String?, myId: Int) {

        RetrofitClient.getRetrofitInstance.geActionPlanDetailsAPI("Bearer "+mytoken,myId)
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

                            var  getActionPlanDetailsModel : GetActionPlanDetailsModel=
                                gson.fromJson(json, GetActionPlanDetailsModel::class.java)

                            Log.d(TAG, "onResponse: "+json)

                            Log.d(TAG, "onResponse: "+getActionPlanDetailsModel)




                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@MyActionPlanActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }

    private fun setData(getActionplanListModel : GetActionPlanListModel) {

        val creationList : ArrayList<data>
        creationList = getActionplanListModel.data!!

        var  getActionPlanDetailsModel : GetActionPlanDetailsModel? = GetActionPlanDetailsModel()

        Log.d(TAG, "setData: "+getActionPlanDetailsModel)
        Log.d(TAG, "setData: "+getActionPlanDetailsModel!!.created_by)
        Log.d(TAG, "setData: "+getActionPlanDetailsModel.status)

        if(creationList.size == 0)
        {
            createdList.visibility = View.GONE
            empty_list_txt.visibility = View.VISIBLE
        }
        else
        {
            createdList.visibility = View.VISIBLE
            empty_list_txt.visibility = View.GONE
        }


        createdList.adapter = CreationListAdapter(this, creationList, getActionPlanDetailsModel)

        createdList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


    }


}