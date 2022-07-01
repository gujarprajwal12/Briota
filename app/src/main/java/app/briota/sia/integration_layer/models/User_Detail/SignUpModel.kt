package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SignUpModel {

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("profile")
    @Expose
    var profile: SignUpProfileModel? = null


    @SerializedName("role_id")
    @Expose
    var role_id: Int? = 3

    @SerializedName("language_id")
    @Expose
    var language_id: String? = null



}