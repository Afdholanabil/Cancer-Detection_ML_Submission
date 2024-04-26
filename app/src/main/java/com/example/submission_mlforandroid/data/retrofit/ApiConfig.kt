package com.example.submission_mlforandroid.data.retrofit

import com.example.submission_mlforandroid.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        fun getApiService(): ApiService {
            val myApiKey = BuildConfig.API_KEY
            val baseUrl = BuildConfig.BASE_URL

            val authInterceptor = Interceptor {chain ->
                val req =chain.request()
                val requestHeader = req.newBuilder()
                    .addHeader("Authorization", myApiKey)
                    .build()
                chain.proceed(requestHeader)
            }

            val client = OkHttpClient.Builder().addInterceptor(authInterceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}