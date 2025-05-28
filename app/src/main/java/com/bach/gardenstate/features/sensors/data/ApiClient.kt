package com.bach.gardenstate.features.sensors.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json


object ApiClient {
    private const val BASE_URL = "http://192.168.188.21:3000/temperature-greenhouse"

    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getActualTemperature(): HttpResponse {
        return httpClient.get(BASE_URL)
    }
}