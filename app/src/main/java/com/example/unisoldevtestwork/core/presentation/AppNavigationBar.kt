package com.example.unisoldevtestwork.core.presentation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigationBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomNavigationItems = listOf(
        Screens.CategoryPhotos,
        Screens.FavouritePhotos,
        Screens.DownloadedPhotos,
    )
//    val isNavigationScreen: Boolean = navController.currentDestination?.route in bottomNavigationItems.map { it.route }
    NavigationBar {
        bottomNavigationItems.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = screen.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = screen.resourceId))
                },
                alwaysShowLabel = true
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBottomBar() {
    val navController = rememberNavController()
    AppNavigationBar(
        navController = navController,
    )
}
