package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Green {

    @SerializedName("daily")
    @Expose
    var daily : ArrayList<Daily>? = null

    @SerializedName("rescue")
    @Expose
    var rescue : ArrayList<Rescue>? = null

    @SerializedName("steroid")
    @Expose
    var steroid : ArrayList<Steroid>? = null

}