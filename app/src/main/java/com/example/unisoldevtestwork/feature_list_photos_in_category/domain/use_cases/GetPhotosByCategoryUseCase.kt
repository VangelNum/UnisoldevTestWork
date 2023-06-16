package com.example.unisoldevtestwork.feature_list_photos_in_category.domain.use_cases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.Result
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.repository.ListPhotosCategoryRepository
import com.example.unisoldevtestwork.feature_list_photos_in_category.presentation.PhotosPagingSource
import kotlinx.coroutines.flow.Flow

class GetPhotosByCategoryUseCase(
    private val repository: ListPhotosCategoryRepository
) {
    operator fun invoke(category: String): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { PhotosPagingSource(repository, category) }
        ).flow
    }
}