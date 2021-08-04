#!/bin/sh

LINUX_VERSION=`uname -r`
PATH=/lib/modules/$LINUX_VERSION/kernel

if [ -e $PATH/drivers/amlogic/sec_mkl/amlsec-mkl-create-devnode.sh ];then
    $PATH/drivers/amlogic/sec_mkl/amlsec-mkl-create-devnode.sh
fi

if [ -e $PATH/drivers/amlogic/ca_cert/nocs-ca-cert-create-devnode.sh ];then
    $PATH/drivers/amlogic/ca_cert/nocs-ca-cert-create-devnode.sh
fi
