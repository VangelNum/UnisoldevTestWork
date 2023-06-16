package com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data

import com.google.gson.annotations.SerializedName

data class CategoryItems(
    val results: List<Result>,
    val total: Int,
    @SerializedName("total_pages") val totalPages: Int
)
