package com.example.submission_mlforandroid.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class CancerSaved (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0,

    @ColumnInfo(name = "imageUri")
    var imageUrl: String? = "",

    @ColumnInfo(name = "resultDetection")
    var resultDetection: String? = null,

    @ColumnInfo(name = "confidenceScore")
    var confidenceScore: Float? = null
) : Parcelable


