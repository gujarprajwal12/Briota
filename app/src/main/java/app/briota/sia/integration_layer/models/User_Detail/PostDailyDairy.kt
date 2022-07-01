package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostDailyDairy {

    @SerializedName("patient_id")
    @Expose
    var patient_id: Int? = null


    @SerializedName("medication")
    @Expose
    var medication: Boolean? = null

    @SerializedName("exercise")
    @Expose
    var exercise : Boolean? = null

    @SerializedName("diet")
    @Expose
    var diet: Boolean? = null

    @SerializedName("curr_date")
    @Expose
    var curr_date: String? = null

    @SerializedName("added_date")
    @Expose
    var added_date: String? = null

}