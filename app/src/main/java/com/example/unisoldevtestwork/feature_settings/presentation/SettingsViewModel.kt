package com.example.unisoldevtestwork.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unisoldevtestwork.feature_settings.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _settingsState = MutableStateFlow(
        SettingsState(
            themeMode = ThemeType.SYSTEM_THEME,
            qualityOfImage = QualityType.WITH_COMPRESSION_75,
            networkType = NetworkType.DEFAULT
        )
    )
    val settingsState = _settingsState.asStateFlow()

    init {
        getTheme()
        getQualityOfImage()
        getNetworkType()
    }

    private fun getQualityOfImage() {
        viewModelScope.launch {
            val response = settingsRepository.getQualityOfImage()
            _settingsState.value = _settingsState.value.copy(
                qualityOfImage = response
            )
        }
    }

    fun setQualityOfImage(qualityOfImage: QualityType) {
        viewModelScope.launch {
            settingsRepository.setQualityOfImage(qualityOfImage)
            _settingsState.value = _settingsState.value.copy(
                qualityOfImage = qualityOfImage
            )
        }
    }

    private fun getTheme() {
        viewModelScope.launch {
            val response = settingsRepository.getTheme()
            _settingsState.value = _settingsState.value.copy(
                themeMode = response
            )
        }
    }

    fun setTheme(mode: ThemeType) {
        viewModelScope.launch {
            settingsRepository.setTheme(mode)
            _settingsState.value = _settingsState.value.copy(
                themeMode = mode
            )
        }
    }

    private fun getNetworkType() {
        viewModelScope.launch {
            val response = settingsRepository.getNetworkType()
            _settingsState.value = _settingsState.value.copy(
                networkType = response
            )
        }
    }

    fun setNetworkType(networkType: NetworkType) {
        viewModelScope.launch {
            settingsRepository.setNetworkType(networkType)
            _settingsState.value = _settingsState.value.copy(
                networkType = networkType
            )
        }
    }
}

