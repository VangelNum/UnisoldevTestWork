package com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("blur_hash") val blurHash: String,
    val color: String,
    @SerializedName("created_at") val createdAt: String,
    val height: Int,
    val id: String,
    val likes: Int,
    val urls: Urls,
    val width: Int
)
