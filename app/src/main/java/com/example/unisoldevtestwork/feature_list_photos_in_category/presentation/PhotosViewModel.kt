package com.example.unisoldevtestwork.feature_list_photos_in_category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.Result
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.use_cases.GetPhotosByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotosByCategoryUseCase: GetPhotosByCategoryUseCase
) : ViewModel() {
    private val _photosState: MutableStateFlow<PagingData<Result>> = MutableStateFlow(PagingData.empty())
    val photosState = _photosState.asStateFlow()

    private var currentCategory: String? = null

    fun getPhotosByCategory(category: String) {
        if (category == currentCategory) {
            return
        }
        currentCategory = category
        val newPhotos = getPhotosByCategoryUseCase(category).cachedIn(viewModelScope)
        viewModelScope.launch {
            newPhotos.collectLatest { pagingData ->
                _photosState.value = pagingData
            }
        }
    }
}