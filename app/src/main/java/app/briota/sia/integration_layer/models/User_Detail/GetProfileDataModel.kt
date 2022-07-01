package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetProfileDataModel {

    @SerializedName("first_name")
    @Expose
    val first_name : String? = null

    @SerializedName("last_name")
    @Expose
    val last_name : String? = null

    @SerializedName("phone_number")
    @Expose
    val phone_number : String? = null

    @SerializedName("email")
    @Expose
    val email : String? = null

    @SerializedName("role_id")
    @Expose
    val role_id : String? = null

    @SerializedName("user_id")
    @Expose
    val user_id : Int? = null

    @SerializedName("is_user_verified")
    @Expose
    val is_user_verified : Boolean? = null

}