package com.bach.gardenstate.features.sensors.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.sensors.model.TemperatureSensorGreenhouse
import com.bach.gardenstate.features.sensors.model.WaterValveData
import kotlinx.serialization.json.Json

class GreenHouseViewModel : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }
    private val mqttServerUri: String = "tcp://192.168.188.21:1883"
    private val waterValveGreenHouseMqttTopic: String = "zigbee2mqtt/Watervalve_Greenhouse"
    private val temperatureSensorGreenHouseMqttTopic: String =
        "zigbee2mqtt/TemperatureSensor_Greenhouse"

    private val waterValveMqttClientManager = MqttClientManager(
        mqttServerUri,
        "$waterValveGreenHouseMqttTopic/set"
    ) { message ->
        Log.d("GreenHouseViewModel", "message: $message.value")
    }

    private val _messageTemperatureSensorGreenhouse: MutableState<TemperatureSensorGreenhouse> =
        mutableStateOf(
            TemperatureSensorGreenhouse(
                "2025-05-03T22:27:46+02:00",
                29.02f,
                humidity = 99.05f,
                linkquality = 50
            )
        )
    val messageTemperatureSensorGreenhouse: State<TemperatureSensorGreenhouse> =
        _messageTemperatureSensorGreenhouse

    private val _messageWaterValveGreenHouse: MutableState<WaterValveData> = mutableStateOf(
        WaterValveData(50, "2025-05-03T22:27:46+02:00", 32, "OFF", 100)
    )
    val messageWaterValveGreenHouse: State<WaterValveData> =
        _messageWaterValveGreenHouse

    init {
        subscribeTemperatureSensorGreenHouse()
        subscribeWaterValve()
    }

    private fun subscribeTemperatureSensorGreenHouse() {
        MqttClientManager(mqttServerUri, temperatureSensorGreenHouseMqttTopic)
        { message ->
            Log.d("OverviewScreenViewModel", message)
            _messageTemperatureSensorGreenhouse.value =
                withUnknownKeys.decodeFromString<TemperatureSensorGreenhouse>(message)
        }
    }

    private fun subscribeWaterValve() {
        MqttClientManager(mqttServerUri, waterValveGreenHouseMqttTopic)
        { message ->
            _messageWaterValveGreenHouse.value =
                withUnknownKeys.decodeFromString<WaterValveData>(message)
        }
    }

    fun onChangeWaterValveState(checked: Boolean) {
        if (checked) {
            waterValveMqttClientManager.publish("{\"state\":\"ON\"}")
        } else {
            waterValveMqttClientManager.publish("{\"state\":\"OFF\"}")
        }
    }
}