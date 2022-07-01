package app.briota.sia.integration_layer.models.User_Detail


import com.google.gson.annotations.SerializedName

data class Diet(
    @SerializedName("followed")
    var followed: Int? = null,

    @SerializedName("unfollowed")
    var unfollowed: Int? = null
)