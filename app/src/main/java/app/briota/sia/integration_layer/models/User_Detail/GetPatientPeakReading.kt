package app.briota.sia.integration_layer.models.User_Detail


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 class GetPatientPeakReading {


     @SerializedName("data")
     @Expose
     var data: ArrayList<PeakData>? = null


    @SerializedName("status")
    @Expose
    var status: Boolean? = null



}