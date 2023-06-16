package com.example.unisoldevtestwork.feature_favourite.domain.use_cases

import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity
import com.example.unisoldevtestwork.feature_favourite.domain.repository.FavouriteRepository

class InsertPhotoUseCase(
    private val repository: FavouriteRepository
) {
    suspend operator fun invoke(photo: FavouriteEntity) {
        repository.insertPhoto(photo)
    }
}