package app.briota.sia.integration_layer.models.User_Detail.UploadImage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UploadImageRequest {

    @SerializedName("photo")
    @Expose
    var photo : String? = null
}