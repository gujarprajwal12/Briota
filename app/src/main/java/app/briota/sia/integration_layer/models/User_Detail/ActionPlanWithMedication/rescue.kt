package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class rescue {

    @SerializedName("medicine_name" )
    @Expose
    var medicine_name : String?  = null

    @SerializedName("puff_or_dose"  )
    @Expose
    var puff_or_dose   : String?  = null

    @SerializedName("morning" )
    @Expose
    var morning      : Boolean? = null

    @SerializedName("afternoon" )
    @Expose
    var afternoon    : Boolean? = null

    @SerializedName("evening")
    @Expose
    var evening      : Boolean? = null

    @SerializedName("night" )
    @Expose
    var night        : Boolean? = null

}

