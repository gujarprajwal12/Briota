package app.briota.sia.Network.retrofit.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {



    private const val BASE_URL = "http://3.7.36.252:8000/api/"

    private var okHttpClient = OkHttpClient.Builder()

        .connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)


        .addInterceptor { chain ->
            val original = chain.request()


            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)


            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val getRetrofitInstance: ApiClinetService by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiClinetService::class.java)
    }
}

