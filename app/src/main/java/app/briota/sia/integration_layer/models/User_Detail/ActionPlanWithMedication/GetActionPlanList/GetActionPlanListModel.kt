package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetActionPlanListModel {

    @SerializedName("status")
    @Expose
    var status : Boolean? = null

    @SerializedName("data")
    @Expose
    var data : ArrayList<data>? = null
}