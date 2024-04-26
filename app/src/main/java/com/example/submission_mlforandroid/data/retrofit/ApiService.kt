package com.example.submission_mlforandroid.data.retrofit

import com.example.submission_mlforandroid.data.response.ArticelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getArticel(
        @Query("q") q : String,
        @Query("category") category : String,
        @Query("language") language : String,
        @Query("apiKey") key : String
    ): Call<ArticelResponse>
}