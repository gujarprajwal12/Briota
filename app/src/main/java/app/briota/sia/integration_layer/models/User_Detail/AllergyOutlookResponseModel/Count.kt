package app.briota.sia.integration_layer.models.User_Detail.AllergyOutlookResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Count {

    @SerializedName("grass_pollen")
    @Expose
    var grass_pollen : Int? = null

    @SerializedName("tree_pollen")
    @Expose
    var tree_pollen : Int? = null

    @SerializedName("weed_pollen")
    @Expose
    var weed_pollen : Int? = null

}