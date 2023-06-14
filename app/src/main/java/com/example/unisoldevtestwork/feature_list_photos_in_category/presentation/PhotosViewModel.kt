package com.example.unisoldevtestwork.feature_list_photos_in_category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.CategoryItemsDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.use_cases.GetPhotosByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotosByCategoryUseCase: GetPhotosByCategoryUseCase
) : ViewModel() {
    private val _photosState: MutableStateFlow<Resource<CategoryItemsDto>> = MutableStateFlow(Resource.Loading())
    val photosState = _photosState.asStateFlow()


    private var previousCategory: String? = null
    fun getPhotosByCategory(category: String) {
        if (category == previousCategory) {
            return
        }
        previousCategory = category

        getPhotosByCategoryUseCase(category).onEach { response ->
            _photosState.value = response
        }.launchIn(viewModelScope)
    }
}