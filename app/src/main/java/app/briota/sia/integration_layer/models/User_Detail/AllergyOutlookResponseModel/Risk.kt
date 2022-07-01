package app.briota.sia.integration_layer.models.User_Detail.AllergyOutlookResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Risk {

    @SerializedName("grass_pollen")
    @Expose
    var grass_pollen : String? = null

    @SerializedName("tree_pollen")
    @Expose
    var tree_pollen : String? = null

    @SerializedName("weed_pollen")
    @Expose
    var weed_pollen : String? = null

}