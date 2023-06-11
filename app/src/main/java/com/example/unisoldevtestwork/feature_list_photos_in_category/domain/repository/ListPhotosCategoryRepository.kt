package com.example.unisoldevtestwork.feature_list_photos_in_category.domain.repository

import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.CategoryItemsDto

interface ListPhotosCategoryRepository {
    suspend fun getPhotosByCategory(category: String): CategoryItemsDto
}