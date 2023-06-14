package com.example.unisoldevtestwork.feature_settings.domain.repository

import com.example.unisoldevtestwork.feature_settings.presentation.NetworkType
import com.example.unisoldevtestwork.feature_settings.presentation.QualityOption
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeOption

interface SettingsRepository {
    suspend fun setTheme(mode: ThemeOption)
    suspend fun getTheme(): ThemeOption
    suspend fun setQualityOfImage(qualityOfImage: QualityOption)
    suspend fun getQualityOfImage(): QualityOption
    suspend fun setNetworkType(networkType: NetworkType)
    suspend fun getNetworkType(): NetworkType
}