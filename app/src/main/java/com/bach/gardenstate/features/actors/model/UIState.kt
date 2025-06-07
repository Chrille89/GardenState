package com.bach.gardenstate.features.actors.model

sealed interface UIState {
    data object isLoading : UIState
    data class success(val waterValveData: WaterValveData) : UIState
}