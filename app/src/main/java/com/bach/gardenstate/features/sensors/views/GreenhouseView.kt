package com.bach.gardenstate.features.sensors.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.sensors.model.TemperatureSensorGreenhouse
import com.bach.gardenstate.features.sensors.model.WaterValveData
import com.bach.gardenstate.features.sensors.viewmodel.GreenHouseViewModel
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.utils.DateFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun GreenhouseView(
    modifier: Modifier = Modifier,
    greenhouseViewModel: GreenHouseViewModel = viewModel()
) {
    val waterValveDataState: WaterValveData = greenhouseViewModel.messageWaterValveGreenHouse.value
    val temperatureSensorGreenhouseState: TemperatureSensorGreenhouse =
        greenhouseViewModel.messageTemperatureSensorGreenhouse.value
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Temperatur")
                    if (temperatureSensorGreenhouseState.temperature > 40) {
                        Text(
                            "Bitte Gewächshaus öffnen und ggf. schattieren!",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red
                        )
                    } else if (temperatureSensorGreenhouseState.temperature < 15) {
                        Text(
                            "Bitte Gewächshaus schließen!",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red
                        )
                    }
                }
                Text("${temperatureSensorGreenhouseState.temperature} °C")

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Luftfeuchtigkeit")
                Text("${temperatureSensorGreenhouseState.humidity} %")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Verbindungs-Qualität Sensor")
                Text("${temperatureSensorGreenhouseState.linkquality}")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Zuletzt Aktualisiert")
                Text(
                    "${
                        DateFormatter.formatDateTime(
                            Instant.parse(temperatureSensorGreenhouseState.last_seen)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                        )
                    }\""
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Bewässerung")
                    if (waterValveDataState.state == "OFF") {
                        Text(
                            "Am nächsten Morgen wird bewässert!",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red
                        )
                    }
                }
                Switch(
                    checked = waterValveDataState.state == "ON",
                    onCheckedChange = {
                        greenhouseViewModel.onChangeWaterValveState(it)
                    }
                )
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