package com.bach.gardenstate.features.overview.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.overview.model.SoilMoistureSensorData
import com.bach.gardenstate.features.overview.model.TemperatureSensorGreenhouse
import com.bach.gardenstate.features.overview.model.WaterValveData
import com.bach.gardenstate.features.overview.viewmodel.OverviewScreenViewModel
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.utils.DateFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    overviewScreenViewModel: OverviewScreenViewModel = viewModel()
) {
    val waterValveDataState : WaterValveData = overviewScreenViewModel.messageWaterValveSensor.value
    val soilMoistureSensorDataState: SoilMoistureSensorData = overviewScreenViewModel.messageSoilMoistureSensor.value
    val temperatureSensorGreenhouseState: TemperatureSensorGreenhouse = overviewScreenViewModel.messageTemperatureSensorGreenhouse.value

    Scaffold(
        modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("GardenState")}
            )
        },
        ) { paddings ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings)

        ) {

            Card(
                modifier = Modifier.fillMaxWidth().padding(10.dp)

            ) {
                Column(modifier = Modifier.padding(5.dp)) {
                    val dateTime: LocalDateTime = Instant.parse(soilMoistureSensorDataState.last_seen)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                    Text("Gemüsebeet", style = MaterialTheme.typography.titleMedium)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Bodenfeuchte")
                        Text("${soilMoistureSensorDataState.soil_moisture} %")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Bodentemperatur")
                        Text("${soilMoistureSensorDataState.temperature} °C")                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Verbindungs-Qualität Sensor")
                        Text("${soilMoistureSensorDataState.linkquality}")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Ladestand Sensor")
                        Text("${soilMoistureSensorDataState.battery} %")

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Zuletzt Aktualisiert")
                        Text("${DateFormatter.formatDateTime(dateTime)}\"")
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Bewässerung")
                            if(soilMoistureSensorDataState.soil_moisture > 40) {
                                Text("Alles im Grünen Bereich",style = MaterialTheme.typography.bodySmall,color= Color.Green)
                            } else {
                                Text("Am nächsten Morgen wird bewässert!",style = MaterialTheme.typography.bodySmall,color= Color.Red)
                            }
                        }
                        var checked by remember { mutableStateOf(true) }
                        Switch(
                            checked = waterValveDataState.state == "ON",
                            onCheckedChange = {
                                overviewScreenViewModel.onChangeWaterValveState(it)
                            }
                        )
                    }
                }

            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(10.dp)

            ) {
                Column(modifier = Modifier.padding(5.dp)) {
                    val dateTime: LocalDateTime = Instant.parse(temperatureSensorGreenhouseState.last_seen)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                    Text("Gewächshaus", style = MaterialTheme.typography.titleMedium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Temperatur")
                            if(temperatureSensorGreenhouseState.temperature > 40) {
                                Text("Bitte Gewächshaus öffnen und ggf. schattieren!",style = MaterialTheme.typography.bodySmall,color= Color.Red)
                            } else if(temperatureSensorGreenhouseState.temperature < 15){
                                Text("Bitte Gewächshaus schließen!",style = MaterialTheme.typography.bodySmall,color= Color.Red)
                            } else {
                                Text("Alles im Grünen Bereich",style = MaterialTheme.typography.bodySmall,color= Color.Green)
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
                        Text(temperatureSensorGreenhouseState.linkquality.toString())
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

    }
}

@Composable
@Preview
fun OverviewScreenPreview() {
    GardenStateTheme {
        OverviewScreen()
    }
}