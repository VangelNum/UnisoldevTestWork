package com.example.unisoldevtestwork.feature_favourite.data.repository

import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity
import com.example.unisoldevtestwork.feature_favourite.data.network.FavouriteDao
import com.example.unisoldevtestwork.feature_favourite.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val dao: FavouriteDao
) : FavouriteRepository {
    override fun getAllFavouritePhotos(): Flow<Resource<List<FavouriteEntity>>> = flow {
        emit(Resource.Loading())
        try {
            dao.getAllPhotos().collect {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun insertPhoto(photo: FavouriteEntity) {
        dao.insertPhoto(photo)
    }

    override suspend fun deletePhoto(photo: FavouriteEntity) {
        dao.deletePhoto(photo)
    }

    override suspend fun deleteAllPhotos() {
        dao.deleteAllPhotos()
    }
}