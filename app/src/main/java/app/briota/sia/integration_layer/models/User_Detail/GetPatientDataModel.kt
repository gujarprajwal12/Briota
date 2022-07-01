package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetPatientDataModel {





    @SerializedName("id")
    @Expose
    val id : Int? = null

    @SerializedName("date_of_birth")
    @Expose
    val date_of_birth : String? = null

    @SerializedName("height")
    @Expose
    val height : Double? = null

    @SerializedName("weight")
    @Expose
    val weight : Double? = null

    @SerializedName("gender")
    @Expose
    val gender : String? = null

    @SerializedName("address")
    @Expose
    val address : String? = null

    @SerializedName("user_id")
    @Expose
    val user_id : Boolean? = null

}