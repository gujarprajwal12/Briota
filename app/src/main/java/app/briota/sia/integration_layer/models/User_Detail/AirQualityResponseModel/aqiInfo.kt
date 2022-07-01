package app.briota.sia.integration_layer.models.User_Detail.AirQualityResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class aqiInfo {

    @SerializedName("category")
    @Expose
    var category : String? = null
}