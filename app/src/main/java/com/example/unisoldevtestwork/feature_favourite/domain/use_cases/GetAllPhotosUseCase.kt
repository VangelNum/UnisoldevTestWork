package com.example.unisoldevtestwork.feature_favourite.domain.use_cases

import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity
import com.example.unisoldevtestwork.feature_favourite.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow

class GetAllPhotosUseCase(
    private val repository: FavouriteRepository
) {
    operator fun invoke(): Flow<Resource<List<FavouriteEntity>>> {
        return repository.getAllFavouritePhotos()
    }
}