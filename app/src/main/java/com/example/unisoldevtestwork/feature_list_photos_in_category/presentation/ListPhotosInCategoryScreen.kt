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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.core.presentation.composableTemplates.ShowErrorScreen
import com.example.unisoldevtestwork.core.presentation.composableTemplates.ShowGradientBelow
import com.example.unisoldevtestwork.core.presentation.composableTemplates.ShowLoadingScreen
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.Result
import com.example.unisoldevtestwork.feature_list_photos_in_category.domain.data.Urls
import com.example.unisoldevtestwork.feature_settings.presentation.QualityType
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPhotosInCategoryScreen(
    category: String?,
    photosState: LazyPagingItems<Result>,
    favouriteState: Resource<List<FavouriteEntity>>,
    onNavigateToBack: () -> Unit,
    onNavigateToWatchPhoto: (String, String) -> Unit,
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    qualityOfImages: QualityType
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
        when (photosState.loadState.refresh) {
            is LoadState.Loading -> {
                ShowLoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is LoadState.Error -> {
                ShowErrorScreen(
                    photosState.loadState.refresh.toString(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            else -> {
                ShowPhotosGrid(
                    innerPadding = innerPadding,
                    photoState = photosState,
                    favourites = favouriteState.data ?: emptyList(),
                    onNavigateToWatchPhoto = onNavigateToWatchPhoto,
                    addToFavourite = addToFavourite,
                    deleteFromFavourite = deleteFromFavourite,
                    qualityOfImages = qualityOfImages,
                    appendState = photosState.loadState.append
                )
            }
        }
    }
}


@Composable
private fun ShowPhotosGrid(
    innerPadding: PaddingValues,
    photoState: LazyPagingItems<Result>,
    favourites: List<FavouriteEntity>,
    onNavigateToWatchPhoto: (String, String) -> Unit,
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    qualityOfImages: QualityType,
    appendState: LoadState
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.padding(innerPadding)
    ) {
        items(photoState.itemCount) { index ->
            val photo = photoState[index]
            if (photo != null) {
                ShowPhotoItem(
                    photo = photo,
                    favourites = favourites,
                    onNavigateToWatchPhoto = onNavigateToWatchPhoto,
                    addToFavourite = addToFavourite,
                    deleteFromFavourite = deleteFromFavourite,
                    qualityOfImages = qualityOfImages,
                )
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            when (appendState) {
                is LoadState.Loading -> {
                    Box() {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is LoadState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(text = stringResource(id = R.string.error_append))
                    }
                }

                is LoadState.NotLoading -> {
                    Unit
                }
            }
        }
    }
}

@Composable
private fun ShowPhotoItem(
    photo: Result,
    favourites: List<FavouriteEntity>,
    onNavigateToWatchPhoto: (String, String) -> Unit,
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    qualityOfImages: QualityType,
) {
    var photoQuality by remember {
        mutableStateOf(photo.urls.raw)
    }
    photoQuality = when (qualityOfImages) {
        QualityType.WITHOUT_COMPRESSION -> {
            photo.urls.full
        }

        QualityType.WITH_COMPRESSION_75 -> {
            photo.urls.regular
        }

        QualityType.SMALL_IMAGE_WITH_COMPRESSION_75 -> {
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
    photo: Result,
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

private fun isPhotoInFavourites(photo: Result, favourites: List<FavouriteEntity>): Boolean {
    return favourites.any { favouriteEntity ->
        favouriteEntity.id == photo.id
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewListPhotosInCategoryScreen() {
    val favouriteState = Resource.Success(
        listOf(
            FavouriteEntity("0", "0"),
            FavouriteEntity("0", "0"),
            FavouriteEntity("0", "0")
        )
    )
    val photosState = flowOf(
        PagingData.from(
            listOf(
                Result(
                    blurHash = "",
                    color = "",
                    createdAt = "01-01-2000",
                    height = 0,
                    id = "",
                    likes = 0,
                    urls = Urls(
                        "",
                        "",
                        "https://w-dog.ru/wallpapers/10/18/464728990985141/priroda-gory-kamni-les.jpg",
                        "",
                        ""
                    ),
                    width = 0
                ),
            )
        )
    ).collectAsLazyPagingItems()

    ListPhotosInCategoryScreen(
        category = null,
        onNavigateToBack = {},
        onNavigateToWatchPhoto = { id, url -> },
        photosState = photosState,
        deleteFromFavourite = {},
        addToFavourite = {},
        favouriteState = favouriteState,
        qualityOfImages = QualityType.WITHOUT_COMPRESSION
    )
}