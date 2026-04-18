package com.bach.gardenstate.features.sensors.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TemperatureSensorViewModelFactory(private val endpoint: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TemperatureSensorViewModel::class.java) -> {
                TemperatureSensorViewModel(endpoint) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
