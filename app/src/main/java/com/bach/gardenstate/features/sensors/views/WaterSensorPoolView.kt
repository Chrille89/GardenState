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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.sensors.model.SoilMoistureSensorUIState
import com.bach.gardenstate.features.sensors.model.TemperatureSensorUIState
import com.bach.gardenstate.features.sensors.model.WaterSensorPoolUIState
import com.bach.gardenstate.features.sensors.viewmodel.WaterSensorPoolViewModel
import com.bach.gardenstate.utils.DateFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun WaterSensorPoolView(
    modifier: Modifier = Modifier,
    waterSensorPoolViewModel: WaterSensorPoolViewModel = viewModel()
) {

    val waterSensorPoolUIState: WaterSensorPoolUIState =
        waterSensorPoolViewModel.messageWaterSensorPool.value

    Card(
        modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            "Wasserqualität Pool",
            Modifier.padding(5.dp),
            style = MaterialTheme.typography.titleLarge
        )

        when (waterSensorPoolUIState) {
            WaterSensorPoolUIState.isLoading ->
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { CircularProgressIndicator() }

            is WaterSensorPoolUIState.success ->
                Column(
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Temperatur")
                        Text("${waterSensorPoolUIState.waterSensorPoolData.temperature} °C")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("pH-Wert")
                        Text("${waterSensorPoolUIState.waterSensorPoolData.ph}")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Freies (aktives) Chlor")
                        Text("${waterSensorPoolUIState.waterSensorPoolData.free_chlorine} ppm")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Oxidations-Reduktions-Potenzial")
                        Text("${waterSensorPoolUIState.waterSensorPoolData.orp} mV")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Salzgehalt")
                        Text("${waterSensorPoolUIState.waterSensorPoolData.salinity} ppm")
                    }
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Akku Sensor")
                            Text("${waterSensorPoolUIState.waterSensorPoolData.battery} %")

                        }
                        Row {
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                progress = { waterSensorPoolUIState.waterSensorPoolData.battery.toFloat() / 100 })
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val dateTime: LocalDateTime =
                            Instant.parse(waterSensorPoolUIState.waterSensorPoolData.last_seen)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                        Text("Zuletzt Aktualisiert")
                        Text(DateFormatter.formatDateTime(dateTime))
                    }
                }
        }
    }
}