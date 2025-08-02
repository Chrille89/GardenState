package com.bach.gardenstate.features.actors.model

sealed interface PoolPumpUIState {
    data object isLoading : PoolPumpUIState
    data class success(val poolPumpData: PoolPumpData) : PoolPumpUIState
}