package com.bach.gardenstate.features.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json


object ApiClient {
    private const val BASE_URL = "http://192.168.188.21:3000"

    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAutomaticIrrigation(): HttpResponse {
        return httpClient.get("$BASE_URL/irrigation")
    }

    suspend fun patchAutomaticIrrigation(irrigation: Boolean): HttpResponse {
        return httpClient.patch("$BASE_URL/irrigation") {
            contentType(ContentType.Application.Json)
            setBody(
                """
            {
                "enabled": "$irrigation"
            }
            """.trimIndent()
            )
        }
    }

    suspend fun getActualTemperature(path: String): HttpResponse {
        return httpClient.get("$BASE_URL/temperature/$path")
    }

    suspend fun getActualPoolWaterQuality(): HttpResponse {
        return httpClient.get("$BASE_URL/pool")
    }

    suspend fun getActualSoilMoistureGreenHouse(): HttpResponse {
        return httpClient.get("$BASE_URL/soilMoistureGreenhouse")
    }

    suspend fun getActualSoilMoistureVegetables(): HttpResponse {
        return httpClient.get("$BASE_URL/soilMoistureVegetables")
    }
}