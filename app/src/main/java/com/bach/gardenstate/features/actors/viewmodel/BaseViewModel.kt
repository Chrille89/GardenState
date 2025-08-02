package com.bach.gardenstate.features.actors.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bach.gardenstate.MqttClientManager
import kotlinx.serialization.json.Json

abstract class BaseViewModel(private val actorFriendlyName: String) : ViewModel() {
    protected val withUnknownKeys = Json { ignoreUnknownKeys = true }
    protected val mqttServerUri: String = "tcp://192.168.188.21:1883"
    protected val baseTopic: String = "zigbee2mqtt"
    protected val interviewTopic: String = "$baseTopic/bridge/request/device/interview"

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
            mqttClientManager.publish("{\"state\":\"ON\"}")
        } else {
            mqttClientManager.publish("{\"state\":\"OFF\"}")
        }
    }

    abstract fun subscribe()
}