package com.example.unisoldevtestwork.feature_list_photos_in_category.domain.repository

import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.CategoryItems

interface ListPhotosCategoryRepository {
    suspend fun getPhotosByCategory(category: String, page: Int): CategoryItems
}