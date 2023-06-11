package com.example.unisoldevtestwork.feature_list_photos_in_category.domain.use_cases

import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.CategoryItemsDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.repository.ListPhotosCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPhotosByCategoryUseCase @Inject constructor(
    private val repository: ListPhotosCategoryRepository
) {
    operator fun invoke(category: String): Flow<Resource<CategoryItemsDto>> = flow {
        try {
            val response = repository.getPhotosByCategory(category)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}