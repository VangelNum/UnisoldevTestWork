package com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.download

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.example.unisoldevtestwork.feature_settings.presentation.NetworkType

class AndroidDownloaderImpl(
    private val context: Context
): Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(networkType: NetworkType, url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("image.jpg")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg")
        when (networkType) {
            NetworkType.WIFI -> {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            }
            NetworkType.MOBILE_DATA -> {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
            }
            else -> {
                request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
                )
            }
        }
        return downloadManager.enqueue(request)
    }
}