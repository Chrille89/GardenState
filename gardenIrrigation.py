# Module Imports
import paho.mqtt.client as mqtt
import json
import time
import sys

client = mqtt.Client()

def on_connect(client, userdata, flags, rc):
    print(f"Connected with result code {rc}")
    # Subscribe, which need to put into on_connect
    # If reconnect after losing the connection with the broker, it will continue to subscribe to the raspberry/topic topic
    client.subscribe("zigbee2mqtt/SoilMoistureSensor");
    client.publish("zigbee2mqtt/bridge/request/device/interview","{\"id\": \"SoilMoistureSensor\"}");

# The callback function, it will be triggered when receiving messages
def on_message(client, userdata, msg):
    decoded_message=str(msg.payload.decode("utf-8"))
    msgjson = json.loads(decoded_message);
    soilMoisture =  msgjson["soil_moisture"];

    print("Bodenfeuchte: "+str(soilMoisture))

    if(soilMoisture < 41):
      print("Sprengen!");
      waterValveMqttClient = mqtt.Client()
      waterValveMqttClient.connect("localhost", 1883, 60)
      waterValveMqttClient.publish("zigbee2mqtt/Watervalve/set","{\"state\":\"ON\"}")
      time.sleep(1800);
      print("Stop!");
      waterValveMqttClient.publish("zigbee2mqtt/Watervalve/set","{\"state\":\"OFF\"}")
      waterValveMqttClient.disconnect()
    client.disconnect()
    sys.exit()

client.on_connect = on_connect
client.on_message = on_message

# Create connection, the three parameters are broker address, broker port number, and keep-alive time respectively
client.connect("localhost", 1883, 60)

client.loop_forever()