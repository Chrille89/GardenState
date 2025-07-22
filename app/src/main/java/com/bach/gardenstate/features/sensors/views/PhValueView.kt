package com.bach.gardenstate.features.sensors.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bach.gardenstate.R
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.ui.views.TriangleShape

val colors = listOf(
    Color(0xFFFF0000),
    Color(0xFFFF4800),
    Color(0xFFFF7200),
    Color(0xFFFFFC00),
    Color(0xFFC6DE00),
    Color(0xFF72A954),
    Color(0xFF1EAB00),
    Color(0xFF0BFF00),
    Color(0xFF1EAB00),
    Color(0xFF5E76FF),
    Color(0xFF003DFF),
    Color(0xFF00168D),
    Color(0xFF18007A),
    Color(0xFF500094),
    Color(0xFF6C00C5)
)

@Composable
fun PhValueView(modifier: Modifier = Modifier, actualPhValue: Int) {
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("Sauer")
            for (i in 0..14) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .weight(1f)
                        .background(colors[i]), contentAlignment = Alignment.TopCenter
                ) {
                    if (actualPhValue == i)
                        TriangleShape(modifier = Modifier.size(20.dp), color = Color.Black)
                }
            }
            Text("Basisch")
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            when {
                actualPhValue < 5 -> Text("Stark sauer – ätzend für Haut, Technik",color = colorResource(
                    R.color.lightRed))
                actualPhValue in 5 .. 6 -> Text("Noch zu sauer – Chlor aggressiv",color = colorResource(
                    R.color.orange))
                actualPhValue in 8 .. 9 -> Text("Leicht basisch – Chlor wirkt schlechter",color = colorResource(
                    R.color.darkYellow))
                actualPhValue > 9 -> Text("Zu basisch – Trübungen, Chlor fast wirkungslos",color = colorResource(
                    R.color.lightRed))
            }
        }
    }

}

@Composable
@Preview
fun PhValueViewPreview() {
    GardenStateTheme {
        PhValueView(actualPhValue = 7)
    }
}

