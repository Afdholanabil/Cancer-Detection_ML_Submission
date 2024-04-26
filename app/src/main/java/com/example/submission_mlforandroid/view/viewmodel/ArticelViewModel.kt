package com.example.submission_mlforandroid.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_mlforandroid.BuildConfig
import com.example.submission_mlforandroid.data.response.ArticelResponse
import com.example.submission_mlforandroid.data.response.ArticlesItem
import com.example.submission_mlforandroid.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArticelViewModel: ViewModel() {
    private val _listArticel = MutableLiveData<List<ArticlesItem>>()
    val listArticel : LiveData<List<ArticlesItem>> = _listArticel

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    init {
        getListArticel()
    }
    private fun getListArticel() {
        _loading.value = true

        val client = ApiConfig.getApiService().getArticel(q, category, languageEn, apiKey)
        client.enqueue(object : Callback<ArticelResponse> {
            override fun onResponse(
                call: Call<ArticelResponse>,
                response: Response<ArticelResponse>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    _listArticel.value = response.body()?.articles
                    Log.d(TAG,"onresponse : ${response.message()} with data : ${response.body()} header : ${response.headers()}")
                } else {
                    Log.d(TAG, "onfailure-else : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticelResponse>, t: Throwable) {
                Log.d(TAG,"Onfailure: ${t.message.toString()}")
                _loading.value = false
            }

        })

    }

    companion object {
        private const val TAG = "Articel ViewModel"
        private const val q = "cancer"
        private const val category = "health"
        private const val languageEn = "en"
        private const val apiKey = BuildConfig.API_KEY
    }
}