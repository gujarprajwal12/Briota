package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetProfileModel {

    @SerializedName("date_of_birth")
    @Expose
    val date_of_birth : String? = null

    @SerializedName("height")
    @Expose
    val height :Int? = null

    @SerializedName("weight")
    @Expose
    val weight : Int? = null

    @SerializedName("gender")
    @Expose
    val gender : String? = null

    @SerializedName("address")
    @Expose
    val address : String? = null

    @SerializedName("user_id")
    @Expose
    val adduser_idress : Int? = null

    @SerializedName("ethnicity_id")
    @Expose
    val ethnicity_id : Int? = null

}