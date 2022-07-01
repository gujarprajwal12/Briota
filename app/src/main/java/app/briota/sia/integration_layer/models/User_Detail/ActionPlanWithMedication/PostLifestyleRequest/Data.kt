package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.PostLifestyleRequest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("id")
    @Expose
    var id : Int? = null

    @SerializedName("diagnosis")
    @Expose
    var diagnosis : String? = null

    @SerializedName("other_diagnosed")
    @Expose
    var other_diagnosed : Boolean? = null

    @SerializedName("other_diagnosed_specify")
    @Expose
    var other_diagnosed_specify : String? = null

    @SerializedName("Medication")
    @Expose
    var Medication : Boolean? = null

    @SerializedName("medication_specify")
    @Expose
    var medication_specify : String? = null

    @SerializedName("user_id")
    @Expose
    var user_id : Int? = null

}