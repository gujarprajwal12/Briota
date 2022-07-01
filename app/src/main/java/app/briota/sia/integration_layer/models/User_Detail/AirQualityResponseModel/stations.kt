package app.briota.sia.integration_layer.models.User_Detail.AirQualityResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class stations {

    @SerializedName("city")
    @Expose
    var city : String? = null

    @SerializedName("countryCode")
    @Expose
    var countryCode : String? = null

    @SerializedName("division")
    @Expose
    var division : String? = null

    @SerializedName("lat")
    @Expose
    var lat : Double? = null

    @SerializedName("lng")
    @Expose
    var lng : Double? = null

    @SerializedName("placeName")
    @Expose
    var placeName : String? = null

    @SerializedName("postalCode")
    @Expose
    var postalCode : String? = null

    @SerializedName("state")
    @Expose
    var state : String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt : String? = null

    @SerializedName("AQI")
    @Expose
    var AQI : Int? = null

    @SerializedName("aqiInfo")
    @Expose
    var aqiInfo : aqiInfo? = null


}