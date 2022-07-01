package com.briota.sia.integration_layer.models.User_Detail.GetPatientAllDetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetPatientAllDetailsResponseModel {

    @SerializedName("status")
    @Expose
    var status : Boolean? = null

    @SerializedName("message")
    @Expose
    var message : String? = null

    @SerializedName("first_name")
    @Expose
    var first_name : String? = null

    @SerializedName("last_name")
    @Expose
    var last_name : String? = null

    @SerializedName("phone_number")
    @Expose
    var phone_number : String? = null

    @SerializedName("emergency_contact")
    @Expose
    var emergency_contact : String? = null

    @SerializedName("email")
    @Expose
    var email : String? = null

    @SerializedName("role_id")
    @Expose
    var role_id : Int? = null

    @SerializedName("Profile_Data")
    @Expose
    var Profile_Data : Profile_Data? = null

    @SerializedName("Medical_Data")
    @Expose
    var Medical_Data : Medical_Data? = null

    @SerializedName("Lifestyle_Data")
    @Expose
    var Lifestyle_Data : Lifestyle_Data? = null



}