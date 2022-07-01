package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetProfileInfoDataModel {

    @SerializedName("emergency_contact")
    @Expose
    val emergency_contact : String? = null

    @SerializedName("date_of_birth")
    @Expose
    val date_of_birth : Int? = null

    @SerializedName("height")
    @Expose
    val height : String? = null


    @SerializedName("weight")
    @Expose
    val weight : String? = null

    @SerializedName("gender")
    @Expose
    val gender : String? = null

    @SerializedName("address")
    @Expose
    val address : String? = null

    @SerializedName("user_id")
    @Expose
    val user_id : Int? = null

    @SerializedName("ethnicity_id")
    @Expose
    val ethnicity_id : String? = null

    @SerializedName("other_ethincity")
    @Expose
    val other_ethincity : String? = null
}