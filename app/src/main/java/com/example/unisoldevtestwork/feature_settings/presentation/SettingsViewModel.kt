package com.example.unisoldevtestwork.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unisoldevtestwork.feature_settings.domain.repository.SettingsRepository
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeConstants.SYSTEM_THEME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsState(themeMode = SYSTEM_THEME))
    val settingsState = _settingsState.asStateFlow()

    init {
        getTheme()
    }

    private fun getTheme() {
        viewModelScope.launch {
            val response = settingsRepository.getTheme()
            _settingsState.value = _settingsState.value.copy(
                themeMode = response
            )
        }
    }

    fun setTheme(mode: String) {
        viewModelScope.launch {
            settingsRepository.putTheme(mode)
            _settingsState.value = _settingsState.value.copy(
                themeMode = mode
            )
        }
    }
}

data class SettingsState(
    var themeMode: String
)