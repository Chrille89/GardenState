package com.bach.gardenstate.features.actors.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val actorFriendlyName: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WaterValveViewModel::class.java) -> {
                WaterValveViewModel(actorFriendlyName) as T
            }
            modelClass.isAssignableFrom(PoolPumpViewModel::class.java) -> {
                PoolPumpViewModel(actorFriendlyName) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}