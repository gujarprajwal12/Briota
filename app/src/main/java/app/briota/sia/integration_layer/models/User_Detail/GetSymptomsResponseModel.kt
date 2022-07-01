package app.briota.sia.integration_layer.models.User_Detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetSymptomsResponseModel {

    @SerializedName("status")
    @Expose
    val status : Boolean? = null

    @SerializedName("Description")
    @Expose
    val Description : List<GetSymptomsList>? = null

}