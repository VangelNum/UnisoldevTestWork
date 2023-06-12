package com.example.unisoldevtestwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.unisoldevtestwork.core.presentation.PhotosViewModel
import com.example.unisoldevtestwork.core.presentation.Screens
import com.example.unisoldevtestwork.feature_category.presentation.CategoryPhotosScreen
import com.example.unisoldevtestwork.feature_favourite.presentation.FavouritePhotosScreen
import com.example.unisoldevtestwork.feature_favourite.presentation.FavouriteViewModel
import com.example.unisoldevtestwork.feature_list_photos_in_category.presentation.ListPhotosInCategoryScreen
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.FullPhotoScreen
import com.example.unisoldevtestwork.ui.theme.UnisoldevTestWorkTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnisoldevTestWorkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val navController = rememberAnimatedNavController()
                    val photosViewModel = hiltViewModel<PhotosViewModel>()
                    val favouriteViewModel = hiltViewModel<FavouriteViewModel>()
                    val favouriteState = favouriteViewModel.favouriteState.collectAsStateWithLifecycle().value
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = { }
                    ) {
                        Scaffold { innerPadding ->
                            AnimatedNavHost(
                                navController = navController,
                                startDestination = Screens.CategoryPhotos.route,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable(Screens.CategoryPhotos.route) {
                                    CategoryPhotosScreen(
                                        onNavigateToSelectableCategory = {
                                            navController.navigate(Screens.ListPhotosInCategory.route + "/$it")
                                        },
                                        updateCategory = { category ->
                                            photosViewModel.getPhotosByCategory(category)
                                        },
                                        navController = navController
                                    )
                                }
                                composable(Screens.ListPhotosInCategory.route + "/{category}",
                                    arguments = listOf(
                                        navArgument("category") {
                                            type = NavType.StringType
                                            nullable = false
                                            defaultValue = ""
                                        }
                                    )
                                ) { entry ->
                                    val photosState = photosViewModel.photosState.collectAsStateWithLifecycle().value
                                    val category = entry.arguments?.getString("category")
                                    ListPhotosInCategoryScreen(
                                        category = category,
                                        photosState = photosState,
                                        favouriteState = favouriteState,
                                        onNavigateToBack = {
                                            navController.popBackStack()
                                        },
                                        onNavigateToWatchPhoto = { id, photoUrl ->
                                            val encodedUrl = URLEncoder.encode(
                                                photoUrl,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate(Screens.FullScreenPhoto.route + "/$encodedUrl/$id")
                                        },
                                        addToFavourite = { photo ->
                                            favouriteViewModel.insertPhoto(photo)
                                        },
                                        deleteFromFavourite = { photo ->
                                            favouriteViewModel.deletePhoto(photo)
                                        }
                                    )
                                }
                                composable(Screens.FullScreenPhoto.route + "/{photoUrl}/{id}",
                                    arguments = listOf(
                                        navArgument("photoUrl") {
                                            type = NavType.StringType
                                            nullable = false
                                            defaultValue = ""
                                        },
                                        navArgument("id") {
                                            type = NavType.StringType
                                            nullable = false
                                            defaultValue = ""
                                        }
                                    )) { entry ->
                                    val photoUrl = entry.arguments?.getString("photoUrl")
                                    val id = entry.arguments?.getString("id")
                                    FullPhotoScreen(id = id, photoUrl = photoUrl, onNavigateBack = {
                                        navController.popBackStack()
                                    }, favouritePhotoState = favouriteState, addToFavourite = {
                                        favouriteViewModel.insertPhoto(it)
                                    }, deleteFromFavourite = {
                                        favouriteViewModel.deletePhoto(it)
                                    })
                                }
                                composable(Screens.FavouritePhotos.route) {
                                    FavouritePhotosScreen(
                                        navController = navController,
                                        favouriteState = favouriteState,
                                        deleteFromFavourite = { photo ->
                                            favouriteViewModel.deletePhoto(photo)
                                        },
                                        onNavigateToWatchPhoto = { id, photoUrl ->
                                            val encodedUrl = URLEncoder.encode(
                                                photoUrl,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate(Screens.FullScreenPhoto.route + "/$encodedUrl/$id")
                                        }
                                    )
                                }
                                composable(Screens.DownloadedPhotos.route) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
