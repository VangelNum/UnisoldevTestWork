package com.example.unisoldevtestwork.feature_favourite.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity

@Database(entities = [FavouriteEntity::class], version = 1)
abstract class FavouriteDatabase: RoomDatabase() {
    abstract fun getDao(): FavouriteDao
    companion object {
        const val FAVOURITE_DATABASE_NAME = "favourite_database"
    }
}