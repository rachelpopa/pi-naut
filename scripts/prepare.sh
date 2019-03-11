#!/bin/bash

for i in "$@"; do
  case $i in
    -i=*|--image=*)
    IMAGE="${i#*=}"
    shift
    ;;
    -d=*|--device=*)
    DEVICE="${i#*=}"
    shift
    ;;
    -b=*|--bootmount=*)
    BOOTMOUNTPATH="${i#*=}"
    shift
    ;;
    -f=*|--filemount=*)
    FILEMOUNTPATH="${i#*=}"
    shift
    ;;
  esac
done

if [ -z "$IMAGE" ]; then
  echo "Error -> You must specify a source path to a Raspberry Pi Image"
  echo "Exiting!"
  exit 1
fi

if [ -z "$DEVICE" ]; then
  echo "Error -> You must specify a target path to a device"
  echo "Exiting!"
  exit 1
fi

if [ -z "$BOOTMOUNTPATH" ]; then
  echo "Error -> You must specify an existing path to a mount point for the SD boot partition"
  echo "Exiting!"
  exit 1
fi

if [ -z "$FILEMOUNTPATH" ]; then
  echo "Error -> You must specify an existing path to a mount point for the SD rootfs partition"
  echo "Exiting!"
  exit 1
fi

echo "PI Image To Write=${IMAGE}"
echo "SD Card Device Path=${DEVICE}"
echo "Temporary Boot Mount Path=${BOOTMOUNTPATH}"
echo "Temporary File Mount Path=${FILEMOUNTPATH}"
waitForPanic() {
  for i in 1 2 3 4 5; do
    echo -ne "\rWaiting ${i}/5..."
    sleep 1
  done
  echo
}

promptForSanity() {
  read -p "Continue? (y/n)" -r ANSWER
  if [ ${ANSWER} != "y" ] && [ ${ANSWER} != "Y" ]; then
    echo "Aborting."
    exit 0
  fi
}

read -p "Check variables above to ensure accuracy!  THIS COULD DESTROY DATA!  Continue (y/n)? " -r ANSWER
echo #newww line
if [ $ANSWER = "y" ] || [ ${ANSWER} = "Y" ];
then
  echo "Welcome to the Danger Zone!"
  waitForPanic

  echo "About to DD using ${IMAGE} as source and ${DEVICE} as target.  THIS COULD DESTROY DATA!"
  promptForSanity
  echo "Running DD against ${DEVICE}"
  dd if=${IMAGE} of=${DEVICE}
  sync;

  echo "Mounting RPI boot directory."
  BOOT_PARTITION="${DEVICE}p1"
  echo "Mounted ${BOOT_PARTITION} to ${BOOTMOUNTPATH}"
  mount ${BOOT_PARTITION} ${BOOTMOUNTPATH}

  # Add OTG Ethernet Modules
  echo "Adding OTG and Ethernet modules..."
  sed --in-place=_BACKUP 's/rootwait/rootwait modules-load=dwc2,g_ether/' ${BOOTMOUNTPATH}/cmdline.txt

  # Add dtoverlay config
  echo "Enabling dwc2 overlay..."
  echo "dtoverlay=dwc2" | tee --append ${BOOTMOUNTPATH}/config.txt > /dev/null

  # Speed up i2c interface (faster display)
  echo "dtparam=i2c_baudrate=1000000" | tee --append ${BOOTMOUNTPATH}/config.txt > /dev/null

  # Enable AVAHI on boot.  It's up in the air if this option is actually needed.
  echo "Enabling Avahi..."
  touch ${BOOTMOUNTPATH}/avahi
  # Enable SSH on boot.
  echo "Enabling SSH..."
  touch ${BOOTMOUNTPATH}/ssh

  echo "Unmounting $BOOT_PARTITION"
  umount ${BOOTMOUNTPATH}

  echo "Mounting RPI rootfs directory."
  FILE_PARTITION="${DEVICE}p2"

  echo "Mounted ${FILE_PARTITION} to ${FILEMOUNTPATH}"
  mount ${FILE_PARTITION} ${FILEMOUNTPATH}

  if [ -f ./install.sh ]; then
    cp install.sh ${FILEMOUNTPATH}/home/pi/install.sh
    chmod u+x ${FILEMOUNTPATH}/home/pi/install.sh
  else
    echo "Could not find install script in working directory for copy.  Ignoring."
  fi


  echo "Unmounting $FILE_PARTITION"
  umount ${FILEMOUNTPATH}

else
  echo "Aborting."
fi

# if [ ! -d "/media/$USER" ]; then
#   echo "Creating folder /media/tester"
#   mkdir /media/tester
# else
#   echo "Folder /media/tester already already exists so not creating."
# fi
#
# if [ ! -d "/media/$USER/pi-device" ]; then
#   echo "Creating folder /media/tester/pi-device"
#   mkdir /media/tester/pi-device
# else
#   echo "Folder /media/tester/pi-device already exists so not creating."
# fi
