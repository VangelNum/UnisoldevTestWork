package com.example.unisoldevtestwork.feature_list_photos_in_category.data.repository

import com.example.unisoldevtestwork.feature_list_photos_in_category.data.api.ApiInterface
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.mappers.toCategoryItems
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.CategoryItems
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.repository.ListPhotosCategoryRepository
import javax.inject.Inject

class ListPhotosCategoryRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : ListPhotosCategoryRepository {
    override suspend fun getPhotosByCategory(category: String, page: Int): CategoryItems {
        return api.getPhotosByCategory(query = category, page = page).toCategoryItems()
    }
}