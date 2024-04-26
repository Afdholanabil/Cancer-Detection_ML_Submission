package com.example.submission_mlforandroid.view.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_mlforandroid.data.database.CancerSaved
import com.example.submission_mlforandroid.data.repository.CancerSavedRepository

class ResultViewModel(application: Application): ViewModel() {

    val application :Application? = null

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _snackBar = MutableLiveData<String>()
    val snackbar : LiveData<String> = _snackBar

    private val mCancerSavedRepository: CancerSavedRepository = CancerSavedRepository(application)

    fun insert(pimageUri: Uri, presultDetection: String, pconfidenceScore: Float) {
        val insertData = CancerSaved(imageUrl= pimageUri.toString(), resultDetection = presultDetection, confidenceScore = pconfidenceScore)
        mCancerSavedRepository.insert(insertData)
    }

    fun delete(id : Int) {
        val cancerSaved = CancerSaved(id ?: 0)
        mCancerSavedRepository.delete(cancerSaved)
    }
}