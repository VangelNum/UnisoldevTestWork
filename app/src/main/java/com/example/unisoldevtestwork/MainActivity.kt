package com.example.unisoldevtestwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.unisoldevtestwork.core.presentation.AppNavigationBar
import com.example.unisoldevtestwork.core.presentation.PhotosViewModel
import com.example.unisoldevtestwork.core.presentation.Screens
import com.example.unisoldevtestwork.feature_category.presentation.CategoryPhotosScreen
import com.example.unisoldevtestwork.feature_favourite.presentation.FavouritePhotosScreen
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
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
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
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    val bottomNavigationItems = listOf(
                        Screens.CategoryPhotos,
                        Screens.FavouritePhotos,
                        Screens.DownloadedPhotos,
                    )
                    ModalNavigationDrawer(drawerState = drawerState, drawerContent = { }, content = {
                        Scaffold(
                            bottomBar = {
                                AppNavigationBar(
                                    navController = navController,
                                    bottomNavigationItems = bottomNavigationItems,
                                    currentDestination = currentDestination
                                )
                            },
                            topBar = {
                                if (navController.currentDestination?.route in bottomNavigationItems.map { it.route }) {
                                    TopAppBar(title = {
                                        Text(text = stringResource(id = R.string.unisoldev))
                                    }, navigationIcon = {
                                        IconButton(onClick = { /*TODO*/ }) {
                                            Icon(imageVector = Icons.Filled.Menu, contentDescription = stringResource(
                                                id = R.string.menu
                                            ))
                                        }
                                    })
                                }
                            }
                        ) { innerPadding ->
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
                                    )) { entry ->
                                    val photosState = photosViewModel.photosState.collectAsStateWithLifecycle().value
                                    val category = entry.arguments?.getString("category")
                                    ListPhotosInCategoryScreen(
                                        category = category, photosState, onNavigateToBack = {
                                            navController.popBackStack()
                                        }, onNavigateToWatchPhoto = { photoUrl ->
                                            val encodedUrl = URLEncoder.encode(
                                                photoUrl,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate(Screens.FullScreenPhoto.route + "/$encodedUrl")
                                        }
                                    )
                                }
                                composable(Screens.FullScreenPhoto.route + "/{photoUrl}",
                                    arguments = listOf(
                                        navArgument("photoUrl") {
                                            type = NavType.StringType
                                            nullable = false
                                            defaultValue = ""
                                        }
                                    )) { entry ->
                                    val photoUrl = entry.arguments?.getString("photoUrl")
                                    FullPhotoScreen(photoUrl, onNavigateBack = {
                                        navController.popBackStack()
                                    })
                                }
                                composable(Screens.FavouritePhotos.route) {
                                    FavouritePhotosScreen()
                                }
                                composable(Screens.DownloadedPhotos.route) {

                                }
                            }
                        }
                    })
                }
            }
        }
    }
}