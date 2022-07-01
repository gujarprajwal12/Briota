package app.briota.sia.integration_layer.models.User_Detail


import com.google.gson.annotations.SerializedName

data class PeakData(


    @SerializedName("created_datetime")
    var createdDatetime: String? = null,

    @SerializedName("daily_zone")
    var dailyZone: String? = null,

    @SerializedName("fev_1")
    var fev1: Int?  = null,

    @SerializedName("fvc")
    var fvc: Int?  = null,

    @SerializedName("id")
    var id: Int?  = null,

    @SerializedName("patient_id_id")
    var patientIdId: Int?  = null,

    @SerializedName("pef")
    var pef: Int?  = null,

    @SerializedName("pulse")
    var pulse: Int?  = null,

    @SerializedName("reading_datetime")
    var readingDatetime: String?  = null,

    @SerializedName("spo_2")
    var spo2: Int?  = null,

    @SerializedName("temperature")
    var temperature: Int?  = null
)