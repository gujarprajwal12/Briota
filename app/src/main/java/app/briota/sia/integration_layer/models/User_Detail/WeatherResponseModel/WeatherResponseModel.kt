package app.briota.sia.integration_layer.models.User_Detail.WeatherResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherResponseModel {

    @SerializedName("message")
    @Expose
    var message : String? = null

    @SerializedName("data")
    @Expose
    var data : data? = data()
}