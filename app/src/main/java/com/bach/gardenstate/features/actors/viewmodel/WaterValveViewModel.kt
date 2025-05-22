package com.bach.gardenstate.features.actors.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.actors.model.WaterValveData
import kotlinx.serialization.json.Json

class WaterValveViewModel(private val waterValveTopic : String) : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }
    private val mqttServerUri: String = "tcp://192.168.188.21:1883"
    private val interviewTopic: String = "zigbee2mqtt/bridge/request/device/interview"

    private val _messageWaterValve: MutableState<WaterValveData> = mutableStateOf(
        WaterValveData(50, "2025-05-03T22:27:46+02:00", "00:00:30",32, "OFF", 100,"2025-05-03T22:27:46+02:00")
    )
    val messageWaterValve: State<WaterValveData> = _messageWaterValve

    private val waterValveMqttClientManager = MqttClientManager(
        mqttServerUri,
        "$waterValveTopic/set"
    ) { message ->
        Log.d("WaterValveViewModel", "message: $message.value")
    }

    init {
        subscribeWaterValve()
        interview()
    }

    private fun interview() {
        val interviewMqttClientManager = MqttClientManager(mqttServerUri, interviewTopic)
        { message ->
            Log.d("OverviewScreenViewModel", "message: $message.value")
        }
        interviewMqttClientManager.publish("{\"id\": \"$waterValveTopic\"}")
        interviewMqttClientManager.disconnect()
    }

    private fun subscribeWaterValve() {
        MqttClientManager(mqttServerUri, waterValveTopic)
        { message ->
            _messageWaterValve.value =
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