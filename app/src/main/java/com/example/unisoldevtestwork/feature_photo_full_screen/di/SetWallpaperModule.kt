package com.example.unisoldevtestwork.feature_photo_full_screen.di

import android.app.WallpaperManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SetWallpaperModule {

    @Singleton
    @Provides
    fun provideWallpaperManager(@ApplicationContext context: Context): WallpaperManager {
        return WallpaperManager.getInstance(context)
    }
}