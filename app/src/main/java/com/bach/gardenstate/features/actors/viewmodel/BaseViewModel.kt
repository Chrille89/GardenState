package com.bach.gardenstate.features.actors.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.actors.model.AutomaticIrrigationData
import com.bach.gardenstate.features.data.ApiClient
import com.bach.gardenstate.features.sensors.model.TemperatureSensorData
import com.bach.gardenstate.features.sensors.model.TemperatureSensorUIState
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

abstract class BaseViewModel(private val actorFriendlyName: String) : ViewModel() {

    protected val withUnknownKeys = Json { ignoreUnknownKeys = true }
    protected val mqttServerUri: String = "tcp://192.168.188.21:1883"
    protected val baseTopic: String = "zigbee2mqtt"
    private val interviewTopic: String = "$baseTopic/bridge/request/device/interview"

    abstract val defaultMsOnTime: Long

    protected val _stateActor: MutableState<Boolean> = mutableStateOf(
        false
    )
    val stateActor: State<Boolean> = _stateActor

    protected val mqttClientManager = MqttClientManager(
        mqttServerUri,
        "$baseTopic/$actorFriendlyName/set"
    ) { message ->
        Log.d("BaseViewModel", "message: $message.value")
    }

    protected fun interview() {
        val interviewMqttClientManager = MqttClientManager(mqttServerUri, interviewTopic)
        { message ->
            Log.d("BaseViewModel", "message: $message.value")
        }
        interviewMqttClientManager.publish("{\"id\": \"$actorFriendlyName\"}")
        interviewMqttClientManager.disconnect()
    }

    fun onChangeState(checked: Boolean) {
        if (checked) {
            viewModelScope.launch {
                mqttClientManager.publish("{\"state\":\"ON\"}")
                delay(defaultMsOnTime)
                mqttClientManager.publish("{\"state\":\"OFF\"}")
            }
        } else {
            mqttClientManager.publish("{\"state\":\"OFF\"}")
        }
    }

    abstract fun subscribe()
}