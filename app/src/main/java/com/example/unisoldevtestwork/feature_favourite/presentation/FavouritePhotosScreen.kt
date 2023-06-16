package com.example.unisoldevtestwork.feature_favourite.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.core.presentation.AppNavigationBar
import com.example.unisoldevtestwork.core.presentation.AppTopBar
import com.example.unisoldevtestwork.core.presentation.composableTemplates.ShowErrorScreen
import com.example.unisoldevtestwork.core.presentation.composableTemplates.ShowGradientBelow
import com.example.unisoldevtestwork.core.presentation.composableTemplates.ShowLoadingScreen
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouritePhotosScreen(
    navController: NavHostController,
    favouriteState: Resource<List<FavouriteEntity>>,
    onNavigateToWatchPhoto: (String, String) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    onNavigationButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(onNavigationButtonClick)
        },
        bottomBar = {
            AppNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        when (favouriteState) {
            is Resource.Loading -> {
                ShowLoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is Resource.Error -> {
                ShowErrorScreen(
                    errorMessage = favouriteState.message,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is Resource.Success -> {
                if (favouriteState.data.isNullOrEmpty()) {
                    FavouritePhotosIsEmpty(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(128.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(favouriteState.data, key = { item ->
                            item.id
                        }
                        ) { photos ->
                            Card(shape = MaterialTheme.shapes.large, modifier = Modifier
                                .animateItemPlacement()
                                .clickable {
                                    onNavigateToWatchPhoto(photos.id, photos.urlPhoto)
                                }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                ) {
                                    SubcomposeAsyncImage(
                                        model = photos.urlPhoto,
                                        modifier = Modifier.fillMaxSize(),
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
                                    ShowGradientBelow()
                                    IconButton(
                                        modifier = Modifier.align(Alignment.BottomEnd),
                                        onClick = {
                                            deleteFromFavourite(
                                                FavouriteEntity(
                                                    photos.id,
                                                    photos.urlPhoto
                                                )
                                            )
                                        }) {
                                        Icon(
                                            imageVector = Icons.Filled.Close,
                                            contentDescription = stringResource(
                                                id = R.string.favourite
                                            ),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun FavouritePhotosIsEmpty(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(text = stringResource(id = R.string.empty_favourite_list))
    }
}

@Composable
@Preview(name = "error loading favourite", showSystemUi = true, showBackground = true)
fun PreviewErrorPhotosState() {
    val navController = rememberNavController()
    FavouritePhotosScreen(
        navController = navController,
        favouriteState = Resource.Error("ERROR MESSAGE"),
        onNavigateToWatchPhoto = { id, url ->

        },
        deleteFromFavourite = {}
    ) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFavouritePhotosScreen() {
    val navController = rememberNavController()
    FavouritePhotosScreen(
        navController = navController,
        favouriteState = Resource.Success(
            listOf(
                FavouriteEntity(
                    "0",
                    "https://images.unsplash.com/photo-1563089145-599997674d42?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0MTQzOTR8MHwxfHNlYXJjaHwzfHxBYnN0cmFjdHxlbnwwfHx8fDE2ODY1NjQ0NTV8MA&ixlib=rb-4.0.3&q=80&w=1080"
                ),
                FavouriteEntity(
                    "1",
                    "https://images.unsplash.com/photo-1567095761054-7a02e69e5c43?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0MTQzOTR8MHwxfHNlYXJjaHw0fHxBYnN0cmFjdHxlbnwwfHx8fDE2ODY1NjQ0NTV8MA&ixlib=rb-4.0.3&q=80&w=1080"
                ),
                FavouriteEntity(
                    "2",
                    "https://images.unsplash.com/photo-1567095761054-7a02e69e5c43?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0MTQzOTR8MHwxfHNlYXJjaHw0fHxBYnN0cmFjdHxlbnwwfHx8fDE2ODY1NjQ0NTV8MA&ixlib=rb-4.0.3&q=80&w=1080"
                ),
                FavouriteEntity(
                    "3",
                    "https://images.unsplash.com/photo-1567095761054-7a02e69e5c43?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0MTQzOTR8MHwxfHNlYXJjaHw0fHxBYnN0cmFjdHxlbnwwfHx8fDE2ODY1NjQ0NTV8MA&ixlib=rb-4.0.3&q=80&w=1080"
                ),
            )
        ),
        onNavigateToWatchPhoto = { id, url ->

        },
        deleteFromFavourite = { },
        onNavigationButtonClick = {}
    )
}