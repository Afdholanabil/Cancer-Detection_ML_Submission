package com.example.submission_mlforandroid.view.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_mlforandroid.data.database.CancerSaved
import com.example.submission_mlforandroid.data.repository.CancerSavedRepository

class CancerSavedViewModel(application: Application) : ViewModel() {

    private val mSavedCancerRepository :CancerSavedRepository = CancerSavedRepository(application)

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _listSaved = MutableLiveData<List<CancerSaved>>()
    val listSaved : LiveData<List<CancerSaved>> = _listSaved

    private val _snackBar = MutableLiveData<String>()
    val snackBar : LiveData<String> = _snackBar


    fun getAllResultSaved() {
        _loading.value= true
        mSavedCancerRepository.getAllCancerSaved().observeForever{ list ->
            _listSaved.value = list
            _loading.value = false
            _snackBar.value = "Berhasil menampilkan hasil tersimpan !"

        }
    }
}