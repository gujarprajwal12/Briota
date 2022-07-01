package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Red {

    @SerializedName("rescue")
    @Expose
    var rescue : ArrayList<rescue>? = null

    @SerializedName("steroid")
    @Expose
    var steroid : ArrayList<steroid>? = null

}