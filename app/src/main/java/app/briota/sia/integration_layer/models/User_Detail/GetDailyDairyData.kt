package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetDailyDairyData {


    @SerializedName("status")
    @Expose
    val status : Int? = null

    @SerializedName("data")
    @Expose
    val data : GetDailydairyInfoDataModel? = null
}