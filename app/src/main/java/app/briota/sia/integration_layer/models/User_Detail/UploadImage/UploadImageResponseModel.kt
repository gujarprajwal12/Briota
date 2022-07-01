package app.briota.sia.integration_layer.models.User_Detail.UploadImage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class UploadImageResponseModel {

    @SerializedName("filename")
    @Expose
    var filename : String? = null

    @SerializedName("s3_file_path")
    @Expose
    var s3_file_path : String? = null

}