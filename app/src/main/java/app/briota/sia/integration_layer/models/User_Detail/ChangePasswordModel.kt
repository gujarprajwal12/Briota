package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChangePasswordModel {

    @SerializedName("otp")
    @Expose
    var otp : String? = null

    @SerializedName("new_password")
    @Expose
    var new_password : String? = null

    @SerializedName("confirm_password")
    @Expose
    var confirm_password : String? = null

    @SerializedName("email")
    @Expose
    var email : String? = null
}