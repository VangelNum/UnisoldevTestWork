package com.example.unisoldevtestwork.core.presentation.composableTemplates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.unisoldevtestwork.ui.theme.GradientBlack

@Composable
fun ShowGradientBelow () {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    GradientBlack
                ),
                startY = 600F
            )
        ))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewGradientBelow() {
    ShowGradientBelow()
}