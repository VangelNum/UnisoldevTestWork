package com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto

import com.google.gson.annotations.SerializedName

data class CategoryItemsDto(
    val results: List<ResultDto>,
    val total: Int,
    @SerializedName("total_pages") val totalPages: Int
)