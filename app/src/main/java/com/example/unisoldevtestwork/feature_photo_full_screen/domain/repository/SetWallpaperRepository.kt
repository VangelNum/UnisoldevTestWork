package com.example.unisoldevtestwork.feature_photo_full_screen.domain.repository
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.text_helper.UiText

interface SetWallpaperRepository {
    suspend fun setWallpaperToHomeScreen(photoUrl: String?): UiText
    suspend fun setWallpaperToLockScreen(photoUrl: String?): UiText
    suspend fun setWallpaperToBothScreens(photoUrl: String?): UiText
}