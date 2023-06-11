package com.example.unisoldevtestwork.feature_list_photos_in_category.data.repository

import com.example.unisoldevtestwork.feature_list_photos_in_category.data.api.ApiInterface
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.CategoryItemsDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.repository.ListPhotosCategoryRepository
import javax.inject.Inject

class ListPhotosCategoryRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : ListPhotosCategoryRepository {
    override suspend fun getPhotosByCategory(category: String): CategoryItemsDto {
        return api.getPhotosByCategory(query = category)
    }
}