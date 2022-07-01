package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResendOTPModel {

    @SerializedName("email")
    @Expose
    var email : String? = null

    @SerializedName("language_id")
    @Expose
    var language_id: String? = null

}