package com.bach.gardenstate.features.actors.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bach.gardenstate.features.actors.model.AutomaticIrrigationData
import com.bach.gardenstate.features.data.ApiClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch

class ActorScreenViewModel : ViewModel()  {

    private val _automaticIrrigation: MutableState<AutomaticIrrigationData> = mutableStateOf(AutomaticIrrigationData(false))
    val automaticIrrigation: State<AutomaticIrrigationData> = _automaticIrrigation

    init {
        viewModelScope.launch {
            val httpResponse = ApiClient.getAutomaticIrrigation()
            if (httpResponse.status.value == 200) {
                _automaticIrrigation.value = httpResponse.body()
            }
        }
    }

    fun onChangeAutomaticIrrigation(checked: Boolean) {
        viewModelScope.launch {
            val httpResponse: HttpResponse = if (checked) {
                ApiClient.patchAutomaticIrrigation(true)
            } else {
                ApiClient.patchAutomaticIrrigation(false)
            }
            if (httpResponse.status.value == 200) {
                _automaticIrrigation.value = httpResponse.body()
            }
        }
    }
}