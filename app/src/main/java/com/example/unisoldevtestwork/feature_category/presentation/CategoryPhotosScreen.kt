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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.feature_category.presentation.util.CollectionItems

@Composable
fun CategoryPhotosScreen(
    onNavigateToSelectableCategory: (String) -> Unit,
    updateCategory: (String) -> Unit
) {
    val listOfCollection = listOf(
        CollectionItems(R.drawable.abstractphoto, "Abstract"),
        CollectionItems(R.drawable.animal, "Animal"),
        CollectionItems(R.drawable.anime2, "Anime"),
        CollectionItems(R.drawable.car, "Car"),
        CollectionItems(R.drawable.cartoon, "Cartoon"),
        CollectionItems(R.drawable.city, "City"),
        CollectionItems(R.drawable.colorful, "Colorful"),
        CollectionItems(R.drawable.horror, "Horror"),
        CollectionItems(R.drawable.flower, "Flower"),
        CollectionItems(R.drawable.food, "Food")
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
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
                            updateCategory(category.name)
                            onNavigateToSelectableCategory(category.name)
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
                                        Color.Black
                                    ),
                                    startY = 10F
                                )
                            )
                    )
                    Text(
                        text = category.name,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCategoryPhotosScreen() {
    CategoryPhotosScreen(
        onNavigateToSelectableCategory = {},
        updateCategory = {}
    )
}