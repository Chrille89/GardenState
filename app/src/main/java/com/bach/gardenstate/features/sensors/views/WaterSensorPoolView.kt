package com.bach.gardenstate.features.sensors.views

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.R
import com.bach.gardenstate.features.sensors.model.WaterSensorPoolUIState
import com.bach.gardenstate.features.sensors.viewmodel.WaterSensorPoolViewModel
import com.bach.gardenstate.ui.theme.GardenStateTheme
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
    val context = LocalContext.current
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
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row {
                                    Text("pH-Wert", modifier = Modifier
                                        .pointerInput(Unit) {
                                            detectTapGestures {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Der pH-Wert beschreibt, wie sauer oder basisch dein Poolwasser ist. Er ist ein zentraler Parameter für die Wasserqualität, weil er die Wirksamkeit von Chlor und den Komfort für Haut, Augen und Technik direkt beeinflusst. Optimal: 7,2 – 7,4.",
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                            }
                                        })
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "Mehr Informationen",
                                        tint = Color.Gray,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .padding(start = 4.dp)
                                    )
                                }

                                Text("${waterSensorPoolUIState.waterSensorPoolData.ph}")
                            }
                            PhValueView(actualPhValue = waterSensorPoolUIState.waterSensorPoolData.ph.toInt())
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text("ORP-Wert", modifier = Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        Toast
                                            .makeText(
                                                context,
                                                "Der ORP-Wert misst, wie stark das Wasser oxidierend ist – also wie gut es organische Stoffe (z.B. Bakterien, Viren, Algen) zerstören kann (Desinfektionskraft). Er sollte zwischen 650-750mV liegen.",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    }
                                })
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Mehr Informationen",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(18.dp)
                                    .padding(start = 4.dp)
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            val orp = waterSensorPoolUIState.waterSensorPoolData.orp
                            Text("$orp mV")
                            when {
                                orp < 650 -> Text(
                                    "Desinfektion unzureichend",
                                    color = colorResource(R.color.lightRed)
                                )

                                orp in 750..850 -> Text(
                                    "Starkes Oxidationspotenzial",
                                    color = colorResource(R.color.darkYellow)
                                )

                                orp > 850 -> Text(
                                    "Reizungen möglich",
                                    color = colorResource(R.color.lightRed)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text("Salzgehalt", modifier = Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        Toast
                                            .makeText(
                                                context,
                                                "Der Salzgehalt beeinflusst Hautverträglichkeit, Wassergefühl, Korrosionsrisiken und Sensorgenauigkeit. Er sollte zwischen 200–800 ppm liegen.",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    }
                                })
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Mehr Informationen",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(18.dp)
                                    .padding(start = 4.dp)
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            val salinity = waterSensorPoolUIState.waterSensorPoolData.salinity
                            Text("$salinity ppm")
                            when {
                                salinity < 300 -> Text(
                                    "Frischwasserqualität",
                                    color = colorResource(R.color.lightBlue)
                                )

                                salinity in 800..1500 -> Text(
                                    "Rückstände/Chemie",
                                    color = colorResource(R.color.darkYellow)
                                )

                                salinity in 1500..2500 -> Text(
                                    "Rückspülen, Teilwasserwechsel prüfen",
                                    color = colorResource(R.color.orange)
                                )

                                salinity > 2500 -> Text(
                                    "Hohe Salz-/Leitwertbelastung, hygienisch fragwürdig",
                                    color = colorResource(R.color.lightRed)
                                )
                            }
                        }
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

@Preview
@Composable
fun WaterSensorPoolPreview() {
    GardenStateTheme {
        WaterSensorPoolView(Modifier, viewModel())
    }
}