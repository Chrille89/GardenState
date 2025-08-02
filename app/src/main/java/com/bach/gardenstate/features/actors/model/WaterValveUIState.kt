package com.bach.gardenstate.features.actors.model

sealed interface WaterValveUIState {
    data object isLoading : WaterValveUIState
    data class success(val waterValveData: WaterValveData) : WaterValveUIState
}