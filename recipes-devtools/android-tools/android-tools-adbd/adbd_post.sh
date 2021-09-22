#!/bin/sh

usb_net_ipconfig()
{
    ifconfig usb0 192.168.5.1 up
}

sleep 3

if [ -f /etc/adb_udc_file ]; then
    echo $(cat /etc/adb_udc_file) > /sys/kernel/config/usb_gadget/amlogic/UDC
else
    echo ff400000.dwc2_a > /sys/kernel/config/usb_gadget/amlogic/UDC
fi
/usr/bin/usb_monitor &

usb_net_ipconfig

echo "------------------------------------"
echo "usb rndis & adb start: OK!"
echo "------------------------------------"
