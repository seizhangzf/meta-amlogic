#!/bin/sh

LINUX_VERSION=`uname -r`
PATH=/lib/modules/$LINUX_VERSION/kernel
if [ -e $PATH/media/dovi.ko ];then
    /sbin/insmod $PATH/media/dovi.ko
fi
