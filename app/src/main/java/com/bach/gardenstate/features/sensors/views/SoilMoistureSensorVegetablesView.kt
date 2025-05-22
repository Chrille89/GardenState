package com.bach.gardenstate.features.sensors.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.sensors.model.SoilMoistureSensorUIState
import com.bach.gardenstate.features.sensors.viewmodel.SoilMoistureVegetablesViewModel
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.utils.DateFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun SoilMoistureSensorVegetablesView(
    modifier: Modifier = Modifier,
    soilMoistureVegetablesViewModel: SoilMoistureVegetablesViewModel = viewModel()
) {
    val soilMoistureSensorDataState: SoilMoistureSensorUIState =
        soilMoistureVegetablesViewModel.messageSoilMoistureSensor.value

    Card(
        modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text("Gemüsebeet", Modifier.padding(5.dp), style = MaterialTheme.typography.titleLarge)

        when (soilMoistureSensorDataState) {
            SoilMoistureSensorUIState.isLoading ->
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { CircularProgressIndicator() }

            is SoilMoistureSensorUIState.success ->
                Column(
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Bodenfeuchte")
                            Text("${soilMoistureSensorDataState.soilMoistureSensorData.temperature} %")
                        }
                        if(soilMoistureSensorDataState.soilMoistureSensorData.temperature < 40) {
                            Row {
                                Text("Morgen wird bewässert!", color = Color.Yellow, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Bodentemperatur")
                            if (soilMoistureSensorDataState.soilMoistureSensorData.temperature < 5) {
                                Text(
                                    "Achtung Bodenfrost!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Red
                                )
                            }
                        }
                        Text("${soilMoistureSensorDataState.soilMoistureSensorData.temperature} °C")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Verbindungs-Qualität")
                        Text("${soilMoistureSensorDataState.soilMoistureSensorData.linkquality}")
                    }
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Akku Sensor")
                            Text("${soilMoistureSensorDataState.soilMoistureSensorData.battery} %")
                        }
                        Row {
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                progress = { soilMoistureSensorDataState.soilMoistureSensorData.battery.toFloat() / 100 })
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val dateTime: LocalDateTime =
                            Instant.parse(soilMoistureSensorDataState.soilMoistureSensorData.last_seen)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                        Text("Zuletzt Aktualisiert")
                        Text(DateFormatter.formatDateTime(dateTime))
                    }

                }
        }

    }
}

@Composable
@Preview
fun SoilMoistureSensorVegetablesPreview() {
    GardenStateTheme {
        SoilMoistureSensorVegetablesView()
    }
}

