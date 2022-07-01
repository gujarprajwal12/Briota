package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileInfoDResponseModel {

    @SerializedName("status")
    @Expose
    val status : Boolean? = null


    @SerializedName("message")
    @Expose
    val message : String? = null

    @SerializedName("data")
    @Expose
    val data : GetPatientDataModel? = null

}