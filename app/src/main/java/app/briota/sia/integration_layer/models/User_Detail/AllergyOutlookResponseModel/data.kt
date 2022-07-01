package app.briota.sia.integration_layer.models.User_Detail.AllergyOutlookResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class data {

    @SerializedName("Count")
    @Expose
    var Count : Count? = Count()

    @SerializedName("Risk")
    @Expose
    var Risk : Risk? = Risk()


}