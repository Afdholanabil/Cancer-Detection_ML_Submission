package com.example.submission_mlforandroid.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission_mlforandroid.data.database.CancerSaved
import com.example.submission_mlforandroid.data.database.CancerSavedDao
import com.example.submission_mlforandroid.data.database.CancerSavedRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CancerSavedRepository(application: Application) {
    private val mCancerSavedDao : CancerSavedDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = CancerSavedRoomDatabase.getDatabase(application)
        mCancerSavedDao = db.cancerSaveDao()
    }

    fun getAllCancerSaved():  LiveData<List<CancerSaved>> = mCancerSavedDao.getAllCancerSaved()

    fun getCancerSavedById(id : Int): LiveData<CancerSaved> = mCancerSavedDao.getCancerSavedById(id)

    fun insert(cancerSaved: CancerSaved) {
        executorService.execute { mCancerSavedDao.insert(cancerSaved) }
    }

    fun delete(cancerSaved: CancerSaved) {
        executorService.execute { mCancerSavedDao.deleteCancerSavedById(cancerSaved) }
    }
}