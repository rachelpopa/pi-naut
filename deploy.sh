#!/bin/bash
RPI_HOSTNAME=$(cat RPI_HOSTNAME)
./gradlew build \
  && ssh pi@${RPI_HOSTNAME}.local "sudo systemctl stop pikachu-github-helper" \
  && rsync -avzhe ssh ./build/libs/pikachu-github-helper-*-all.jar pi@${RPI_HOSTNAME}.local:/opt/pikachu-github-helper/pikachu-github-helper.jar \
  && ssh pi@${RPI_HOSTNAME}.local "sudo systemctl start pikachu-github-helper"
