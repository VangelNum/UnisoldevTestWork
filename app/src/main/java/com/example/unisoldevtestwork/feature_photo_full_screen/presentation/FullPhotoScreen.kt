package com.example.unisoldevtestwork.feature_photo_full_screen.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.core.common.Resource
import com.example.unisoldevtestwork.feature_favourite.data.model.FavouriteEntity
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.model.FullScreenCollectionItems
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.set_wallpapers_types.WallpaperSetType
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.text_helper.UiText
import com.example.unisoldevtestwork.ui.theme.GradientBlack

//suppress because full screen image is needed
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FullPhotoScreen(
    id: String?,
    photoUrl: String?,
    onNavigateBack: () -> Unit,
    favouritePhotoState: Resource<List<FavouriteEntity>>,
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    downloadToggle: () -> Unit,
    fullPhotoDownloadChannel: UiText?,
    fullPhotoSetWallpaperChannel: UiText?,
    setWallpaperToggle: (wallpaperSetType: WallpaperSetType) -> Unit
) {
    val isPhotoInFavourite = photoUrl?.let { url ->
        id?.let { entityId ->
            favouritePhotoState.data?.contains(FavouriteEntity(id = entityId, urlPhoto = url))
        }
    } ?: false

    var isTopAppBarVisible by remember { mutableStateOf(true) }
    var isBottomAppBarVisible by remember { mutableStateOf(true) }
    val snackBarHostState = remember { SnackbarHostState() }
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(key1 = fullPhotoDownloadChannel) {
        fullPhotoDownloadChannel?.let { state ->
            snackBarHostState.showSnackbar(
                message = state.asString(context)
            )
        }
    }
    LaunchedEffect(key1 = fullPhotoSetWallpaperChannel) {
        fullPhotoSetWallpaperChannel?.let { state ->
            snackBarHostState.showSnackbar(
                message = state.asString(context)
            )
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }, topBar = {
        TopBarFullPhotoScreen(
            isTopAppBarVisible = isTopAppBarVisible, onNavigateBack = onNavigateBack
        )
    }, bottomBar = {
        BottomBarFullPhotoScreen(
            bottomAppBarVisible = isBottomAppBarVisible,
            photoInFavourite = isPhotoInFavourite,
            photoUrl = photoUrl,
            id = id,
            addToFavourite = addToFavourite,
            deleteFromFavourite = deleteFromFavourite,
            openDialog = openDialog,
            downloadToggle = downloadToggle,
        )
    }) {

        if (openDialog.value) {
            ShowInstallAlertDialog(
                openDialog = openDialog,
                setWallpaperToggle = setWallpaperToggle
            )
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    isTopAppBarVisible = !isTopAppBarVisible
                    isBottomAppBarVisible = !isBottomAppBarVisible
                })
            }) {
            SubcomposeAsyncImage(model = photoUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                })
        }
        ShowAboveGradientFullPhotoScreen()
        ShowBelowGradientFullPhotoScreen()
    }
}

@Composable
fun ShowInstallAlertDialog(
    openDialog: MutableState<Boolean>,
    setWallpaperToggle: (wallpaperSetType: WallpaperSetType) -> Unit
) {
    val radioOptions = listOf(
        R.string.main_screen, R.string.lock_screen, R.string.both_screens
    )
    val (selectedOption, onOptionSelected) = remember { mutableIntStateOf(radioOptions[0]) }
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = stringResource(id = R.string.install))
        },
        text = {
            Column(Modifier.selectableGroup()) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption), onClick = null
                        )
                        Text(
                            text = stringResource(id = text),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                openDialog.value = false
                when (selectedOption) {
                    R.string.main_screen -> {
                        setWallpaperToggle(WallpaperSetType.HOME_SCREEN)
                    }

                    R.string.lock_screen -> {
                        setWallpaperToggle(WallpaperSetType.LOCK_SCREEN)
                    }

                    R.string.both_screens -> {
                        setWallpaperToggle(WallpaperSetType.BOTH_SCREENS)
                    }
                }
            }) {
                Text(stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}


@Composable
fun ShowBelowGradientFullPhotoScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                alpha = 0.6F, brush = verticalGradient(
                    listOf(Color.Transparent, GradientBlack),
                    startY = 1500F,
                )
            ),
    )
}


@Composable
fun ShowAboveGradientFullPhotoScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                alpha = 0.6F, brush = verticalGradient(
                    listOf(GradientBlack, Color.Transparent), startY = 0F, endY = 200F
                )
            ),
    )
}


@Composable
fun BottomBarFullPhotoScreen(
    bottomAppBarVisible: Boolean,
    photoInFavourite: Boolean,
    photoUrl: String?,
    id: String?,
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit,
    openDialog: MutableState<Boolean>,
    downloadToggle: () -> Unit
) {
    AnimatedVisibility(
        visible = bottomAppBarVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomAppBar(
            containerColor = Color.Transparent,
        ) {
            val fullScreenBottomItems = listOf(
                FullScreenCollectionItems(
                    R.drawable.outline_file_download_24, R.string.download
                ),
                FullScreenCollectionItems(
                    R.drawable.outline_install_mobile_24, R.string.install
                ),
                FullScreenCollectionItems(
                    R.drawable.outline_favorite_border_24, R.string.favourite
                ),
            )
            fullScreenBottomItems.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        if (screen.name == R.string.favourite) {
                            if (photoInFavourite) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = stringResource(
                                        id = R.string.favourite,
                                    ),
                                    tint = Color.Red
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = screen.icon),
                                    contentDescription = stringResource(
                                        id = R.string.favourite
                                    ),
                                    tint = Color.White
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    label = { Text(stringResource(screen.name), color = Color.White) },
                    onClick = {
                        when (screen.name) {
                            R.string.download -> {
                                downloadToggle()
                            }

                            R.string.install -> {
                                openDialog.value = true
                            }

                            R.string.favourite -> {
                                toggleFavouriteStatus(
                                    photoInFavourite,
                                    id,
                                    photoUrl,
                                    addToFavourite,
                                    deleteFromFavourite
                                )
                            }
                        }
                    },
                    selected = false
                )
            }
        }
    }
}

fun toggleFavouriteStatus(
    photoInFavourite: Boolean,
    id: String?,
    photoUrl: String?,
    addToFavourite: (FavouriteEntity) -> Unit,
    deleteFromFavourite: (FavouriteEntity) -> Unit
) {
    if (id != null && photoUrl != null) {
        if (photoInFavourite) {
            deleteFromFavourite(FavouriteEntity(id = id, urlPhoto = photoUrl))
        } else {
            addToFavourite(FavouriteEntity(id = id, urlPhoto = photoUrl))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarFullPhotoScreen(isTopAppBarVisible: Boolean, onNavigateBack: () -> Unit) {
    AnimatedVisibility(
        visible = isTopAppBarVisible,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ) {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewFullPhotoScreen() {
    FullPhotoScreen(
        id = "",
        photoUrl = "https://stardewvalleywiki.com/mediawiki/images/a/ad/Ostrich.png",
        onNavigateBack = {},
        favouritePhotoState = Resource.Success(
            listOf(
                FavouriteEntity("0", ""),
                FavouriteEntity("0", ""),
                FavouriteEntity("0", ""),
            )
        ),
        addToFavourite = {

        },
        deleteFromFavourite = {

        },
        downloadToggle = {

        },
        fullPhotoDownloadChannel = UiText.StringResource(0),
        fullPhotoSetWallpaperChannel = null,
        setWallpaperToggle = {

        }
    )
}