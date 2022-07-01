package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails

import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.actionplan
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.Green
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.Red1
import com.example.briota.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.Yellow1
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetActionPlanDetailsModel {

    @SerializedName("status")
    @Expose
    var status : Boolean? = null

    @SerializedName("data")
    @Expose
    var data : ArrayList<data>? = null

    @SerializedName("actionplan")
    @Expose
    var actionplan : actionplan? = null

    @SerializedName("created_by")
    @Expose
    var created_by : String? = null

    @SerializedName("Green")
    @Expose
    var Green : ArrayList<Green>? = null

    @SerializedName("Yellow")
    @Expose
    var Yellow1 : ArrayList<Yellow1>? = null

    @SerializedName("Red")
    @Expose
    var Red1 : ArrayList<Red1>? = null
}