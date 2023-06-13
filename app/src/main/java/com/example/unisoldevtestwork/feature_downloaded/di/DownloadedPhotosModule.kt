package com.example.unisoldevtestwork.feature_downloaded.di

import android.content.Context
import androidx.room.Room
import com.example.unisoldevtestwork.feature_downloaded.data.network.DownloadedDao
import com.example.unisoldevtestwork.feature_downloaded.data.network.DownloadedPhotosDatabase
import com.example.unisoldevtestwork.feature_downloaded.data.repository.DownloadedPhotoRepositoryImpl
import com.example.unisoldevtestwork.feature_downloaded.domain.repository.DownloadedPhotoRepository
import com.example.unisoldevtestwork.feature_downloaded.domain.use_cases.DeleteDownloadedPhotoUseCase
import com.example.unisoldevtestwork.feature_downloaded.domain.use_cases.DownloadedUseCase
import com.example.unisoldevtestwork.feature_downloaded.domain.use_cases.GetAllDownloadedPhotosUseCase
import com.example.unisoldevtestwork.feature_downloaded.domain.use_cases.InsertDownloadedPhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DownloadedPhotosModule {
    @Provides
    @Singleton
    fun provideDownloadedDatabase(@ApplicationContext context: Context): DownloadedPhotosDatabase {
        return Room.databaseBuilder(
            context,
            DownloadedPhotosDatabase::class.java,
            DownloadedPhotosDatabase.DOWNLOAD_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDownloadedDao(database: DownloadedPhotosDatabase) : DownloadedDao {
        return database.getDownloadDao()
    }

    @Singleton
    @Provides
    fun provideDownloadedRepository(dao: DownloadedDao) : DownloadedPhotoRepository {
        return DownloadedPhotoRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideDownloadedUseCase(repository: DownloadedPhotoRepository): DownloadedUseCase {
        return DownloadedUseCase(
            getAllDownloadedPhotosUseCase = GetAllDownloadedPhotosUseCase(repository),
            insertDownloadedPhotoUseCase = InsertDownloadedPhotoUseCase(repository),
            deleteDownloadedPhotoUseCase = DeleteDownloadedPhotoUseCase(repository)
        )
    }
}

