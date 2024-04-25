package com.example.submission_mlforandroid.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.submission_mlforandroid.data.repository.CancerSavedRepository

@Dao
interface CancerSavedDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cancerSaved: CancerSaved)

    @Update
    fun update(cancerSaved: CancerSaved)

    @Delete
    fun deleteCancerSavedById(cancerSaved: CancerSaved)

    @Query("SELECT * from cancersaved ORDER BY id = id ")
    fun getAllCancerSaved(): LiveData<List<CancerSaved>>

    @Query("SELECT * from cancersaved where id = :id")
    fun getCancerSavedById(id : Int): LiveData<CancerSaved>


}