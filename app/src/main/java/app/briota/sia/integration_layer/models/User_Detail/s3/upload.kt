package app.briota.sia.integration_layer.models.User_Detail.s3

import com.google.gson.annotations.SerializedName


  data  class upload (

    @SerializedName("filename" )
    var filename   : String? = null,

    @SerializedName("s3_file_path" )
    var s3FilePath : String? = null

)