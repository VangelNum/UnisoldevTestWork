package com.example.unisoldevtestwork.core.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.unisoldevtestwork.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(onNavigationButtonClick: ()-> Unit) {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.unisoldev))
    }, navigationIcon = {
        IconButton(onClick = { onNavigationButtonClick() }) {
            Icon(
                imageVector = Icons.Filled.Menu, contentDescription = stringResource(
                    id = R.string.menu
                )
            )
        }
    })
}