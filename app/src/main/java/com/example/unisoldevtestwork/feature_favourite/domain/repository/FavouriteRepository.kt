package com.example.unisoldevtestwork.feature_favourite.domain.repository

import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    fun getAllFavouritePhotos() : Flow<Resource<List<FavouriteEntity>>>
    suspend fun insertPhoto(photo: FavouriteEntity)
    suspend fun deletePhoto(photo: FavouriteEntity)
    suspend fun deleteAllPhotos()
}