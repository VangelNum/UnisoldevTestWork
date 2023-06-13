package com.example.unisoldevtestwork.feature_settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.unisoldevtestwork.feature_settings.data.repository.SettingsRepositoryImpl
import com.example.unisoldevtestwork.feature_settings.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_SETTING_NAME = "user_settings"

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile(USER_SETTING_NAME) }
        )
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(dataStore: DataStore<Preferences>) : SettingsRepository {
        return SettingsRepositoryImpl(dataStore)
    }
}