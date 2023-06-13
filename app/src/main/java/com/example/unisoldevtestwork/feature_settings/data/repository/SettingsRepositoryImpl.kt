package com.example.unisoldevtestwork.feature_settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.unisoldevtestwork.feature_settings.domain.repository.SettingsRepository
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeConstants.SYSTEM_THEME
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    companion object {
        private val CURRENT_MODE = stringPreferencesKey("mode")
    }

    override suspend fun putTheme(mode: String) {
        dataStore.edit { pref ->
            pref[CURRENT_MODE] = mode
        }
    }

    override suspend fun getTheme(): String {
        val preferences = dataStore.data.first()
        return preferences[CURRENT_MODE]?: SYSTEM_THEME
    }
}