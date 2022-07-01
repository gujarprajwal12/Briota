package app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostActionPlanWithMedModel {

    @SerializedName("patient_id")
    @Expose
    var patient_id : Int? = null

    @SerializedName("doctor_id")
    @Expose
    var doctor_id : Int? = null

    @SerializedName("diagnosis")
    @Expose
    var diagnosis : String? = null

    @SerializedName("dr_phone")
    @Expose
    var dr_phone : String? = ""

    @SerializedName("action_plan_id")
    @Expose
    var action_plan_id : Int? = null

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

    @SerializedName("photo_url")
    @Expose
    var photo_url : String? = null

    @SerializedName("Green")
    @Expose
    var Green : Green? = null

    @SerializedName("Yellow")
    @Expose
    var Yellow :Yellow? = null

    @SerializedName("Red")
    @Expose
    var Red : Red? = null

}