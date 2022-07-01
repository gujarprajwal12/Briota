package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignInFailedResponse {

    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("status_code")
    @Expose
    var status_code: Int? = null

    @SerializedName("message")
    @Expose
    var message: SignInFailedResponseModel? = null
}