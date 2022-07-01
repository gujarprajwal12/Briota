package app.briota.sia.integration_layer.models.User_Detail.AllergyOutlookResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AllergyOutlookResponseModel {

    @SerializedName("message")
    @Expose
    var message : String? = null

    @SerializedName("lat")
    @Expose
    var lat : Double? = null

    @SerializedName("lng")
    @Expose
    var lng : Double? = null

    @SerializedName("data")
    @Expose
    var data : ArrayList<data>? = null

}