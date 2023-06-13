package com.example.unisoldevtestwork.core.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.unisoldevtestwork.R

sealed class Screens(val route: String, @StringRes val resourceId: Int, @DrawableRes val icon: Int) {
    object CategoryPhotos : Screens("category_photos", R.string.category,R.drawable.outline_collections_24)
    object FavouritePhotos : Screens("favourite_photos", R.string.favourite,R.drawable.outline_favorite_border_24)
    object DownloadedPhotos : Screens("downloaded_photos", R.string.loadedPhotos,R.drawable.outline_file_download_24)
    object ListPhotosInCategory : Screens("list_photos_in_category", 0,0)
    object FullScreenPhoto : Screens("full_screen_photo", 0, 0)
    object SettingsScreen : Screens("settings_screen", 0, 0)
}