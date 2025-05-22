package com.bach.gardenstate.features.sensors.model

sealed interface SoilMoistureSensorUIState {
    data object isLoading : SoilMoistureSensorUIState
    data class success(val soilMoistureSensorData: SoilMoistureSensorData) :
        SoilMoistureSensorUIState
}