#!/usr/bin/env python
import paho.mqtt.client as mqtt
import scrollphat
import sys
import time
import json

poolSensor = "WaterSensor_Pool"
client = mqtt.Client()

def on_connect(client, userdata, flags, rc):
    print(f"Connected with result code {rc}")
    # Subscribe, which need to put into on_connect
    # If reconnect after losing the connection with the broker, it will continue to subscribe to the raspberry/topic topic
    client.subscribe("zigbee2mqtt/" + poolSensor)

    print(
    """
    Scroll pHAT - Scrolling Text

    Press Ctrl+C to exit!

    """
    )

    scrollphat.set_brightness(1)

    while True:
        try:
            scrollphat.scroll()
            time.sleep(0.1)
        except KeyboardInterrupt:
            scrollphat.clear()
            sys.exit(-1)

def on_message(client, userdata, msg):
    decoded_message = str(msg.payload.decode("utf-8"))
    msgjson = json.loads(decoded_message)
    temperature = msgjson["temperature"]
    print("temperature: " + str(temperature))
    scrollingText = "Pool " + str(int(temperature)) + "GRAD"
    scrollphat.write_string(scrollingText.upper(), 11)

client.on_connect = on_connect
client.on_message = on_message

# Create connection, the three parameters are broker address, broker port number, and keep-alive time respectively
client.connect("192.168.188.21", 1883, 60)

client.loop_forever()
