package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignInFailedResponseModel {

    @SerializedName("non_field_errors")
    @Expose
    var non_field_errors: Array<String>? = null


}