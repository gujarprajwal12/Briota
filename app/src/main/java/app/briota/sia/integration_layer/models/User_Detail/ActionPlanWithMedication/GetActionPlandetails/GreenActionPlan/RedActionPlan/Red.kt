package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan

import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.RedActionPlan.RDaily
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.RedActionPlan.RRescue
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.RedActionPlan.RSteroid
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Red1 {

    @SerializedName("daily")
    @Expose
    var reddaily : ArrayList<RDaily>? = null

    @SerializedName("rescue")
    @Expose
    var redrescue : ArrayList<RRescue>? = null

    @SerializedName("steroid")
    @Expose
    var redsteroid : ArrayList<RSteroid>? = null

}