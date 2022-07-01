package app.briota.sia.integration_layer.utilities

import android.app.Application
import android.content.Context
import app.briota.sia.Network.retrofit.api.ApiClinetService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    var context: Context? = null
    private var instance: MyApplication? = null
    private var __retrofit: Retrofit? = null
    private var api: ApiClinetService? = null

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        // The following line triggers the initialization of ACRA
        // ACRA.init(this);
    }


    fun getInstance(): MyApplication? {
        if (instance == null) {
            instance = MyApplication()
        }
        return instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = MyApplication()
        context = applicationContext
//        val sharedPreferenceManager = SharedPreferenceManager(context)
//        sharedPreferenceManager.initializeInstance(context)
    }

    fun getRetrofitInstance(): Retrofit {
        if (__retrofit == null) __retrofit = Retrofit.Builder().baseUrl("http://3.7.36.252:8000/api/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return __retrofit!!
    }

    fun getAPIInstance(): ApiClinetService? {
        if (api == null) api = getRetrofitInstance().create(ApiClinetService::class.java)
        return api
    }

}