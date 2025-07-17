import scrollphat
import requests
import json
import time
from datetime import datetime
import sys

# Rotate 180 degrees and set brightness
scrollphat.set_rotate(True)
scrollphat.set_brightness(3)

hostname="http://192.168.188.21:3000"

def scroll():
  temperaturePool = request(hostname+"/pool","temperature")
  responseSoilMoistureVegetables = request(hostname+"/soilMoistureVegetables")
  responseSoilMoistureGreenhouse = request(hostname+"/soilMoistureGreenhouse")
  responseTmpGreenhouse = request(hostname+"/temperature-greenhouse")
  actualTime = datetime.now().strftime("%H:%M")
  message = actualTime+"Uhr Pool "+temperaturePool+"C Boden "+str(responseSoilMoistureVegetables["temperature"])+"C "+str(responseSoilMoistureVegetables["soil_moisture"])+"% Bodenfeuchte Gewaechshaus "+str(responseTmpGreenhouse["temperature"])+"C "+str(responseTmpGreenhouse["humidity"])+"% Luftfeuchte "+str(responseSoilMoistureGreenhouse["soil_moisture"])+"% Bodenfeuchte";
  print(message)
  time.sleep(5)
 # message="22:10 Uhr Pool 37C Boden 25C 50% Bodenfeuchte Gewaechshaus 25C 60% Luftfeuchte 80% Bodenfeuchte"
#  scrollphat.write_string(message.upper(), 11)
  length = scrollphat.buffer_len()

  for i in range(length):
    try:
        scrollphat.scroll()
        time.sleep(0.1)
    except KeyboardInterrupt:
        scrollphat.clear()
        sys.exit(-1)


def request(endpoint,propertyName=""):
  response = requests.get(endpoint)
  jsonResponse = json.loads(str(response.content.decode("utf-8")))

  if(propertyName != ""):
    return str(jsonResponse[propertyName])
  return jsonResponse

while True:
  scroll()

scroll()
