package com.example.unisoldevtestwork.feature_settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.unisoldevtestwork.feature_settings.domain.repository.SettingsRepository
import com.example.unisoldevtestwork.feature_settings.presentation.NetworkType
import com.example.unisoldevtestwork.feature_settings.presentation.QualityType
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeType
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

    override suspend fun setTheme(mode: ThemeType) {
        dataStore.edit { preferences ->
            preferences[CURRENT_MODE] = mode.name
        }
    }

    override suspend fun getTheme(): ThemeType {
        val preferences = dataStore.data.first()
        val themeValue = preferences[CURRENT_MODE] ?: ThemeType.SYSTEM_THEME.name
        return when (themeValue) {
            ThemeType.LIGHT_THEME.name -> ThemeType.LIGHT_THEME
            ThemeType.DARK_THEME.name -> ThemeType.DARK_THEME
            else -> ThemeType.SYSTEM_THEME
        }
    }

    override suspend fun setQualityOfImage(qualityOfImage: QualityType) {
        dataStore.edit { preferences ->
            preferences[CURRENT_QUALITY_OF_IMAGE] = qualityOfImage.name
        }
    }

    override suspend fun getQualityOfImage(): QualityType {
        val preferences = dataStore.data.first()
        val quality = preferences[CURRENT_QUALITY_OF_IMAGE] ?: QualityType.WITH_COMPRESSION_75.name
        return when (quality) {
            QualityType.WITH_COMPRESSION_75.name -> QualityType.WITH_COMPRESSION_75
            QualityType.SMALL_IMAGE_WITH_COMPRESSION_75.name -> QualityType.SMALL_IMAGE_WITH_COMPRESSION_75
            else -> {
                QualityType.WITHOUT_COMPRESSION
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