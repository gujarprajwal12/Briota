package app.briota.sia.integration_layer.models.User_Detail.ReminderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetReminderResponseModel {

    @SerializedName("status")
    @Expose
    var status : Int? = null

    @SerializedName("message")
    @Expose
    var message : String? = null

    @SerializedName("data")
    @Expose
    var data : ArrayList<data>? = null
}