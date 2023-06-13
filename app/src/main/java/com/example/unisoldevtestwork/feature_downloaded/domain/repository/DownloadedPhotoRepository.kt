package com.example.unisoldevtestwork.feature_downloaded.domain.repository

import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_downloaded.data.model.DownloadedEntity
import kotlinx.coroutines.flow.Flow

interface DownloadedPhotoRepository {
    fun getDownloadedPhotos() : Flow<Resource<List<DownloadedEntity>>>
    suspend fun addDownloadedPhoto(photo: DownloadedEntity)
    suspend fun deleteDownloadedPhoto(photo: DownloadedEntity)
}