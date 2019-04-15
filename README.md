# Pi-Naut

An open-source seed project built on [Micronaut](https://micronaut.io/) and [Pi4J](https://pi4j.com/1.2/) for running modern Java applications on a Pi.

## Getting Started

#### Configuring your Pi

Before you can run Java on your Pi you need to have `WiringPi` and a `JRE` already installed.
The quickest way to get up and running is to run the `prepare.sh` and `install.sh` in the `/scripts` directory to image an SD card with all the fixings.
By default it comes with Adafruits SSD1306 library for components with oled displays. If need be you can simply customize the shell scripts to your liking.


##### prepare.sh

This will wipe and image a drive with an OS of the users choice and also add some basic device configuration (ssh, avahi, dtoverlay, and OTG modules).

WARNING: Before you read the docs for this commands please note that it uses DD. You are using it at your own risk and only you can be held accountable if you wipe an unintended drive.

1. Download the image that you would like to build from (Raspbian Lite, Alpine, FreeBSD). Generally we recommend [Raspian Lite](https://downloads.raspberrypi.org/raspbian_lite_latest) for most devices :)

2. Find the correct drive path that is mapped to the target micro SD card.
The disk utility you use to find it is OS dependant but you will generally see three parts to a mounted drive.
The actual drive (1s), and it's two partitions (1s1 and 1s2). We only care about the actual drive. For example, on my particular machine (macOS) it will generally show up as `/disk2s`.

3. Run the command `./prepare.sh -i=/path/to/image -d=/path-to-device`

5. Run the `install` script below.

##### install.sh

This will install OpenJDK 8, WiringPi, and Adafruit SSD1306 library (for OLED displays).

NOTE: If you did not image your micro SD card with the `prepare` script, you will have to copy the `install` script onto your pi (rsync works well).

1. Connect to your Pi via `ssh pi@raspberrypi.local`. The default password is `raspberry`.

2. Run `./install.sh`

* [How to Use](docs/how-to-use.md)

* [Development Workflow](docs/workflow.md)

## Resources

* [Micronaut docs](https://docs.micronaut.io/latest/guide/index.html)
* [Pi4J docs](https://pi4j.com/1.2/index.html)
