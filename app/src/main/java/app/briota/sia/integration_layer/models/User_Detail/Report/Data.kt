package app.briota.sia.integration_layer.models.User_Detail.Report


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class data{

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("fev_1")
    @Expose
    var fev_1: Int? = null

    @SerializedName("pef")
    @Expose
    var pef: Int? = null

    @SerializedName("time")
    @Expose
    var time: String? = null
}