package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.PostLifestyleRequest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostLifestyleRequestModel {

    @SerializedName("status")
    @Expose
    var status : Boolean? = null

    @SerializedName("message")
    @Expose
    var message : String? = null

    @SerializedName("data")
    @Expose
    var data : Data? = null
}