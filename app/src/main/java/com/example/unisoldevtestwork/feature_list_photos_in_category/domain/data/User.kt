package com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("first_name") val firstName: String,
    val id: String,
    @SerializedName("instagram_username") val instagramUsername: String,
    @SerializedName("last_name") val lastName: String,
    val links: LinksX,
    val name: String,
    @SerializedName("portfolio_url") val portfolioUrl: String,
    @SerializedName("profile_image") val profileImage: ProfileImage,
    @SerializedName("twitter_username") val twitterUsername: String,
    val username: String
)