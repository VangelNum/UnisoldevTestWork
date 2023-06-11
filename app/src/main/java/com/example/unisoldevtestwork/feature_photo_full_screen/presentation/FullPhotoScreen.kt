package com.example.unisoldevtestwork.feature_photo_full_screen.presentation

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.download.AndroidDownloaderImpl
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.model.FullScreenCollectionItems
import com.example.unisoldevtestwork.ui.theme.GradientBlack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

//suppress because full screen image is needed
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FullPhotoScreen(
    photoUrl: String?,
    onNavigateBack: () -> Unit
) {
    var isTopAppBarVisible by remember { mutableStateOf(true) }
    var isBottomAppBarVisible by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopBarFullPhotoScreen(
                isTopAppBarVisible = isTopAppBarVisible,
                onNavigateBack = onNavigateBack
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomAppBarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomAppBar(
                    containerColor = Color.Transparent,
                ) {
                    val fullScreenBottomItems = listOf(
                        FullScreenCollectionItems(
                            R.drawable.outline_file_download_24,
                            R.string.download
                        ),
                        FullScreenCollectionItems(
                            R.drawable.outline_install_mobile_24,
                            R.string.install
                        ),
                        FullScreenCollectionItems(
                            R.drawable.outline_favorite_border_24,
                            R.string.favourite
                        ),
                    )
                    fullScreenBottomItems.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = screen.icon),
                                    contentDescription = null,
                                )
                            },
                            label = { Text(stringResource(screen.name)) },
                            onClick = {
                                when (screen.name) {
                                    R.string.download -> {
                                        val downloader = AndroidDownloaderImpl(context)
                                        if (photoUrl != null) {
                                            scope.launch {
                                                snackBarHostState.showSnackbar(context.getString(R.string.download_started))
                                            }
                                            downloader.downloadFile(photoUrl)
                                        }
                                    }

                                    R.string.install -> {
                                        openDialog.value = true
                                    }
                                }
                            },
                            selected = false
                        )
                    }
                }
            }
        }
    ) {
        val radioOptions = listOf(
            R.string.main_screen,
            R.string.lock_screen,
            R.string.both_screens
        )
        val (selectedOption, onOptionSelected) = remember { mutableIntStateOf(radioOptions[0]) }
        if (openDialog.value) {
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
                                        onClick = { onOptionSelected(text) },
                                        role = Role.RadioButton
                                    )
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (text == selectedOption),
                                    onClick = null
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
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            when (selectedOption) {
                                R.string.main_screen -> {
                                    scope.launch {
                                        setWallpaperToHomeScreen(context = context, photoUrl)
                                    }
                                }

                                R.string.lock_screen -> {
                                    scope.launch {
                                        setWallpaperToLockScreen(context = context, photoUrl)
                                    }
                                }

                                R.string.both_screens -> {
                                    scope.launch {
                                        setWallpaperToBothScreens(context = context, photoUrl)
                                    }
                                }
                            }
                        }
                    ) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            isTopAppBarVisible = !isTopAppBarVisible
                            isBottomAppBarVisible = !isBottomAppBarVisible
                        }
                    )
                }
        ) {
            SubcomposeAsyncImage(
                model = photoUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    alpha = 0.8F,
                    brush = verticalGradient(
                        listOf(Color.Transparent, GradientBlack),
                        startY = 1500F,
                    )
                ),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    alpha = 0.8F,
                    brush = verticalGradient(
                        listOf(GradientBlack, Color.Transparent),
                        startY = 0F,
                        endY = 300F
                    )
                ),
        )
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
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
        )
    }
}


suspend fun setWallpaperToHomeScreen(context: Context, photoUrl: String?) {
    val bitmap = loadBitmapFromUrl(photoUrl)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        setWallpaper(context, bitmap, WallpaperManager.FLAG_SYSTEM)
    }
}

suspend fun setWallpaperToLockScreen(context: Context, photoUrl: String?) {
    val bitmap = loadBitmapFromUrl(photoUrl)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        setWallpaper(context, bitmap, WallpaperManager.FLAG_LOCK)
    }
}

suspend fun setWallpaperToBothScreens(context: Context, photoUrl: String?) {
    val bitmap = loadBitmapFromUrl(photoUrl)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        setWallpaper(context, bitmap, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
    }
}

private suspend fun loadBitmapFromUrl(photoUrl: String?): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(photoUrl)
            val connection = url.openConnection()
            connection.connect()
            val inputStream = connection.getInputStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}


private fun setWallpaper(context: Context, bitmap: Bitmap?, flags: Int) {
    val wallpaperManager = WallpaperManager.getInstance(context)
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(bitmap, null, true, flags)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewFullPhotoScreen() {
    FullPhotoScreen(
        photoUrl = "https://stardewvalleywiki.com/mediawiki/images/a/ad/Ostrich.png",
        onNavigateBack = {})
}