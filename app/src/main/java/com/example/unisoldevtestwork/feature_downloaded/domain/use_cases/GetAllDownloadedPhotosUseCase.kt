package com.example.unisoldevtestwork.feature_downloaded.domain.use_cases

import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_downloaded.data.model.DownloadedEntity
import com.example.unisoldevtestwork.feature_downloaded.domain.repository.DownloadedPhotoRepository
import kotlinx.coroutines.flow.Flow

class GetAllDownloadedPhotosUseCase(
    private val repository: DownloadedPhotoRepository
) {
    operator fun invoke() : Flow<Resource<List<DownloadedEntity>>> {
        return repository.getDownloadedPhotos()
    }
}