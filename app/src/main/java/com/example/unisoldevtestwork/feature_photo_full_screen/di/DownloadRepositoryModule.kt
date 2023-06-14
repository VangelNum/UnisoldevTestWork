package com.example.unisoldevtestwork.feature_photo_full_screen.di

import com.example.unisoldevtestwork.feature_photo_full_screen.data.repository.AndroidDownloaderImplRepository
import com.example.unisoldevtestwork.feature_photo_full_screen.domain.repository.DownloaderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DownloadRepositoryModule {
    @Binds
    @Singleton
    fun bindDownloaderRepository(
        downloadRepository: AndroidDownloaderImplRepository
    ) : DownloaderRepository
}