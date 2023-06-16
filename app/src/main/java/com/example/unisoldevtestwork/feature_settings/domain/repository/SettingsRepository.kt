package com.example.unisoldevtestwork.feature_settings.domain.repository

import com.example.unisoldevtestwork.feature_settings.presentation.NetworkType
import com.example.unisoldevtestwork.feature_settings.presentation.QualityType
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeType

interface SettingsRepository {
    suspend fun setTheme(mode: ThemeType)
    suspend fun getTheme(): ThemeType
    suspend fun setQualityOfImage(qualityOfImage: QualityType)
    suspend fun getQualityOfImage(): QualityType
    suspend fun setNetworkType(networkType: NetworkType)
    suspend fun getNetworkType(): NetworkType
}