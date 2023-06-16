package com.example.unisoldevtestwork.feature_favourite.domain.use_cases

import com.example.unisoldevtestwork.feature_favourite.domain.repository.FavouriteRepository

class DeleteAllPhotosUseCase(
    private val repository: FavouriteRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllPhotos()
    }
}