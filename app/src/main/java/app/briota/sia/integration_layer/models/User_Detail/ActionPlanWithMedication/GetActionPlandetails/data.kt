package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class data {

    @SerializedName("id")
    @Expose
    var id : Int? = null

    @SerializedName("patient_id_id")
    @Expose
    var patient_id_id : Int? = null

    @SerializedName("doctor_id")
    @Expose
    var doctor_id : Int? = null

    @SerializedName("diagnosis")
    @Expose
    var diagnosis : String? = null

    @SerializedName("dr_phone")
    @Expose
    var dr_phone : String? = null

    @SerializedName("photo_url")
    @Expose
    var photo_url : String? = null


    @SerializedName("green_pef")
    @Expose
    var green_pef : String? = null

    @SerializedName("green_fev")
    @Expose
    var green_fev : String? = null

    @SerializedName("yellow_pef")
    @Expose
    var yellow_pef : String? = null

    @SerializedName("yellow_fev")
    @Expose
    var yellow_fev : String? = null

    @SerializedName("red_pef")
    @Expose
    var red_pef : String? = null

    @SerializedName("red_fev")
    @Expose
    var red_fev : String? = null

}