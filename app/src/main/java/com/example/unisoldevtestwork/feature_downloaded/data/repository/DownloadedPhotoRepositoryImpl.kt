package com.example.unisoldevtestwork.feature_downloaded.data.repository

import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_downloaded.data.model.DownloadedEntity
import com.example.unisoldevtestwork.feature_downloaded.data.network.DownloadedDao
import com.example.unisoldevtestwork.feature_downloaded.domain.repository.DownloadedPhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DownloadedPhotoRepositoryImpl @Inject constructor(
    private val dao: DownloadedDao
) : DownloadedPhotoRepository {
    override fun getDownloadedPhotos(): Flow<Resource<List<DownloadedEntity>>> = flow {
        try {
            emit(Resource.Loading())
            dao.getAllDownloadedPhotos().collect {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun addDownloadedPhoto(photo: DownloadedEntity) {
        dao.insertDownloadedPhoto(photo)
    }

    override suspend fun deleteDownloadedPhoto(photo: DownloadedEntity) {
        dao.deleteDownloadedPhoto(photo)
    }
}