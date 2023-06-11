package com.example.unisoldevtestwork.feature_list_photos_in_category.di

import com.example.unisoldevtestwork.feature_list_photos_in_category.data.api.ApiInterface
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.repository.ListPhotosCategoryRepositoryImpl
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.repository.ListPhotosCategoryRepository
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.use_cases.GetPhotosByCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListPhotosByCategoryModule {

    @Provides
    @Singleton
    fun provideGetPhotosByCategoryRepository(api: ApiInterface): ListPhotosCategoryRepository {
        return ListPhotosCategoryRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideGetPhotosUseCase(repository: ListPhotosCategoryRepository): GetPhotosByCategoryUseCase {
        return GetPhotosByCategoryUseCase(repository)
    }
}