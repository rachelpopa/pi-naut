# TODO, use a key-file rather than requiring manual authentication
# TODO, do a file rename to something consistent
# TODO, add to boot config on pi if not already done
./gradlew build && rsync -avzhe ssh ./build/libs/pi-naut-1.0.0-SNAPSHOT-all.jar pi@raspberrypi.local:
