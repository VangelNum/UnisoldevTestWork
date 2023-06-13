package com.example.unisoldevtestwork.feature_downloaded.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unisoldevtestwork.feature_downloaded.data.model.DownloadedEntity

@Database(entities = [DownloadedEntity::class], version = 1)
abstract class DownloadedPhotosDatabase: RoomDatabase() {
    abstract fun getDownloadDao(): DownloadedDao
    companion object {
        const val DOWNLOAD_DATABASE_NAME = "DOWNLOAD_DATABASE"
    }
}