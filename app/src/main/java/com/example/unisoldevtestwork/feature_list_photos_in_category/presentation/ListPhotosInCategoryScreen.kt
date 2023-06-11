package com.example.unisoldevtestwork.feature_list_photos_in_category.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.LinksDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.LinksXDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.ProfileImageDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.ResultDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.CategoryItemsDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.UrlsDto
import com.example.unisoldevtestwork.feature_list_photos_in_category.data.dto.UserDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPhotosInCategoryScreen(
    category: String?,
    photosState: Resource<CategoryItemsDto>,
    onNavigateToBack: () -> Unit,
    onNavigateToWatchPhoto: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text =  category ?: "")
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
        },
        content = {
            when (photosState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = photosState.message ?: "Error")
                    }
                }

                is Resource.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(128.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.padding(it)
                    ) {
                        items(photosState.data.results) { photos ->
                            Card(shape = MaterialTheme.shapes.large, modifier = Modifier.clickable {
                                onNavigateToWatchPhoto(photos.urls.regular)
                            }) {
                                SubcomposeAsyncImage(
                                    model = photos.urls.regular,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    contentScale = ContentScale.Crop,
                                    loading = {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    },
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewListPhotosInCategoryScreen() {
    ListPhotosInCategoryScreen(
        category = null,
        onNavigateToBack = {},
        onNavigateToWatchPhoto = {},
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
                        likedByUser  = false,
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
        )
    )
}