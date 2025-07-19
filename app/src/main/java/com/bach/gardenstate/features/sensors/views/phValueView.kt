package com.bach.gardenstate.features.sensors.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.ui.views.TriangleShape

@Composable
fun PhValueView(modifier: Modifier = Modifier, pointerOffset: Float = 0f) {
    Column(modifier.fillMaxSize()) {

        TriangleShape(
            color = Color.Gray,
            modifier = Modifier
                .size(50.dp),
            pointerOffset = pointerOffset
        )

        Row(modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xffff0000))) {
            }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xffff9933))) {
            }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xffffff00))) {
            }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color( 0xff66ff33))) {
            }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xff269900))) {
            }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xff269900))) {
            }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xff66ff33))) {
            }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xffffff00))) {
            }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xffff9933))) {
            }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xffff0000))) {
            }



        }
    }
}

@Composable
@Preview
fun PhValueViewPreview() {
    GardenStateTheme {
        PhValueView()
    }
}

