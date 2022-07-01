package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EditProfileInfoModel {

    @SerializedName("user_id")
    @Expose
    var user_id: Int? = null


    @SerializedName("date_of_birth")
    @Expose
    var date_of_birth: Int? = null

    @SerializedName("emergency_contact")
    @Expose
    var emergency_contact: String? = null


    @SerializedName("height")
    @Expose
    var height: Int? = null


    @SerializedName("weight")
    @Expose
    var weight: Int? = null


    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("ethnicity_id")
    @Expose
    var ethnicity_id: Int? = null

    @SerializedName("other_ethincity")
    @Expose
    var  other_ethincity: String? = null


}