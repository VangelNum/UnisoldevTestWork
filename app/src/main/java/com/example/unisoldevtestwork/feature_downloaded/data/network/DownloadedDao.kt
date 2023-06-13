package com.example.unisoldevtestwork.feature_downloaded.data.network

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unisoldevtestwork.feature_downloaded.data.model.DownloadedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedDao {
    @Query("SELECT * FROM downloaded_photos_table")
    fun getAllDownloadedPhotos() : Flow<List<DownloadedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownloadedPhoto(photo: DownloadedEntity)

    @Delete
    suspend fun deleteDownloadedPhoto(photo: DownloadedEntity)
}