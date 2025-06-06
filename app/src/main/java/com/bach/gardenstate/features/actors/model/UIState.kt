package com.bach.gardenstate.features.actors.model

sealed interface UIState {
    data object isLoading : UIState
    data object isWaterValveChange : UIState
    data class success(val waterValveData: WaterValveData) : UIState
}