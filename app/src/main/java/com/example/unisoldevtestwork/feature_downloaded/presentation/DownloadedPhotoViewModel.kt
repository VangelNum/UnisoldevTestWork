package com.example.unisoldevtestwork.feature_downloaded.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_downloaded.data.model.DownloadedEntity
import com.example.unisoldevtestwork.feature_downloaded.domain.use_cases.DownloadedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadedPhotoViewModel @Inject constructor(
    private val downloadedUseCase: DownloadedUseCase
) : ViewModel() {
    private val _downloadPhotosState: MutableStateFlow<Resource<List<DownloadedEntity>>> = MutableStateFlow(Resource.Loading())
    val downloadPhotosState: StateFlow<Resource<List<DownloadedEntity>>> = _downloadPhotosState.asStateFlow()

    fun getDownloadedPhotos() {
        downloadedUseCase.getAllDownloadedPhotosUseCase().onEach {
            _downloadPhotosState.value = it
        }.launchIn(viewModelScope)
    }

    fun addToDownloadedList(photo: DownloadedEntity) {
        viewModelScope.launch {
            downloadedUseCase.insertDownloadedPhotoUseCase(photo)
        }
    }

    fun deleteFromDownloaded(photo: DownloadedEntity) {
        viewModelScope.launch {
            downloadedUseCase.deleteDownloadedPhotoUseCase(photo)
        }
    }
}