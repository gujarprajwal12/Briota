package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetMedicalData {

    @SerializedName("status")
    @Expose
    val status : Boolean? = null

    @SerializedName("data")
    @Expose
    val data : GetMedicalInfoDataModel? = null

}