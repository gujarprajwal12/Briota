package app.briota.sia.integration_layer.models.User_Detail
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostMedcialPatienSpiroReading(



    @SerializedName("created_datetime")
    @Expose
    var createdDatetime: String? = null,

    @SerializedName("daily_zone")
    @Expose
    var dailyZone: String? = null,

    @SerializedName("fev_1")
    @Expose
    var fev1: Int? = null,

    @SerializedName("fvc")
    @Expose
    var fvc: Int? = null,

    @SerializedName("patient_id")
    @Expose
    var patientId: Int? = null,

    @SerializedName("pulse")
    @Expose
    var pulse: Int? = null,

    @SerializedName("reading_datetime")
    @Expose
    var readingDatetime: String? = null,

    @SerializedName("spo_2")
    @Expose
    var spo2: Int? = null,

    @SerializedName("temperature")
    @Expose
    var temperature: Int? = null
)