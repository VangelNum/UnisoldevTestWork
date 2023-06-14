package com.example.unisoldevtestwork.feature_photo_full_screen.data.repository

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.feature_photo_full_screen.domain.repository.SetWallpaperRepository
import com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.text_helper.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class SetWallpaperRepositoryImpl @Inject constructor(
    private val wallpaperManager: WallpaperManager
) : SetWallpaperRepository {
    private fun setWallpaper(bitmap: Bitmap?, flags: Int) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, flags)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private suspend fun loadBitmapFromUrl(photoUrl: String?): Result<Bitmap> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(photoUrl)
                val connection = url.openConnection()
                connection.connect()
                val inputStream = connection.getInputStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                Result.Success(bitmap)
            } catch (e: IOException) {
                Result.Failure(e)
            }
        }
    }

    override suspend fun setWallpaperToHomeScreen(photoUrl: String?): UiText {
        val bitmapResult = loadBitmapFromUrl(photoUrl)
        return when (bitmapResult) {
            is Result.Success -> {
                val bitmap = bitmapResult.data
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        setWallpaper(bitmap, WallpaperManager.FLAG_SYSTEM)
                    }
                    UiText.StringResource(R.string.success_set_wallpaper_on_home_screen)
                } catch (e: IOException) {
                    UiText.DynamicString(e.message.toString())
                }
            }

            is Result.Failure -> {
                UiText.StringResource(R.string.error_set_wallpaper)
            }
        }
    }

    override suspend fun setWallpaperToLockScreen(photoUrl: String?): UiText {
        val bitmapResult = loadBitmapFromUrl(photoUrl)
        return when (bitmapResult) {
            is Result.Success -> {
                val bitmap = bitmapResult.data
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        setWallpaper(bitmap, WallpaperManager.FLAG_LOCK)
                    }
                    UiText.StringResource(R.string.success_set_wallpaper_on_lock_screen)
                } catch (e: IOException) {
                    UiText.DynamicString(e.message.toString())
                }
            }

            is Result.Failure -> UiText.StringResource(R.string.error_set_wallpaper)
        }
    }

    override suspend fun setWallpaperToBothScreens(photoUrl: String?): UiText {
        val bitmapResult = loadBitmapFromUrl(photoUrl)
        return when (bitmapResult) {
            is Result.Success -> {
                val bitmap = bitmapResult.data
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        setWallpaper(
                            bitmap,
                            WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                        )
                    }
                    UiText.StringResource(R.string.success_set_wallpaper_both_screen)
                } catch (e: IOException) {
                    UiText.DynamicString(e.message.toString())
                }
            }

            is Result.Failure -> {
                UiText.StringResource(R.string.error_set_wallpaper)
            }
        }
    }
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}