package com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.download

interface Downloader {
    fun downloadFile(url: String): Long
}