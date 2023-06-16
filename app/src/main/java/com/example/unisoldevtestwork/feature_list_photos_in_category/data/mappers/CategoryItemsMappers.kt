package com.example.unisoldevtestwork.feature_list_photos_in_category.data.mappers

import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.CategoryItemsDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.LinksDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.LinksXDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.ProfileImageDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.ResultDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.UrlsDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.UserDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.CategoryItems
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.Links
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.LinksX
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.ProfileImage
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.Result
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.Urls
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.User

fun CategoryItemsDto.toCategoryItems(): CategoryItems {
    return CategoryItems(
        results = this.results.map { it.toResult() },
        total = this.total,
        totalPages = this.totalPages
    )
}

fun ResultDto.toResult(): Result {
    return Result(
        blurHash = this.blurHash,
        color = this.color,
        createdAt = this.createdAt,
        height = this.height,
        id = this.id,
        likes = this.likes,
        urls = this.urls.toUrls(),
        width = this.width
    )
}

fun LinksDto.toLinks(): Links {
    return Links(
        download = this.download,
        html = this.html,
        self = this.self
    )
}

fun LinksXDto.toLinksX(): LinksX {
    return LinksX(
        html = this.html,
        likes = this.likes,
        photos = this.photos,
        self = this.self
    )
}

fun ProfileImageDto.toProfileImage(): ProfileImage {
    return ProfileImage(
        large = this.large,
        medium = this.medium,
        small = this.small
    )
}

fun UrlsDto.toUrls(): Urls {
    return Urls(
        full = this.full,
        raw = this.raw,
        regular = this.regular,
        small = this.small,
        thumb = this.thumb
    )
}

fun UserDto.toUser(): User {
    return User(
        firstName = this.firstName,
        id = this.id,
        instagramUsername = this.instagramUsername,
        lastName = this.lastName,
        links = this.links.toLinksX(),
        name = this.name,
        portfolioUrl = this.portfolioUrl,
        profileImage = this.profileImage.toProfileImage(),
        twitterUsername = this.twitterUsername,
        username = this.username
    )
}