package com.example.unisoldevtestwork.feature_favourite.data.network

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite_photos_table")
    fun getAllPhotos(): Flow<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: FavouriteEntity)

    @Delete
    suspend fun deletePhoto(photo: FavouriteEntity)

    @Query("DELETE FROM favourite_photos_table")
    suspend fun deleteAllPhotos()
}