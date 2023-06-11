package com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto

import com.google.gson.annotations.SerializedName

data class ResultDto(
    @SerializedName("blur_hash") val blurHash: String,
    val color: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("current_user_collections") val currentUserCollections: List<Any>,
    val description: String,
    val height: Int,
    val id: String,
    @SerializedName("liked_by_user") val likedByUser: Boolean,
    val likes: Int,
    val links: LinksDto,
    val urls: UrlsDto,
    val user: UserDto,
    val width: Int
)