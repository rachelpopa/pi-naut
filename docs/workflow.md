## Development Workflow

`Pi4J` sits on top of `WiringPi`, a standard Python library for accessing GPIO pins. It was written for BCM devices which is not likely what you are developing on.

Since it is NOT safe to install `WiringPi` on most devices, the application must be deployed and ran directly on the Pi for active development.

##### To run your application on a Pi for active development:
1. Run `./gradlew build && rsync -avzhe ssh ./build/libs/pi-naut-*-all.jar pi@raspberrypi.local:pi-naut.jar`
2. `ssh pi@raspberrypi.local` into your Pi, and run `java -jar pi-naut.jar -verbose`
