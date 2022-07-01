package app.briota.sia.integration_layer.models.User_Detail.Report.byDairy


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class data{

    @SerializedName("added_date")
    @Expose
    var addedDate: String? = null

    @SerializedName("followed")
    @Expose
    var followed: Boolean? = null

    @SerializedName("time")
    @Expose
    var time: String? = null

}