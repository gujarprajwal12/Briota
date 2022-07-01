package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VerifyOTPModel {

    @SerializedName("otp")
    @Expose
    var otp : String? = null

    @SerializedName("email")
    @Expose
    var email : String? = null


}