package app.briota.sia.integration_layer.models.User_Detail.Report.byDairy


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReportbyDairy{

    @SerializedName("data")
    @Expose
    var data: ArrayList<data>? = null

    @SerializedName("followed")
    @Expose
    var followed: Int? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("unfollowed")
    @Expose
    var unfollowed: Int? = null

}