package com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.download

import com.example.unisoldevtestwork.feature_settings.presentation.NetworkType

interface Downloader {
    fun downloadFile(networkType: NetworkType, url: String): Long
}