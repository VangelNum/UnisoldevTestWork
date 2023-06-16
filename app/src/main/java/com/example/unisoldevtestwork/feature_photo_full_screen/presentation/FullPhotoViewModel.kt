package com.example.unisoldevtestwork.feature_photo_full_screen.presentation

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.feature_downloaded.data.model.DownloadedEntity
import com.example.unisoldevtestwork.feature_downloaded.domain.repository.DownloadedPhotoRepository
import com.example.unisoldevtestwork.feature_photo_full_screen.domain.repository.DownloaderRepository
import com.example.unisoldevtestwork.feature_photo_full_screen.domain.repository.SetWallpaperRepository
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.set_wallpapers_types.WallpaperSetType
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.text_helper.UiText
import com.example.unisoldevtestwork.feature_settings.presentation.NetworkType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullPhotoViewModel @Inject constructor(
    private val downloaderRepository: DownloaderRepository,
    private val downloadedRepository: DownloadedPhotoRepository,
    private val connectivityManager: ConnectivityManager,
    private val setWallpaperRepository: SetWallpaperRepository
) : ViewModel() {

    private val _fullPhotoDownloadChannel = Channel<UiText>()
    val fullPhotoDownloadChannel = _fullPhotoDownloadChannel.receiveAsFlow()

    private val _fullPhotoSetWallpaperChannel = Channel<UiText>()
    val fullPhotoSetWallpaperChannel = _fullPhotoSetWallpaperChannel.receiveAsFlow()

    fun downloadToggle(
        id: String?,
        photoUrl: String?,
        networkType: NetworkType
    ) {
        if (photoUrl != null && id != null) {
            when (networkType) {
                NetworkType.WIFI -> {
                    if (isWiFiAvailable()) {
                        viewModelScope.launch {
                            _fullPhotoDownloadChannel.send(UiText.StringResource(R.string.download_started))
                        }
                        downloadFileAndAddToDownloaded(networkType, photoUrl)
                        addFileToDownloaded(photoUrl, id)
                    } else {
                        viewModelScope.launch {
                            _fullPhotoDownloadChannel.send(UiText.StringResource(R.string.error_wifi_unavailable))
                        }
                    }
                }

                NetworkType.MOBILE_DATA -> {
                    if (isMobileDataAvailable()) {
                        viewModelScope.launch {
                            _fullPhotoDownloadChannel.send(UiText.StringResource(R.string.download_started))
                        }
                        downloadFileAndAddToDownloaded(networkType, photoUrl)
                        addFileToDownloaded(photoUrl, id)
                    } else {
                        viewModelScope.launch {
                            _fullPhotoDownloadChannel.send(UiText.StringResource(R.string.error_mobile_data_unavailable))
                        }
                    }
                }

                else -> {
                    if (isWiFiAvailable() || isMobileDataAvailable()) {
                        viewModelScope.launch {
                            _fullPhotoDownloadChannel.send(UiText.StringResource(R.string.download_started))
                        }
                        downloadFileAndAddToDownloaded(networkType, photoUrl)
                        addFileToDownloaded(photoUrl, id)
                    } else {
                        viewModelScope.launch {
                            _fullPhotoDownloadChannel.send(UiText.StringResource(R.string.error_network_unavailable))
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                _fullPhotoDownloadChannel.send(UiText.StringResource(R.string.error_download_image))
            }
        }
    }

    fun setWallpapers(setWallpaperType: WallpaperSetType, photoUrl: String?) {
        when (setWallpaperType) {
            WallpaperSetType.HOME_SCREEN -> {
                setWallpaperToHomeScreen(photoUrl)
            }

            WallpaperSetType.LOCK_SCREEN -> {
                setWallpaperToLockScreen(photoUrl)
            }

            WallpaperSetType.BOTH_SCREENS -> {
                setWallpaperToBothScreens(photoUrl)
            }
        }
    }


    private fun setWallpaperToHomeScreen(photoUrl: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = setWallpaperRepository.setWallpaperToHomeScreen(photoUrl)
            _fullPhotoSetWallpaperChannel.send(response)
        }
    }

    private fun setWallpaperToLockScreen(photoUrl: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = setWallpaperRepository.setWallpaperToLockScreen(photoUrl)
            _fullPhotoSetWallpaperChannel.send(response)
        }
    }

    private fun setWallpaperToBothScreens(photoUrl: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = setWallpaperRepository.setWallpaperToBothScreens(photoUrl)
            _fullPhotoSetWallpaperChannel.send(response)
        }
    }

    private fun downloadFileAndAddToDownloaded(
        networkType: NetworkType,
        photoUrl: String
    ) {
        downloaderRepository.downloadFile(networkType, photoUrl)
    }

    private fun addFileToDownloaded(
        photoUrl: String,
        id: String,
    ) {
        viewModelScope.launch {
            downloadedRepository.addDownloadedPhoto(DownloadedEntity(id, photoUrl))
        }
    }

    private fun isWiFiAvailable(): Boolean {
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }

    private fun isMobileDataAvailable(): Boolean {
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    }
}
