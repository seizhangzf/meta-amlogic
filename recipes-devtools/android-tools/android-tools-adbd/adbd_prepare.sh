#!/bin/sh

usb_rndis_init()
{
    mount -t configfs none /sys/kernel/config
    mkdir /sys/kernel/config/usb_gadget/amlogic
    cd /sys/kernel/config/usb_gadget/amlogic
    echo 0x18D1 > idVendor
    echo 0x4e26 > idProduct
    mkdir strings/0x409

    #Read out serial number, and configure to ADB
    serialnumber=$(cat /proc/cmdline | sed -n 's/.*androidboot.serialno=\([0-9a-zA-Z]*\).*/\1/p')
    if [ -z "$serialnumber" ] || [ "$serialnumber" = "1234567890" ]; then
      serialnumber="0123456789ABCDEF"
    fi  

    echo $serialnumber > strings/0x409/serialnumber
    echo amlogic > strings/0x409/manufacturer
    echo p212 > strings/0x409/product
    mkdir configs/amlogic.1
    mkdir configs/amlogic.1/strings/0x409
    echo rndis > configs/amlogic.1/strings/0x409/configuration
    echo 500 > configs/amlogic.1/MaxPower
    mkdir functions/rndis.rndis
    ln -s functions/rndis.rndis configs/amlogic.1
    cd /
}

usb_adbd_init()
{
    echo "adb" > /sys/kernel/config/usb_gadget/amlogic/configs/amlogic.1/strings/0x409/configuration
    mkdir /sys/kernel/config/usb_gadget/amlogic/functions/ffs.adb
    mkdir /dev/usb-ffs
    mkdir /dev/usb-ffs/adb
    mount -t functionfs adb /dev/usb-ffs/adb
    ln -s /sys/kernel/config/usb_gadget/amlogic/functions/ffs.adb /sys/kernel/config/usb_gadget/amlogic/configs/amlogic.1/ffs.adb
}

usb_dir=/sys/kernel/config/usb_gadget/amlogic

if [ ! -d ${usb_dir} ]; then
  usb_rndis_init
  echo "usb rndis start......"

  usb_adbd_init
  echo "usb adbd start......"
fi
