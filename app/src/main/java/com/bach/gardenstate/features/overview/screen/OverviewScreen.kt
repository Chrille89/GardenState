package com.bach.gardenstate.features.overview.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.overview.model.SoilMoistureSensorData
import com.bach.gardenstate.features.overview.viewmodel.OverviewScreenViewModel
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.utils.DateFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    overviewScreenViewModel: OverviewScreenViewModel = viewModel()
) {
    val soilMoistureSensorData: SoilMoistureSensorData = overviewScreenViewModel.message.value
    Scaffold(modifier) { paddings ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings)
                .background(overviewScreenViewModel.backgroundColor.value),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val dateTime: LocalDateTime = Instant.parse(soilMoistureSensorData.last_seen)
                .toLocalDateTime(TimeZone.currentSystemDefault())
            Text("Gemüsebeet")
            Text("Bodenfeuchte: ${soilMoistureSensorData.soil_moisture} %")
            Text("Stand: ${DateFormatter.formatDateTime(dateTime)}")
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