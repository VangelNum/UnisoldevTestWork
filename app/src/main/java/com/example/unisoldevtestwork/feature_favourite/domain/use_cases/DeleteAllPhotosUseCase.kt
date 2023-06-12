package com.example.unisoldevtestwork.feature_favourite.domain.use_cases

import com.example.unisoldevtestwork.feature_favourite.domain.repository.FavouriteRepository
import javax.inject.Inject

class DeleteAllPhotosUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllPhotos()
    }
}