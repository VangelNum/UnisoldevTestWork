package com.example.unisoldevtestwork.feature_list_photos_in_category.data.api

import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.CategoryItemsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/search/photos/")
    suspend fun getPhotosByCategory(
        @Query("query") query: String,
    ): CategoryItemsDto
}