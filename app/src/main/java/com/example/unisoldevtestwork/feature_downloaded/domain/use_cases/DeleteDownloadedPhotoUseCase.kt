package com.example.unisoldevtestwork.feature_downloaded.domain.use_cases

import com.example.unisoldevtestwork.feature_downloaded.data.model.DownloadedEntity
import com.example.unisoldevtestwork.feature_downloaded.domain.repository.DownloadedPhotoRepository

class DeleteDownloadedPhotoUseCase(
    private val repository: DownloadedPhotoRepository
) {
    suspend operator fun invoke(photo: DownloadedEntity) {
        return repository.deleteDownloadedPhoto(photo)
    }
}