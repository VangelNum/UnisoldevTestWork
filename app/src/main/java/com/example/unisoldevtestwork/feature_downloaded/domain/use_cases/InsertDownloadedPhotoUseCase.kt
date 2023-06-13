package com.example.unisoldevtestwork.feature_downloaded.domain.use_cases

import com.example.unisoldevtestwork.feature_downloaded.data.model.DownloadedEntity
import com.example.unisoldevtestwork.feature_downloaded.domain.repository.DownloadedPhotoRepository
import javax.inject.Inject

class InsertDownloadedPhotoUseCase @Inject constructor(
    private val repository: DownloadedPhotoRepository
) {
    suspend operator fun invoke(photo: DownloadedEntity) {
        repository.addDownloadedPhoto(photo = photo)
    }
}