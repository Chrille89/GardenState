package com.bach.gardenstate.features.sensors.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.sensors.model.TemperatureSensorUIState
import com.bach.gardenstate.features.sensors.viewmodel.TemperatureSensorGreenhouseViewModel
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.utils.DateFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TemperatureSensorGreenhouseView(
    modifier: Modifier = Modifier,
    temperatureSensorGreenhouseViewModel: TemperatureSensorGreenhouseViewModel = viewModel()
) {
    val temperatureSensorUIState: TemperatureSensorUIState =
        temperatureSensorGreenhouseViewModel.messageTemperatureSensor.value

    Card(
        modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            "Temperatur Gewächshaus",
            Modifier.padding(5.dp),
            style = MaterialTheme.typography.titleLarge
        )

        when (temperatureSensorUIState) {
            TemperatureSensorUIState.isLoading ->
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { CircularProgressIndicator() }

            is TemperatureSensorUIState.success ->
                Column(
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Temperatur")
                            Text("${temperatureSensorUIState.temperatureSensorData.temperature} %")
                        }
                        if(temperatureSensorUIState.temperatureSensorData.temperature < 15) {
                            Row {
                                Text("Bitte Gewächshaus schließen!", color = Color.Yellow, style = MaterialTheme.typography.bodySmall)
                            }
                        } else if(temperatureSensorUIState.temperatureSensorData.temperature > 25) {
                            Row {
                                Text("Bitte Gewächshaus öffnen bzw. schattieren!", color = Color.Yellow, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Luftfeuchtigkeit")
                        Text("${temperatureSensorUIState.temperatureSensorData.humidity} %")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Verbindungs-Qualität")
                        Text("${temperatureSensorUIState.temperatureSensorData.linkquality}")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val dateTime: LocalDateTime =
                            Instant.parse(temperatureSensorUIState.temperatureSensorData.last_seen)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                        Text("Zuletzt Aktualisiert")
                        Text(DateFormatter.formatDateTime(dateTime))
                    }

                }
        }

    }
}

@Preview
@Composable
fun TemperatureSensorGreenhousePreview() {
    GardenStateTheme {
        TemperatureSensorGreenhouseView()
    }
}