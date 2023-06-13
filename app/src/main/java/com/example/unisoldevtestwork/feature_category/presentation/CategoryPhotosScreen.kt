package com.example.unisoldevtestwork.feature_category.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.core.presentation.AppNavigationBar
import com.example.unisoldevtestwork.core.presentation.AppTopBar
import com.example.unisoldevtestwork.feature_category.presentation.util.CollectionItems
import com.example.unisoldevtestwork.ui.theme.GradientBlack

@Composable
fun CategoryPhotosScreen(
    onNavigateToSelectableCategory: (String) -> Unit,
    navController: NavHostController,
    onNavigationButtonClick:() -> Unit
) {
    val listOfCollection = listOf(
        CollectionItems(R.drawable.abstractphoto, R.string.abstract_photo),
        CollectionItems(R.drawable.animal, R.string.animal),
        CollectionItems(R.drawable.anime2, R.string.anime),
        CollectionItems(R.drawable.car, R.string.car),
        CollectionItems(R.drawable.cartoon, R.string.cartoon),
        CollectionItems(R.drawable.city, R.string.city),
        CollectionItems(R.drawable.colorful, R.string.colorful),
        CollectionItems(R.drawable.horror, R.string.horror),
        CollectionItems(R.drawable.flower, R.string.flower),
        CollectionItems(R.drawable.food, R.string.food)
    )
    val context = LocalContext.current
    Scaffold(topBar = {
        AppTopBar(onNavigationButtonClick)
    }, bottomBar = {
        AppNavigationBar(
            navController
        )
    }) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(128.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            items(listOfCollection) { category ->
                Card(
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(10.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .clickable {
                                onNavigateToSelectableCategory(context.getString(category.name))
                            },
                    ) {
                        Image(
                            painter = painterResource(id = category.photoId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            GradientBlack
                                        ),
                                        startY = 600F
                                    )
                                )
                        )
                        Text(
                            text = stringResource(id = category.name),
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        )

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCategoryPhotosScreen() {
    val navController = rememberNavController()
    CategoryPhotosScreen(
        onNavigateToSelectableCategory = {},
        navController = navController,
        onNavigationButtonClick = {}
    )
}