package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResendOTPRequestModel {

    @SerializedName("status")
    @Expose
    var status : Boolean? = null

    @SerializedName("message")
    @Expose
    var message : String? = null

}