package com.example.unisoldevtestwork.feature_settings.domain.repository

interface SettingsRepository {
    suspend fun putTheme(mode: String)
    suspend fun getTheme(): String
}