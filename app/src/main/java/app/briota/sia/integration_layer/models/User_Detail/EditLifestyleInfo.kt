package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EditLifestyleInfo {

    @SerializedName("is_smoker")
    @Expose
    var is_smoker : Boolean? = null

    @SerializedName("smoking_frequency")
    @Expose
    var smoking_frequency : String? = null

    @SerializedName("tobacco_nicotine")
    @Expose
    var tobacco_nicotine : Boolean? = null

    @SerializedName("tobacco_frequency")
    @Expose
    var tobacco_frequency : String? = null

    @SerializedName("exercise")
    @Expose
    var exercise : String? = null

    @SerializedName("user_id")
    @Expose
    var user_id : Int? = null
}