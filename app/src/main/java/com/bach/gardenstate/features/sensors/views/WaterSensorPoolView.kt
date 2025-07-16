package com.bach.gardenstate.features.sensors.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterSensorPoolView(modifier: Modifier = Modifier) {

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

        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Temperatur")
                Text("25 °C")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("pH-Wert")
                Text("7.29")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Freies (aktives) Chlor")
                Text("0.5 ppm}")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Oxidations-Reduktions-Potenzial (Redox-Wert)")
                Text("642 mV")
            }



        }
    }

}
}