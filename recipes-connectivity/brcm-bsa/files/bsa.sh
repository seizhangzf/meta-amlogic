#/bin/bash
echo 0 > /sys/class/rfkill/rfkill0/state;
sleep 1;
echo 1 > /sys/class/rfkill/rfkill0/state;
sleep 1
/usr/bin/bsa_server -r 13 -lpm -all=0 -pp /etc/bluetooth -d /dev/ttyS1 &
