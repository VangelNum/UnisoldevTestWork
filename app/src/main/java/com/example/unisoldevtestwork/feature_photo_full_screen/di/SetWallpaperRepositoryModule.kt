package com.example.unisoldevtestwork.feature_photo_full_screen.di

import com.example.unisoldevtestwork.feature_photo_full_screen.data.repository.SetWallpaperRepositoryImpl
import com.example.unisoldevtestwork.feature_photo_full_screen.domain.repository.SetWallpaperRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SetWallpaperRepositoryModule {
    @Binds
    @Singleton
    fun bindsSetWallpaperRepository(
        setWallpaperRepositoryImpl: SetWallpaperRepositoryImpl
    ): SetWallpaperRepository
}