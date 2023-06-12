package com.example.unisoldevtestwork.feature_favourite.domain.use_cases

data class FavouriteUseCase(
    val getAllPhotosUseCase: GetAllPhotosUseCase,
    val insertPhotoUseCase: InsertPhotoUseCase,
    val deletePhotoUseCase: DeletePhotoUseCase,
    val deleteAllPhotosUseCase: DeleteAllPhotosUseCase,
)