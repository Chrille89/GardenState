package com.bach.gardenstate.features.sensors.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.sensors.model.TemperatureSensorData
import com.bach.gardenstate.features.sensors.model.TemperatureSensorUIState
import kotlinx.serialization.json.Json

class TemperatureSensorGreenhouseViewModel : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }
    private val mqttServerUri: String = "tcp://192.168.188.21:1883"
    private val temperatureMqttTopic: String = "zigbee2mqtt/TemperatureSensor_Greenhouse"

    private val _messageTemperatureSensor: MutableState<TemperatureSensorUIState> = mutableStateOf(
        TemperatureSensorUIState.isLoading
    )
    val messageTemperatureSensor: State<TemperatureSensorUIState> = _messageTemperatureSensor

    init {
        subscribeTemperatureSensor()
    }

    private fun subscribeTemperatureSensor() {
        MqttClientManager(mqttServerUri, temperatureMqttTopic)
        { message ->
            _messageTemperatureSensor.value =
                TemperatureSensorUIState.success(
                    withUnknownKeys.decodeFromString<TemperatureSensorData>(
                        message
                    )
                )
        }
    }
}