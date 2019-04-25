#!/bin/bash
./gradlew build \
  && ssh pi@raspberrypi.local "sudo systemctl stop pi-naut" \
  && rsync -avzhe ssh ./build/libs/pi-naut-*-all.jar pi@raspberrypi.local:/opt/pi-naut/pi-naut.jar \
  && ssh pi@raspberrypi.local "/usr/bin/java -jar /opt/pi-naut/pi-naut.jar"
