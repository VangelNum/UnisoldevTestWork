package com.example.unisoldevtestwork.feature_photo_full_screen.presentation.utils.set_wallpapers

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL


fun setWallpaper(context: Context, bitmap: Bitmap?, flags: Int) {
    val wallpaperManager = WallpaperManager.getInstance(context)
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(bitmap, null, true, flags)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

suspend fun loadBitmapFromUrl(photoUrl: String?): Result<Bitmap> {
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

suspend fun setWallpaperToHomeScreen(context: Context, photoUrl: String?): Result<Unit> {
    val bitmapResult = loadBitmapFromUrl(photoUrl)
    return when (bitmapResult) {
        is Result.Success -> {
            val bitmap = bitmapResult.data
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setWallpaper(context, bitmap, WallpaperManager.FLAG_SYSTEM)
                }
                Result.Success(Unit)
            } catch (e: IOException) {
                Result.Failure(e)
            }
        }

        is Result.Failure -> bitmapResult
    }
}

suspend fun setWallpaperToLockScreen(context: Context, photoUrl: String?): Result<Unit> {
    val bitmapResult = loadBitmapFromUrl(photoUrl)
    return when (bitmapResult) {
        is Result.Success -> {
            val bitmap = bitmapResult.data
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setWallpaper(context, bitmap, WallpaperManager.FLAG_LOCK)
                }
                Result.Success(Unit)
            } catch (e: IOException) {
                Result.Failure(e)
            }
        }

        is Result.Failure -> bitmapResult
    }
}

suspend fun setWallpaperToBothScreens(context: Context, photoUrl: String?): Result<Unit> {
    val bitmapResult = loadBitmapFromUrl(photoUrl)
    return when (bitmapResult) {
        is Result.Success -> {
            val bitmap = bitmapResult.data
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setWallpaper(
                        context,
                        bitmap,
                        WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                    )
                }
                Result.Success(Unit)
            } catch (e: IOException) {
                Result.Failure(e)
            }
        }

        is Result.Failure -> bitmapResult
    }
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}
