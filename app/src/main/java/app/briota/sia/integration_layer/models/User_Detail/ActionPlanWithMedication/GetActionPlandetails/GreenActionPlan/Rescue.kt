package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rescue {

    @SerializedName("id")
    @Expose
    var id : Int? = null


    @SerializedName("patient_action_plan_id_id" )
    @Expose
    var patientActionPlanIdId : Int? = null


    @SerializedName("zone_id"                   )
    @Expose
    var zoneId : String?  = null

    @SerializedName("medicine_name" )
    @Expose
    var medicineName: String?  = null

    @SerializedName("medicine_puff_count" )
    @Expose
    var medicinePuffCount     : String?  = null

    @SerializedName("morning")
    @Expose
    var morning : Boolean? = null

    @SerializedName("afternoon" )
    @Expose
    var afternoon             : Boolean? = null

    @SerializedName("evening")
    @Expose
    var evening               : Boolean? = null

    @SerializedName("night" )
    @Expose
    var night                 : Boolean? = null

    @SerializedName("category")
    @Expose
    var category              : String?  = null

    @SerializedName("date")
    @Expose
    var date                  : String?  = null

    @SerializedName("is_medication_emergency"  )
    @Expose
    var isMedicationEmergency : Boolean? = null
}