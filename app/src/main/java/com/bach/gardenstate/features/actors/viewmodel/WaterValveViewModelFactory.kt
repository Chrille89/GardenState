package com.bach.gardenstate.features.actors.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WaterValveViewModelFactory(private val waterValveTopic: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WaterValveViewModel(waterValveTopic) as T
    }
}