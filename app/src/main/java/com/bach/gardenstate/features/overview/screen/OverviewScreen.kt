package com.bach.gardenstate.features.overview.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bach.gardenstate.ui.theme.GardenStateTheme

@Composable
fun OverviewScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier) { paddings ->
        Column(
            Modifier.padding(paddings).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
            ) {
            Row(
                Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Column(
                    Modifier.weight(1f).fillMaxSize().background(Color.Green),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Text("Möhren")
                }
                Column(
                    Modifier.weight(1f).fillMaxSize().background(Color.Yellow),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Kartoffeln")
                }

            }
            Row(
                Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier.weight(1f).fillMaxSize().background(Color.Red),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Zwiebeln")
                }
                Column(
                    Modifier.weight(1f).fillMaxSize().background(Color.Red),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Mangold")
                }

            }
            Row(
                Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Column(
                    Modifier.weight(1f).fillMaxSize().background(Color.Red),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Kohl")
                }
                Column(
                    Modifier.weight(1f).fillMaxSize().background(Color.Red),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Rettich")
                }

            }
        }




    }

}

@Composable
@Preview
fun OverviewScreenPreview() {
    GardenStateTheme {
        OverviewScreen()
    }
}