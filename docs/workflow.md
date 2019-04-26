## Development Workflow
### Setting Hostname
You probably changed your Raspberry Pis hostname.  We need to tell the following scripts about that.  Please update `RPI_HOSTNAME` with the name of your Pi.  Be sure to leave no extra whitespace or newlines.

### Debugging the project
To debug the project code, all you need to do is run `debug.sh` from the root of the pi-naut project.  This will stop the existing Pi Naut service on your Pi, transfer your project to the Raspberry Pi and run it like a regular Java app in your terminal.  Output will be sent back over standard output.

### Deploying the projects
When you're happy with the changes and want to "install" the project onto your Pi, run `deploy.sh` from the root of the pi-naut project.  This will transfer the project to your Pi and restart the service that will normally automatically run it.  After a minute or two (the project may take awhile to actually start up) the screen should display.  You can check any errors from your application in this case by running `sudo journalctl -xe` to see the last bit of logs for the system, which is where the service will be putting its output and errors.

1. To deploy code from your local machine to your Pi, run `./gradlew clean build && rsync -avzhe ssh ./build/libs/pi-naut-*-all.jar pi@raspberrypi.local:pi-naut.jar`
2. `ssh pi@raspberrypi.local` into your Pi, and run `java -jar pi-naut.jar -verbose`
