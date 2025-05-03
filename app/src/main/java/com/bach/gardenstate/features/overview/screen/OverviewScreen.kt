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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.overview.model.SoilMoistureSensorData
import com.bach.gardenstate.features.overview.model.WaterValveData
import com.bach.gardenstate.features.overview.viewmodel.OverviewScreenViewModel
import com.bach.gardenstate.ui.theme.GardenStateTheme
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime


@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    overviewScreenViewModel: OverviewScreenViewModel = viewModel()
) {
    val sensorData: SoilMoistureSensorData = overviewScreenViewModel.message.value
    Scaffold(modifier) { paddings ->
        Column(
            Modifier
                .padding(paddings)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(Color.Green),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Text("Möhren", color = Color.Black)
                }
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(Color.Yellow),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Kartoffeln", color = Color.Black)
                }

            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(overviewScreenViewModel.backgroundColor.value),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val dateTime: LocalDateTime = Instant.parse(sensorData.last_seen)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                    overviewScreenViewModel.setBackGroundColorBySoilMoisture(sensorData.soil_moisture)
                    Text("Zwiebeln", color = Color.Black)
                    Text("Aktualisiert: ${formatDateTime(dateTime)}", color = Color.Black)

                }
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(Color.Red),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Mangold", color = Color.Black)
                }

            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(Color.Red),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Kohl", color = Color.Black)
                }
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(Color.Red),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Rettich", color = Color.Black)
                }

            }
        }
    }
}


@OptIn(FormatStringsInDatetimeFormats::class)
fun formatDateTime(time: LocalDateTime): String {
    val format = LocalDateTime.Format { byUnicodePattern("dd.MM.yy HH:mm") }
    return format.format(time)
}

@Composable
@Preview
fun OverviewScreenPreview() {
    GardenStateTheme {
        OverviewScreen()
    }
}