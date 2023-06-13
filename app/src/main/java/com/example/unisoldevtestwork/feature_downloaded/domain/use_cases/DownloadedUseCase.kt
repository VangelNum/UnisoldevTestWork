package com.example.unisoldevtestwork.feature_downloaded.domain.use_cases

data class DownloadedUseCase(
    val getAllDownloadedPhotosUseCase: GetAllDownloadedPhotosUseCase,
    val insertDownloadedPhotoUseCase: InsertDownloadedPhotoUseCase,
    val deleteDownloadedPhotoUseCase: DeleteDownloadedPhotoUseCase
)