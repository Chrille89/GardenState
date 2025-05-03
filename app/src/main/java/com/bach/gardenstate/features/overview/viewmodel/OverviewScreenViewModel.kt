package com.bach.gardenstate.features.overview.viewmodel

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.overview.model.SoilMoistureSensorData
import com.bach.gardenstate.features.overview.model.WaterValveData
import com.bach.gardenstate.ui.theme.LightGreen
import com.bach.gardenstate.ui.theme.Orange
import kotlinx.serialization.json.Json

class OverviewScreenViewModel : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }

    private val mqttServerUri: String = "tcp://192.168.188.21:1883"
    private val mqttTopic: String = "zigbee2mqtt/SoilMoistureSensor"

    private val _message: MutableState<SoilMoistureSensorData> = mutableStateOf(
       // WaterValveData(50, "2025-05-03T22:27:46+02:00", 32, "OFF", 50)
        SoilMoistureSensorData(50,"2025-05-03T22:27:46+02:00",32,38,20)
    )
    val message: State<SoilMoistureSensorData> = _message

    private val _backgroundColor: MutableState<Color> = mutableStateOf(
    Color.Red
    )
    val backgroundColor: State<Color> = _backgroundColor

    private val mqttClientManager: MqttClientManager =
        MqttClientManager(mqttServerUri, mqttTopic) { message ->
            _message.value = withUnknownKeys.decodeFromString<SoilMoistureSensorData>(message)
            Log.d("OverviewScreenViewModel", "message: $_message.value")

        }

     fun setBackGroundColorBySoilMoisture(soilMoisture: Int) {
        when(soilMoisture) {
            in 0..20 -> _backgroundColor.value = Color.Red
            in 20..40  -> _backgroundColor.value = Color.Yellow
            in 40..60  -> _backgroundColor.value = LightGreen
            else -> _backgroundColor.value =  Color.Green
        }
    }
}