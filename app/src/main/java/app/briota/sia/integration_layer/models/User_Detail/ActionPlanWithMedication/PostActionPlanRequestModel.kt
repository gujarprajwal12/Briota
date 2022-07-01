package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostActionPlanRequestModel {

    @SerializedName("status")
    @Expose
    var status : Boolean? = null

    @SerializedName("action_plan_id")
    @Expose
    var action_plan_id : Float? = null

    @SerializedName("message")
    @Expose
    var message : String? = null

}