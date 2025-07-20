package com.bach.gardenstate.ui.views

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@Composable
fun TriangleShape(modifier: Modifier = Modifier, color: Color = Color.Red) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo((size.width / 2f), size.height) // untere Spitze
            lineTo(0f, 0f)     // obere linke Ecke
            lineTo(size.width, 0f) // obere rechte Ecke
            close()
        }
        drawPath(path, color)
    }
}