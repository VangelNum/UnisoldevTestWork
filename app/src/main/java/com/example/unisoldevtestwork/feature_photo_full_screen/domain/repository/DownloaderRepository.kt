package com.example.unisoldevtestwork.feature_photo_full_screen.domain.repository

import com.example.unisoldevtestwork.feature_settings.presentation.NetworkType

interface DownloaderRepository {
    fun downloadFile(networkType: NetworkType, url: String): Long
}