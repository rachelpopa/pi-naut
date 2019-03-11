#!/bin/bash
echo "Updating APT... "
apt-get update
echo "Updated APT!"
#echo "Upgrading Packages with APT..."
#apt-get upgrade -y
echo "Installing OpenJDK 8 + Git..."
apt-get install -y openjdk-8-jre-headless git
echo "Installed OpenJDK 8 + Git!"

# Create Folders for Dependencies and Home Run Scripts
DEP_FOLDER="/opt/pi-naut"
if [ ! -d "${DEP_FOLDER}" ]; then
  mkdir ${DEP_FOLDER}
fi

HOME_FOLDER="/home/pi-naut"
if [ ! -d "${HOME_FOLDER}" ]; then
  mkdir ${HOME_FOLDER}
fi

cd ${DEP_FOLDER}

echo "Installing Wiring Pi..."
if [ ! -d "${DEP_FOLDER}/wiringPi" ]; then
  git clone git://git.drogon.net/wiringPi
fi
cd wiringPi && git pull origin && ./build

if ! gpio -v  > /dev/null; then
  echo "gpio -v: Something went wrong and wiringPi didn't install properly."
  exit 1
fi

if ! gpio readall > /dev/null; then
  echo "gpio readall: Something went wrong and wiringPi didn't install properly."
  exit 1
fi

echo "Installed Wiring Pi!"


echo "Installing Notify Service..."
# TODO We should probably come up with a better name.
# TODO Create the dummy hello world JAR so I can actually test this
# TODO Find a better spot maybe for where we are going to rsync the JAR to
# Decide how I want to pull down the initial hello world JAR.
# Pull down a project with it in?  Simple WGET?  Decisions...
# Just add it in with prepare.sh!
cat > /etc/systemd/system/github-notify.service << EOL
[Unit]
Description=Java Github Notify Service

[Service]
WorkingDirectory=${DEP_FOLDER}
ExecStart=/usr/bin/java -jar github-notify.jar

[Install]
WantedBy=multi-user.target

EOL
systemctl daemon-reload
systemctl enable github-notify.service
systemctl start github-notify
echo "Installed Notify Service!"
echo "Your Pi screen should say \"Hello World\" now."
