## Development Workflow

1. To deploy code from your local machine to your Pi, run `./gradlew clean build && rsync -avzhe ssh ./build/libs/pi-naut-*-all.jar pi@raspberrypi.local:pi-naut.jar`
2. `ssh pi@raspberrypi.local` into your Pi, and run `java -jar pi-naut.jar -verbose`

##### To deploy your application run `deploy.sh`
