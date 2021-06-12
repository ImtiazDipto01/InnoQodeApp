package com.example.mygalleryapp.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient private constructor() {

    companion object{

        private const val BASE_URL : String = "https://techcrunch.com/wp-json/wp/v2/"
        const val TIME_OUT_LIMIT : Long = 30
        var retrofit : Retrofit? = null

        @Synchronized
        fun getInstance() : Retrofit? = when(retrofit){
            null -> {
                val gson = GsonBuilder().setLenient().create()

                val client = OkHttpClient.Builder().connectTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS)
                    .build()

                retrofit =  Retrofit.Builder().baseUrl(BASE_URL).client(client).
                    addConverterFactory(GsonConverterFactory.create(gson)).build()

                retrofit
            }
            else -> retrofit
        }

        fun getApiService(): ApiService? = getInstance()?.create(ApiService::class.java)
    }
}