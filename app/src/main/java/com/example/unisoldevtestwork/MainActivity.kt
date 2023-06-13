package com.example.unisoldevtestwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.core.presentation.PhotosViewModel
import com.example.unisoldevtestwork.core.presentation.Screens
import com.example.unisoldevtestwork.core.presentation.drawer_layout.AppDrawerContent
import com.example.unisoldevtestwork.feature_category.presentation.CategoryPhotosScreen
import com.example.unisoldevtestwork.feature_downloaded.presentation.DownloadedPhotoViewModel
import com.example.unisoldevtestwork.feature_downloaded.presentation.DownloadedPhotosScreen
import com.example.unisoldevtestwork.feature_favourite.presentation.FavouritePhotosScreen
import com.example.unisoldevtestwork.feature_favourite.presentation.FavouriteViewModel
import com.example.unisoldevtestwork.feature_list_photos_in_category.presentation.ListPhotosInCategoryScreen
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.FullPhotoScreen
import com.example.unisoldevtestwork.feature_settings.presentation.SettingsScreen
import com.example.unisoldevtestwork.feature_settings.presentation.SettingsState
import com.example.unisoldevtestwork.feature_settings.presentation.SettingsViewModel
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeConstants.DARK_THEME
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeConstants.LIGHT_THEME
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeConstants.SYSTEM_THEME
import com.example.unisoldevtestwork.ui.theme.UnisoldevTestWorkTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val navController = rememberAnimatedNavController()
            val favouriteViewModel = hiltViewModel<FavouriteViewModel>()
            val downloadedViewModel = hiltViewModel<DownloadedPhotoViewModel>()
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            val settingsState = settingsViewModel.settingsState.collectAsStateWithLifecycle().value
            val favouriteState = favouriteViewModel.favouriteState.collectAsStateWithLifecycle().value
            val scope = rememberCoroutineScope()
            val appTheme = getThemeFromSettings(settingsState)
            val systemUiController = rememberSystemUiController()
            SystemBarColors(settingsState, systemUiController)
            UnisoldevTestWorkTheme(
                useDarkTheme = appTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            if (drawerState.isOpen) {
                                BackHandler {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            }
                            ModalDrawerSheet {
                                AppDrawerContent(onNavigateToSettings = {
                                    navController.navigate(Screens.SettingsScreen.route)
                                    scope.launch {
                                        drawerState.close()
                                    }
                                })
                            }
                        }
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
                                        navController = navController,
                                        onNavigationButtonClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
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
                                    val category = entry.arguments?.getString("category")
                                    val photosViewModel = hiltViewModel<PhotosViewModel>()
                                    photosViewModel.getPhotosByCategory(
                                        category = category ?: "animal"
                                    )
                                    val photosState =
                                        photosViewModel.photosState.collectAsStateWithLifecycle().value
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
                                    }, addToDownloadedList = {
                                        downloadedViewModel.addToDownloadedList(it)
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
                                        },
                                        onNavigationButtonClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    )
                                }
                                composable(Screens.DownloadedPhotos.route) {
                                    val downloadedState =
                                        downloadedViewModel.downloadPhotosState.collectAsStateWithLifecycle().value
                                    LaunchedEffect(key1 = Unit) {
                                        if (downloadedState !is Resource.Success) {
                                            downloadedViewModel.getDownloadedPhotos()
                                        }
                                    }
                                    DownloadedPhotosScreen(
                                        navController = navController,
                                        downloadedState = downloadedState,
                                        onNavigateToWatchPhoto = { id, photoUrl ->
                                            val encodedUrl = URLEncoder.encode(
                                                photoUrl,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate(Screens.FullScreenPhoto.route + "/$encodedUrl/$id")
                                        },
                                        deleteFromDownloaded = {
                                            downloadedViewModel.deleteFromDownloaded(it)
                                        },
                                        onNavigationButtonClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    )
                                }
                                composable(Screens.SettingsScreen.route) {
                                    SettingsScreen(settingsState, onNavigateBack = {
                                        navController.popBackStack()
                                    }, onUpdateTheme = {
                                        settingsViewModel.setTheme(it)
                                    })
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
private fun getThemeFromSettings(settingsState: SettingsState): Boolean {
    return when (settingsState.themeMode) {
        LIGHT_THEME -> false
        DARK_THEME -> true
        SYSTEM_THEME -> isSystemInDarkTheme()
        else -> isSystemInDarkTheme()
    }
}

@Composable
private fun SystemBarColors(
    settingsState: SettingsState,
    systemUiController: SystemUiController
) {
    when (settingsState.themeMode) {
        LIGHT_THEME -> {
            systemUiController.setStatusBarColor(darkIcons = true, color = Color.White)
            systemUiController.setNavigationBarColor(color = MaterialTheme.colorScheme.tertiaryContainer)
        }

        DARK_THEME -> {
            systemUiController.setSystemBarsColor(color = Color.Black)
        }

        else -> {
            if (isSystemInDarkTheme()) {
                systemUiController.setSystemBarsColor(color = Color.Black)
            } else {
                systemUiController.setStatusBarColor(darkIcons = true, color = Color.White)
                systemUiController.setNavigationBarColor(color = MaterialTheme.colorScheme.tertiaryContainer)
            }
        }
    }
}



