package com.example.unisoldevtestwork.feature_list_photos_in_category.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.core.presentation.composableTemplates.ShowErrorScreen
import com.example.unisoldevtestwork.core.presentation.composableTemplates.ShowGradientBelow
import com.example.unisoldevtestwork.core.presentation.composableTemplates.ShowLoadingScreen
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.CategoryItemsDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.LinksDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.LinksXDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.ProfileImageDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.ResultDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.UrlsDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.UserDto
import com.example.unisoldevtestwork.feature_settings.presentation.QualityOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPhotosInCategoryScreen(
    category: String?,
    photosState: Resource<CategoryItemsDto>,
    favouriteState: Resource<List<FavouriteEntity>>,
    onNavigateToBack: () -> Unit,
    onNavigateToWatchPhoto: (String, String) -> Unit,
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    qualityOfImages: QualityOption
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = category ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateToBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (photosState) {
            is Resource.Loading -> {
                ShowLoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is Resource.Error -> {
                ShowErrorScreen(
                    photosState.message,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is Resource.Success -> {
                ShowPhotosGrid(
                    innerPadding,
                    photosState.data?.results ?: emptyList(),
                    favouriteState.data ?: emptyList(),
                    onNavigateToWatchPhoto,
                    addToFavourite,
                    deleteFromFavourite,
                    qualityOfImages
                )
            }
        }
    }
}


@Composable
private fun ShowPhotosGrid(
    innerPadding: PaddingValues,
    photos: List<ResultDto>,
    favourites: List<FavouriteEntity>,
    onNavigateToWatchPhoto: (String, String) -> Unit,
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    qualityOfImages: QualityOption
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.padding(innerPadding)
    ) {
        items(photos) { photo ->
            ShowPhotoItem(
                photo,
                favourites,
                onNavigateToWatchPhoto,
                addToFavourite,
                deleteFromFavourite,
                qualityOfImages
            )
        }
    }
}

@Composable
private fun ShowPhotoItem(
    photo: ResultDto,
    favourites: List<FavouriteEntity>,
    onNavigateToWatchPhoto: (String, String) -> Unit,
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    qualityOfImages: QualityOption,
) {
    var photoQuality by remember {
        mutableStateOf(photo.urls.raw)
    }
    photoQuality = when (qualityOfImages) {
        QualityOption.WITHOUT_COMPRESSION -> {
            photo.urls.full
        }
        QualityOption.WITH_COMPRESSION_75 -> {
            photo.urls.regular
        }
        QualityOption.SMALL_IMAGE_WITH_COMPRESSION_75 -> {
            photo.urls.small
        }
    }
    Card(shape = MaterialTheme.shapes.large, modifier = Modifier.clickable {
        onNavigateToWatchPhoto(photo.id, photoQuality)
    }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            SubcomposeAsyncImage(
                model = photoQuality,
                contentScale = ContentScale.Crop,
                loading = {
                    ShowImageLoading()
                },
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            ShowGradientBelow()
            ShowFavouriteIcon(addToFavourite, deleteFromFavourite, photo, favourites)
        }
    }
}


@Composable
private fun ShowImageLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
private fun BoxScope.ShowFavouriteIcon(
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    photo: ResultDto,
    favourites: List<FavouriteEntity>
) {
    val isPhotoFavourite = isPhotoInFavourites(photo, favourites)
    val scale by animateFloatAsState(
        if (isPhotoFavourite) 1.2f else 1.0f,
        label = "scalePhotosList"
    )
    val iconColor by animateColorAsState(
        if (isPhotoFavourite) Color.Red else Color.White,
        label = "iconColorAnimation"
    )

    IconButton(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .scale(scale),
        onClick = {
            if (isPhotoFavourite) {
                deleteFromFavourite(
                    FavouriteEntity(
                        id = photo.id,
                        urlPhoto = photo.urls.regular
                    )
                )
            } else {
                addToFavourite(
                    FavouriteEntity(
                        id = photo.id,
                        urlPhoto = photo.urls.regular
                    )
                )
            }
        }
    ) {
        Icon(
            imageVector = if (isPhotoFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = stringResource(id = R.string.favourite),
            tint = iconColor
        )
    }
}

private fun isPhotoInFavourites(photo: ResultDto, favourites: List<FavouriteEntity>): Boolean {
    return favourites.any { favouriteEntity ->
        favouriteEntity.id == photo.id
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewListPhotosInCategoryScreen() {
    ListPhotosInCategoryScreen(
        category = null,
        onNavigateToBack = {},
        onNavigateToWatchPhoto = { id, url ->

        },
        photosState = Resource.Success(
            CategoryItemsDto(
                results = listOf(
                    ResultDto(
                        blurHash = "",
                        color = "",
                        createdAt = "01-01-2000",
                        currentUserCollections = listOf(""),
                        description = "",
                        height = 0,
                        id = "",
                        likedByUser = false,
                        likes = 0,
                        links = LinksDto(download = "", html = "", self = ""),
                        urls = UrlsDto(
                            "",
                            "",
                            "https://w-dog.ru/wallpapers/10/18/464728990985141/priroda-gory-kamni-les.jpg",
                            "",
                            ""
                        ),
                        user = UserDto(
                            "",
                            "",
                            "",
                            "",
                            LinksXDto("", "", "", ""),
                            "",
                            "",
                            ProfileImageDto("", "", ""),
                            "",
                            ""
                        ),
                        width = 0
                    ),
                    ResultDto(
                        blurHash = "",
                        color = "",
                        createdAt = "01-01-2000",
                        currentUserCollections = listOf(""),
                        description = "",
                        height = 0,
                        id = "",
                        likedByUser = false,
                        likes = 0,
                        links = LinksDto(download = "", html = "", self = ""),
                        urls = UrlsDto(
                            "",
                            "",
                            "https://img.newgrodno.by/wp-content/uploads/16165013908V1z7D_t20_YX6vKm.jpg",
                            "",
                            ""
                        ),
                        user = UserDto(
                            "",
                            "",
                            "",
                            "",
                            LinksXDto("", "", "", ""),
                            "",
                            "",
                            ProfileImageDto("", "", ""),
                            "",
                            ""
                        ),
                        width = 0
                    )
                ), total = 0, totalPages = 0
            )
        ),
        deleteFromFavourite = {},
        addToFavourite = {},
        favouriteState = Resource.Success(
            listOf(
                FavouriteEntity("0", "0"),
                FavouriteEntity("0", "0"),
                FavouriteEntity("0", "0")
            )
        ),
        qualityOfImages = QualityOption.WITHOUT_COMPRESSION
    )
}