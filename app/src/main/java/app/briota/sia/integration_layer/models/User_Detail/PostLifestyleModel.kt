package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostLifestyleModel {

    @SerializedName("is_smoker")
    @Expose
    var is_smoker : Boolean? = null

    @SerializedName("smoking_frequency")
    @Expose
    val smoking_frequency : String? = null

    @SerializedName("tobacco_nicotine")
    @Expose
    val tobacco_nicotine : Boolean? = null

    @SerializedName("tobacco_frequency")
    @Expose
    val tobacco_frequency : String? = null

    @SerializedName("exercise")
    @Expose
    val exercise : String? = null

    @SerializedName("user_id")
    @Expose
    val user_id : Int? = null

}