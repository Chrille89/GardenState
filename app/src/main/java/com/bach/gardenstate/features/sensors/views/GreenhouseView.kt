package com.bach.gardenstate.features.sensors.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.sensors.model.TemperatureSensorGreenhouse
import com.bach.gardenstate.features.actors.model.WaterValveData
import com.bach.gardenstate.features.sensors.model.SoilMoistureSensorData
import com.bach.gardenstate.features.sensors.viewmodel.GreenHouseViewModel
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.utils.DateFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun GreenhouseView(
    modifier: Modifier = Modifier,
    greenhouseViewModel: GreenHouseViewModel = viewModel()
) {
    val soilMoistureSensorData: SoilMoistureSensorData =
        greenhouseViewModel.messageSoilMoistureSensor.value

    Card(
        modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) {
        Text("Gewächshaus", Modifier.padding(5.dp), style = MaterialTheme.typography.titleLarge)
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val dateTime: LocalDateTime = Instant.parse(soilMoistureSensorData.last_seen)
                .toLocalDateTime(TimeZone.currentSystemDefault())

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Bodenfeuchte")
                    Text("${soilMoistureSensorData.soil_moisture} %")
                }
                LinearProgressIndicator(
                    progress = { soilMoistureSensorData.soil_moisture.toFloat() / 100 },
                    modifier = Modifier.fillMaxWidth(),
                    color = if (soilMoistureSensorData.soil_moisture < 30) Color.Red
                    else if (soilMoistureSensorData.soil_moisture < 40) Color.Yellow
                    else Color.Green
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Bodentemperatur")
                    if (soilMoistureSensorData.temperature < 5) {
                        Text(
                            "Achtung Bodenfrost!",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red
                        )
                    }
                }
                Text("${soilMoistureSensorData.temperature} °C")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Verbindungs-Qualität")
                Text("${soilMoistureSensorData.linkquality}")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Zuletzt Aktualisiert")
                Text("${DateFormatter.formatDateTime(dateTime)}\"")
            }

        }
    }
}

@Composable
@Preview
fun GreenhouseViewPreview() {
    GardenStateTheme {
        GreenhouseView()
    }
}