package com.example.unisoldevtestwork.core.presentation.drawer_layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun AppDrawerContent(
    onNavigateToSettings:() -> Unit
) {
    val items = listOf<DrawerItems>(
        DrawerItems.Settings
    )
    LazyColumn {
        itemsIndexed(items) { index, item ->
            ListItem(
                leadingContent = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(
                            id = item.title
                        )
                    )
                },
                headlineContent = {
                    Text(text = stringResource(id = item.title))
                },
                modifier = Modifier.clickable {
                    onNavigateToSettings()
                }
            )
        }
    }
}