package com.example.unisoldevtestwork.feature_list_photos_in_category.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.Result
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.repository.ListPhotosCategoryRepository

class PhotosPagingSource(
    private val repository: ListPhotosCategoryRepository,
    private val category: String
) : PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1
        return try {
            val response = repository.getPhotosByCategory(category, page)
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (page < response.totalPages) page + 1 else null
            LoadResult.Page(
                data = response.results,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}