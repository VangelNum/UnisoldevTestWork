package com.example.unisoldevtestwork.feature_downloaded.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_photos_table")
data class DownloadedEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val urlPhoto: String
)
