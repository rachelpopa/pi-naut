# TODO, use a key-file rather than requiring manual authentication
# TODO, do a file rename to something consistent
# TODO, add to boot config on pi if not already done
./gradlew build && rsync -avzhe ssh ./build/libs/pi-naut-0.1-all.jar pi@raspberrypi.local:
