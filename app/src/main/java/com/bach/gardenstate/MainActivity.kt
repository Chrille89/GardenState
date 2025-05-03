package com.bach.gardenstate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bach.gardenstate.ui.theme.GardenStateTheme

class MainActivity : ComponentActivity() {
    private val mqttServerUri = "tcp://192.168.188.21:1883"
    private val mqttTopic = "zigbee2mqtt/SoilMoistureSensor"
    private lateinit var mqttClientManager: MqttClientManager
    private var messageState by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mqttClientManager = MqttClientManager(mqttServerUri, mqttTopic) { message ->
            messageState = message
        }

        setContent {
            GardenStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MqttView(messageState)
                   // mqttClientManager.publish("Nova mensagem")
                }

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MqttView(messageState: String) {
        Column {
            TopAppBar(title = { Text(text = "MQTT message") })
            Surface {
                Column {
                    Text(text = "MESSSAGE:")
                    Text(text = messageState)
                }
            }
        }
    }
}
