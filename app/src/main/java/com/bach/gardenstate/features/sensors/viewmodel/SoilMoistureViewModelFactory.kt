package com.bach.gardenstate.features.sensors.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bach.gardenstate.features.sensors.model.SoilMoistureType

class SoilMoistureViewModelFactory(private var soilMoistureType: SoilMoistureType) : ViewModelProvider.Factory {
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SoilMoistureViewModel(soilMoistureType) as T
    }
}