package com.bach.gardenstate.features.sensors.model

sealed interface TemperatureSensorUIState {
    data object isLoading : TemperatureSensorUIState
    data class success(val temperatureSensorData: TemperatureSensorData) : TemperatureSensorUIState
}