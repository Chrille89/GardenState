package com.bach.gardenstate.features.sensors.model

sealed interface WaterSensorPoolUIState {
    data object isLoading : WaterSensorPoolUIState
    data class success(val waterSensorPoolData: WaterSensorPoolData) : WaterSensorPoolUIState
}