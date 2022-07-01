package app.briota.sia.integration_layer.models.User_Detail.Report


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Reportbyzone {

    @SerializedName("data")
    @Expose
    var data: ArrayList<data>? = null

    @SerializedName("greenZoneCount")
    @Expose
    var greenZoneCount: Int? = null

    @SerializedName("yellowZoneCount")
    @Expose
    var yellowZoneCount: Int? = null

    @SerializedName("redZoneCount")
    @Expose
    var redZoneCount: Int? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

}