package app.briota.sia.integration_layer.models.User_Detail


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostLifeStyleInfoModel(



    @SerializedName("exercise")
    @Expose
    var exercise: String? = null,


    @SerializedName("is_smoker")
    @Expose
    var isSmoker: Boolean? =null,


    @SerializedName("smoking_frequency")
    @Expose
    var smokingFrequency: String? =null,


    @SerializedName("tobacco_frequency")
    @Expose
    var  tobaccoFrequency: Any? =null,

    @SerializedName("tobacco_nicotine")
    @Expose
    var  tobaccoNicotine: Boolean? = null,


    @SerializedName("user_id")
    @Expose
    var userId: Int? = null


    )