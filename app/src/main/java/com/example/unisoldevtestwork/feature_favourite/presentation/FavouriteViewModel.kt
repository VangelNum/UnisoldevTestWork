package com.example.unisoldevtestwork.feature_favourite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity
import com.example.unisoldevtestwork.feature_favourite.domain.use_cases.FavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteUseCase: FavouriteUseCase
) : ViewModel() {
    private val _favouriteState: MutableStateFlow<Resource<List<FavouriteEntity>>> = MutableStateFlow(Resource.Loading())
    val favouriteState = _favouriteState.asStateFlow()

    init {
        getAllFavouritePhotos()
    }

    private fun getAllFavouritePhotos() {
        favouriteUseCase.getAllPhotosUseCase().onEach { response ->
            _favouriteState.value = response
        }.launchIn(viewModelScope)
    }

    fun insertPhoto(photo: FavouriteEntity) {
        viewModelScope.launch {
            favouriteUseCase.insertPhotoUseCase(photo)
        }
    }

    fun deletePhoto(photo: FavouriteEntity) {
        viewModelScope.launch {
            favouriteUseCase.deletePhotoUseCase(photo)
        }
    }

    fun deleteAllPhotos() {
        viewModelScope.launch {
            favouriteUseCase.deleteAllPhotosUseCase()
        }
    }
}