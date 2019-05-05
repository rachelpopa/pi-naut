#!/bin/bash
RPI_HOSTNAME=$(cat RPI_HOSTNAME)
./gradlew build \
  && ssh pi@${RPI_HOSTNAME}.local "sudo systemctl stop pi-naut" \
  && rsync -avzhe ssh ./build/libs/pikachu-github-helper-*-all.jar pi@${RPI_HOSTNAME}.local:/home/pi/pikachu-github-helper.jar \
  && ssh -t -t pi@${RPI_HOSTNAME}.local "/usr/bin/java -jar /home/pi/pikachu-github-helper.jar -verbose"
