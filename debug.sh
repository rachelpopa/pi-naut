#!/bin/bash
RPI_HOSTNAME=$(cat RPI_HOSTNAME)
./gradlew build \
  && ssh pi@${RPI_HOSTNAME}.local "sudo systemctl stop pi-naut" \
  && rsync -avzhe ssh ./build/libs/pi-naut-*-all.jar pi@${RPI_HOSTNAME}.local:/opt/pi-naut/pi-naut.jar \
  && ssh pi@${RPI_HOSTNAME}.local "/usr/bin/java -jar /opt/pi-naut/pi-naut.jar"
