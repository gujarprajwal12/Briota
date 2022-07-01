package app.briota.sia.integration_layer.models.User_Detail


import com.google.gson.annotations.SerializedName

data class ReportModel(
    @SerializedName("diet")
    var diet: Diet,

    @SerializedName("exercise")
    var exercise: Exercise,

    @SerializedName("medication")
    var medication: Medication,

    @SerializedName("greenZoneCount")
    var greenZoneCount: Int? = null,

    @SerializedName("redZoneCount")
    var redZoneCount: Int? = null ,

    @SerializedName("yellowZoneCount")
    var yellowZoneCount: Int? = null
)