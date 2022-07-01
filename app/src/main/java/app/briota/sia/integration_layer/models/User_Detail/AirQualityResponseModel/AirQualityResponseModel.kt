package app.briota.sia.integration_layer.models.User_Detail.AirQualityResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AirQualityResponseModel {

    @SerializedName("message")
    @Expose
    var message : String? = null

    @SerializedName("stations")
    @Expose
    var stations : ArrayList<stations>? = null
}