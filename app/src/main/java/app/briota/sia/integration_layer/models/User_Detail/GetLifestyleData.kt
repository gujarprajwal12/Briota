package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetLifestyleData {


    @SerializedName("status")
    @Expose
    val status : Boolean? = null

    @SerializedName("data")
    @Expose
    val data : GetLifestyleInfoInfoDataModel? = null

}