package com.example.unisoldevtestwork.feature_settings.presentation

data class SettingsState(
    val themeMode: ThemeType,
    val qualityOfImage: QualityType,
    val networkType: NetworkType
)