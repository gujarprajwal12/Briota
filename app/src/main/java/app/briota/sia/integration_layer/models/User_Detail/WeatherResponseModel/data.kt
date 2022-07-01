package app.briota.sia.integration_layer.models.User_Detail.WeatherResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class data {

    @SerializedName("lat")
    @Expose
    var lat : Double? = null

    @SerializedName("temperature")
    @Expose
    var temperature : Float? = null

    @SerializedName("windBearing")
    @Expose
    var windBearing : Double? = null

    @SerializedName("windSpeed")
    @Expose
    var windSpeed : Double? = null

    @SerializedName("apparentTemperature")
    @Expose
    var apparentTemperature : Double? = null

    @SerializedName("dewPoint")
    @Expose
    var dewPoint : Double? = null

    @SerializedName("humidity")
    @Expose
    var humidity : Double? = null

    @SerializedName("summary")
    @Expose
    var summary : String? = null

    @SerializedName("icon")
    @Expose
    var icon : String? = null

    @SerializedName("lng")
    @Expose
    var lng : Double? = null
}