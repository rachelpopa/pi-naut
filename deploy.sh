#!/bin/bash
# TODO, use a key-file rather than requiring manual authentication
./gradlew build \
  && rsync -avzhe ssh ./build/libs/pi-naut-1.0.0-SNAPSHOT-all.jar pi@raspberrypi.local:/opt/pi-naut/pi-naut.jar \
  && ssh pi@raspberrypi.local "sudo systemctl restart pi-naut"
