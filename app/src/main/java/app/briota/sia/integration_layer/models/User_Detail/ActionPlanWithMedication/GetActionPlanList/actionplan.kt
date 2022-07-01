package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class actionplan {

    @SerializedName("status")
    @Expose
    var status : Boolean? = null

    @SerializedName("message")
    @Expose
    var message : String? = null
}