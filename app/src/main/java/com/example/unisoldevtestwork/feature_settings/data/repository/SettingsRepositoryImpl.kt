package com.example.unisoldevtestwork.feature_settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.unisoldevtestwork.feature_settings.domain.repository.SettingsRepository
import com.example.unisoldevtestwork.feature_settings.presentation.NetworkType
import com.example.unisoldevtestwork.feature_settings.presentation.QualityOption
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeOption
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    companion object {
        private val CURRENT_MODE = stringPreferencesKey("mode")
        private val CURRENT_QUALITY_OF_IMAGE = stringPreferencesKey("quality_images")
        private val CURRENT_NETWORK_TYPE = stringPreferencesKey("network_type")
    }

    override suspend fun setTheme(mode: ThemeOption) {
        dataStore.edit { preferences ->
            preferences[CURRENT_MODE] = mode.name
        }
    }

    override suspend fun getTheme(): ThemeOption {
        val preferences = dataStore.data.first()
        val themeValue = preferences[CURRENT_MODE] ?: ThemeOption.SYSTEM_THEME.name
        return when (themeValue) {
            ThemeOption.LIGHT_THEME.name -> ThemeOption.LIGHT_THEME
            ThemeOption.DARK_THEME.name -> ThemeOption.DARK_THEME
            else -> ThemeOption.SYSTEM_THEME
        }
    }

    override suspend fun setQualityOfImage(qualityOfImage: QualityOption) {
        dataStore.edit { preferences ->
            preferences[CURRENT_QUALITY_OF_IMAGE] = qualityOfImage.name
        }
    }

    override suspend fun getQualityOfImage(): QualityOption {
        val preferences = dataStore.data.first()
        val quality = preferences[CURRENT_QUALITY_OF_IMAGE] ?: QualityOption.WITH_COMPRESSION_75.name
        return when (quality) {
            QualityOption.WITH_COMPRESSION_75.name -> QualityOption.WITH_COMPRESSION_75
            QualityOption.SMALL_IMAGE_WITH_COMPRESSION_75.name -> QualityOption.SMALL_IMAGE_WITH_COMPRESSION_75
            else -> {
                QualityOption.WITHOUT_COMPRESSION
            }
        }
    }

    override suspend fun setNetworkType(networkType: NetworkType) {
        dataStore.edit { preferences ->
            preferences[CURRENT_NETWORK_TYPE] = networkType.name
        }
    }

    override suspend fun getNetworkType(): NetworkType {
        val preferences = dataStore.data.first()
        val networkTypeValue = preferences[CURRENT_NETWORK_TYPE] ?: NetworkType.DEFAULT.name
        return when (networkTypeValue) {
            NetworkType.WIFI.name -> NetworkType.WIFI
            NetworkType.MOBILE_DATA.name -> NetworkType.MOBILE_DATA
            else -> NetworkType.DEFAULT
        }
    }
}