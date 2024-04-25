package com.example.submission_mlforandroid.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CancerSaved::class], version = 1)
abstract class CancerSavedRoomDatabase: RoomDatabase() {

    abstract fun cancerSaveDao() : CancerSavedDao

    companion object {
        @Volatile
        private var INSTANCE : CancerSavedRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) : CancerSavedRoomDatabase {
            if (INSTANCE == null) {
                synchronized(CancerSavedRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, CancerSavedRoomDatabase::class.java, "cancer_saved_database")
                        .build()
                }
            }
            return INSTANCE as CancerSavedRoomDatabase
        }
    }
}