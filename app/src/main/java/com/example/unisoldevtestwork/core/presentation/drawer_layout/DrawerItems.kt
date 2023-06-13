package com.example.unisoldevtestwork.core.presentation.drawer_layout

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.unisoldevtestwork.R

sealed class DrawerItems(@StringRes val title: Int,@DrawableRes val icon: Int) {
    object Settings: DrawerItems(R.string.settings, R.drawable.baseline_settings_24)
}