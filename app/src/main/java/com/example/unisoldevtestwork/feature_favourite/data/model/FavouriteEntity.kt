package com.example.unisoldevtestwork.feature_favourite.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_photos_table")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val urlPhoto: String
)
