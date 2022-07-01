package app.briota.sia.integration_layer.models.User_Detail.ReminderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UpdateReminderRequestModel {


    @SerializedName("patient_id")
    @Expose
    var patient_id : Int? = null

    @SerializedName("medication")
    @Expose
    var medication : Boolean? = null

    @SerializedName("exercise")
    @Expose
    var exercise : Boolean? = null

    @SerializedName("diet")
    @Expose
    var diet : Boolean? = null

    @SerializedName("added_time")
    @Expose
    var added_time : String? = null

}