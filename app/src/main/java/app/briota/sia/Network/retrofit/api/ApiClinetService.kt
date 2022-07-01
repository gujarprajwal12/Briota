package app.briota.sia.Network.retrofit.api

import app.briota.sia.integration_layer.models.User_Detail.*
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.PostActionPlanWithMedModel
import app.briota.sia.integration_layer.models.User_Detail.ReminderModel.PostReminderModel
import app.briota.sia.integration_layer.models.User_Detail.ReminderModel.UpdateReminderRequestModel
import app.briota.sia.integration_layer.models.User_Detail.UploadImage.UploadImageRequest
import app.briota.sia.integration_layer.models.User_Detail.UploadImage.UploadImageResponseModel
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiClinetService {


    //SignUp
    @Headers("Content-Type: application/json")
    @POST("authorize/signup/")
    fun signUPAPI(@Body signUpModelRequest: SignUpModel?): Call<SignUpResponseModel>


    //SignIn

    @FormUrlEncoded
    @POST("authorize/login/")
    fun signin(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<SignInResponseModel>


    //GetProfile API

    @Headers("Content-Type: application/json")
    @GET("authorize/userprofile/")
    fun getProfileApi(
    @Header("Authorization") token : String
    ):Call<Any>

    //GetProfileInfo API

    @Headers("Content-Type: application/json")
    @GET("profiles/patient/profileinfo/{lang}/{user_id}/")
    fun getProfileInfoApi(
        @Header("Authorization") token : String,
        @Path("lang") lang : String,
        @Path("user_id") user_id : Int
    ):Call<Any>


    //GetEthnicity API

    @Headers("Content-Type: application/json")
    @GET("profiles/patient/getethnicity/{lang}/")
    fun getEthnicityApi(
        @Path("lang") lang : String
    ):Call<Any>

    // GetSymptoms

    @Headers("Content-Type: application/json")
    @GET("profiles/patient/getsymptoms/{lang}/")
    fun getSymptomsApi(
        @Header("token") token : String,
        @Path("lang") lang : String
    ):Call<Any>


    // GetTriggers

    @Headers("Content-Type: application/json")
    @GET("profiles/patient/gettriggers/{lang}/{gender}/{age}/")
    fun getTriggerApi(
        @Header("token") token: String,
        @Path("lang") lang : String,
        @Path("gender") gender: String,
        @Path("age") age: Int

    ):Call<Any>


    //PostProfile
    @Headers("Content-Type: application/json")
    @POST("profiles/patient/profileinfo/")
    fun profilePostApi(
        @Header("Authorization") token: String,
        @Body profileInfoPostModelRequest: ProfileInfoPostModel?): Call<Any>

    //PostLifeStyleInfo
    @Headers("Content-Type: application/json")
    @POST("profiles/patient/lifestyleinfo/")
    fun lifestyleInfoPostApi(
        @Header("Authorization") token : String,
        @Body lifeStyleInfoModel: PostLifeStyleInfoModel?
    ) :Call<Any>


    //PostMedicalInfo
    @Headers("Content-Type: application/json")
    @POST("profiles/patient/medicalinfo/")
    fun medicalInfoPostApi(
        @Header("Authorization") token : String,
        @Body postMedicalInfo: PostMedicalInfo?
    ) :Call<Any>

    // Verify OTP API

    @Headers("Content-Type: application/json")
    @POST("authorize/verifyotp/")
    fun verifyOTPApi(
        @Body verifyOTPRequest : VerifyOTPModel?
    ):Call<Any>

    // Resend OTP API

    @Headers("Content-Type: application/json")
    @POST("authorize/sendemail/")
    fun resendOTPAPI(
      @Body resendOTPRequest : ResendOTPModel?
    ):Call<Any>

    // Change Password API

    @Headers("Content-Type: application/json")
    @POST("authorize/changepassword/")
    fun changePasswordAPI(
        @Body changePasswordRequest : ChangePasswordModel?
    ):Call<Any>





   //get Patient Medical ID

    @Headers("Content-Type: application/json")
    @GET("profiles/patient/medicalinfo/{lang}/{user_id}/")

    fun getMedicalInfoApi(
        @Header("Authorization") token : String,
        @Path("lang") lang : String,
        @Path("user_id") user_id : Int
    ):Call<Any>




  //get  Patient Lifestyle ID

    @Headers("Content-Type: application/json")
    @GET("profiles/patient/lifestyleinfo/{user_id}/")

    fun getLifestyleInfoApi(
        @Header("Authorization") token : String,
        @Path("user_id") user_id : Int
    ):Call<Any>


    // Logout API

    @Headers("Content-Type: application/json")
    @POST("authorize/logout/")
    fun postLogoutAPI(
        @Header("Authorization") token : String,
    ):Call<Any>


    // Patch Profile API

    @Headers("Content-Type: application/json")
    @PATCH("profiles/patient/profileinfo/{lang}/{user_id}/")
    fun putProfileInfo(
        @Header("Authorization") token : String,
        @Body editProfileRequest: EditProfileInfoModel?,
        @Path("lang") lang : String,
        @Path("user_id") user_id: String
    ):Call<Any>


    // Patch Lifestyle API

    @Headers("Content-Type: application/json")
    @PATCH("profiles/patient/lifestyleinfo/{user_id}/")
    fun putLifestyleInfo(
        @Header("Authorization") token : String,
        @Body editLifestyleRequest: EditLifestyleInfo?,
        @Path("user_id") user_id: Int
    ):Call<Any>

    // Patch Medical API

    @Headers("Content-Type: application/json")
    @PATCH("profiles/patient/medicalinfo/{lang}/{user_id}/")
    fun putMedicalInfo(
        @Header("Authorization") token: String,
        @Body editMedicalRequest: EditMedicalInfo?,
        @Path("lang") lang : String,
        @Path("user_id") user_id: Int?
    ):Call<Any>

    //get  Patient Lifestyle ID

    @Headers("Content-Type: application/json")
    @GET("actionplan/addpatientactionplan/medical/patientreading/{user_id}/")
    fun getSpiroReadingAPI(
        @Header("Authorization") token : String?,
        @Path("user_id") user_id : Int?
    ):Call<Any>


   // post medical spiro reading

   @Headers("Content-Type: application/json")
   @POST("actionplan/addpatientactionplan/medical/patientreading/")
   fun medicalPatientReading(
       @Header("Authorization") token : String,
       @Body postMedicalPatienSpiroReading: PostMedcialPatienSpiroReading?
   ) :Call<Any>




    // post medical peak reading

    @Headers("Content-Type: application/json")
    @POST("actionplan/addpatientactionplan/medical/patientreading/")
    fun medicalPatientReadingsprio(
        @Header("Authorization") token : String,
        @Body postMedicalPatientPeekReading: PostMedicalPatientPeekReading
    ) :Call<Any>


    //get  medical spiro eading


    @Headers("Content-Type: application/json")
    @GET("actionplan/addpatientactionplan/medical/patientreading/{user_id}/")

    fun getmedicalspiro(
        @Header("Authorization") token : String,
        @Path("user_id") user_id : Int
    ):Call<Any>



    //get  medical peak reading

    @Headers("Content-Type: application/json")
    @GET("actionplan/addpatientactionplan/medical/patientreading/{user_id}/")
    fun getmedicalpeak(
        @Header("Authorization") token: String,
        @Path("user_id") user_id: Int
    ):Call<Any>



    // Post Action Plan API

    @Headers("Content-Type: application/json")
    @POST("actionplan/addpatientactionplan/medical/")
    fun actionMedPlanAPI(
        @Header("Authorization") token : String,
        @Body postActionPlanRequest: PostActionPlanWithMedModel?
    ) :Call<Any>


    // Get Action Plan List API

    @Headers("Content-Type: application/json")
    @GET("actionplan/addpatientactionplan/medical/{user_id}/")
    fun geActionPlanList(
        @Header("Authorization") token: String,
        @Path("user_id") user_id: Int
    ):Call<Any>

    // Get Action Plan All details API

    @Headers("Content-Type: application/json")
    @GET("actionplan/addpatientactionplan/medical/details/{id}/")
    fun geActionPlanDetailsAPI(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ):Call<Any>

    // Patient All Details API

    @Headers("Content-Type: application/json")
    @GET("profiles/patient/details/{lang}/{user_id}/")
    fun getPatientPlanDetailsAPI(
        @Header("Authorization") token: String,
        @Path("lang") lang : String,
        @Path("user_id") user_id: Int
    ):Call<Any>



   // post api for daily diary

    //PostDailyDairy
    @Headers("Content-Type: application/json")
    @POST("dailydairy/adddailydairy/")
    fun DailyDairyPostApi(
        @Header("Authorization") token : String,
        @Body postDailyDairy: PostDailyDairy?
    ) :Call<Any>


    // Patch DailyDairy

    @Headers("Content-Type: application/json")
    @PUT("dailydairy/updatedailydairy/{user_id}")
    fun putDailyDairy(
        @Header("Authorization") token: String,
        @Body putDailyDairyModel: PutDailyDairyModel?,
        @Path("user_id") user_id: Int?
    ):Call<Any>




    //get DailyDairy

    @Headers("Content-Type: application/json")
    @GET("dailydairy/getdailydairy/{user_id}/{start_date}/{end_date}")

    fun getDailyDairyApi(
        @Header("Authorization") token : String,
        @Path("user_id") user_id : Int ,
        @Path("start_date") start_date : String,
        @Path("end_date") end_date: String,
    ):Call<Any>



    //get Report

    @Headers("Content-Type: application/json")
    @GET("report/getreport/{user_id}/{start_date}/{end_date}/")

    fun getReport(
        @Header("Authorization") token : String,
        @Path("user_id") user_id : Int ,
        @Path("start_date") start_date : String,
        @Path("end_date") end_date: String,
    ):Call<Any>


    //get dailydairyreport

    @Headers("Content-Type: application/json")
    @GET("report/getdailydairyreport/{user_id}/{diary}/{start_date}/{end_date}/")

    fun getReportdailydairy(
        @Header("Authorization") token : String,
        @Path("user_id") user_id : Int ,
        @Path("diary") diary : String,
        @Path("start_date") start_date : String,
        @Path("end_date") end_date: String,
    ):Call<Any>



    //get dailydairyreportbyzone

    @Headers("Content-Type: application/json")
    @GET("report/getdailyzonereport/{user_id}/{zone}/{start_date}/{end_date}/")

    fun getReportzonedairy(
        @Header("Authorization") token : String,
        @Path("user_id") user_id : Int ,
        @Path("zone") zone : String,
        @Path("start_date") start_date : String,
        @Path("end_date") end_date: String,
    ):Call<Any>


    //Upload Image to S3
    @Multipart
    @POST("actionplan/addpatientactionplan/addimage/")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Call<UploadImageResponseModel>



    //put api for s3 bucket

    @Headers("Content-Type: application/json")
    @PUT("actionplan/updatepatientactionplan/medical/{id}")
    fun updateactionplan(
        @Header("Authorization") token: String,
        @Body updateReminderModel: PostActionPlanWithMedModel?,
        @Path("id") id: Int?
    ):Call<Any>

    //REMOVE Action Plan
    @Headers("Content-Type: application/json")
    @DELETE("actionplan/deletepatientactionplan/medical/{id}")
    fun removeActionPlan(
        @Header("Authorization") token : String,
        @Path("id") id : Int
    ): Call<Any>

    // Post Reminder API

    @Headers("Content-Type: application/json")
    @POST("reminders/addreminders/")
    fun postReminderAPI(
        @Header("Authorization") token : String,
        @Body postReminder: PostReminderModel?
    ) :Call<Any>


    @Headers("Content-Type: application/json")
    @GET("reminders/getreminders/{user_id}/")
    fun getReminderApi(
        @Header("Authorization") token : String,
        @Path("user_id") user_id : Int
    ):Call<Any>


    //REMOVE Reminder
    @Headers("Content-Type: application/json")
    @DELETE("reminders/deletereminders/{id}/")
    fun removeReminder(
        @Header("Authorization") token : String,
        @Path("id") id : Int
    ): Call<Any>


    @Headers("Content-Type: application/json")
    @PUT("reminders/updatereminders/{id}")
    fun updateReminder(
        @Header("Authorization") token: String,
        @Body updateReminderModel: UpdateReminderRequestModel?,
        @Path("id") id: Int?
    ):Call<Any>

    @Headers("Content-Type: application/json")
    @GET("https://api.ambeedata.com/weather/latest/by-lat-lng")
    fun getCurrentWeather(
        @Header("x-api-key") key : String,
        @Query("lat") lat : String,
        @Query("lng") lng : String,
    ): Call<JsonObject>

    @Headers("Content-Type: application/json")
    @GET("https://api.ambeedata.com/latest/by-lat-lng")
    fun getAirQuality(
        @Header("x-api-key") key : String,
        @Query("lat") lat : String,
        @Query("lng") lng : String,
    ): Call<JsonObject>


    @Headers("Content-Type: application/json")
    @GET("https://api.ambeedata.com/latest/pollen/by-lat-lng")
    fun getAllergyOutlook(
        @Header("x-api-key") key : String,
        @Query("lat") lat : String,
        @Query("lng") lng : String,
    ): Call<JsonObject>


}