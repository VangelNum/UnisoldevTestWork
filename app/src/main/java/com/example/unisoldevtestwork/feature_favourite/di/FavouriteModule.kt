package com.example.unisoldevtestwork.feature_favourite.di

import android.content.Context
import androidx.room.Room
import com.example.unisoldevtestwork.feature_favourite.data.network.FavouriteDao
import com.example.unisoldevtestwork.feature_favourite.data.network.FavouriteDatabase
import com.example.unisoldevtestwork.feature_favourite.data.repository.FavouriteRepositoryImpl
import com.example.unisoldevtestwork.feature_favourite.domain.repository.FavouriteRepository
import com.example.unisoldevtestwork.feature_favourite.domain.use_cases.DeleteAllPhotosUseCase
import com.example.unisoldevtestwork.feature_favourite.domain.use_cases.DeletePhotoUseCase
import com.example.unisoldevtestwork.feature_favourite.domain.use_cases.FavouriteUseCase
import com.example.unisoldevtestwork.feature_favourite.domain.use_cases.GetAllPhotosUseCase
import com.example.unisoldevtestwork.feature_favourite.domain.use_cases.InsertPhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavouriteModule {
    @Provides
    @Singleton
    fun provideFavouriteDatabase(@ApplicationContext context: Context): FavouriteDatabase {
        return Room.databaseBuilder(
            context,
            FavouriteDatabase::class.java,
            FavouriteDatabase.FAVOURITE_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(favouriteDatabase: FavouriteDatabase): FavouriteDao {
        return favouriteDatabase.getDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteRepository(dao: FavouriteDao) : FavouriteRepository {
        return FavouriteRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: FavouriteRepository): FavouriteUseCase {
        return FavouriteUseCase(
            getAllPhotosUseCase = GetAllPhotosUseCase(repository = repository),
            insertPhotoUseCase = InsertPhotoUseCase(repository = repository),
            deletePhotoUseCase = DeletePhotoUseCase(repository = repository),
            deleteAllPhotosUseCase = DeleteAllPhotosUseCase(repository = repository)
        )
    }
}