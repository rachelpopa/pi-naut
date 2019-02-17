# TODO, use a key-file rather than requiring manual authentication
./gradlew build \\
  && rsync -avzhe ssh ./build/libs/pi-naut-1.0.0-SNAPSHOT-all.jar pi@raspberrypi.local/opt/github-notify/github-notify.jar \\
  && ssh pi@raspberrypi.local "sudo service restart github-notify"
