#!/bin/sh

LINUX_VERSION=`uname -r`
PATH=/lib/modules/$LINUX_VERSION/kernel

if [ -e $PATH/media/dovi.ko ];then
    /sbin/insmod $PATH/media/dovi.ko
fi

if [ -e $PATH/drivers/amlogic/audioinfo/audio_data.ko ];then
    /sbin/insmod $PATH/drivers/amlogic/audioinfo/audio_data.ko
fi

if [ -e $PATH/drivers/amlogic/amaudio/amlogic_amaudio.ko ];then
    /sbin/insmod $PATH/drivers/amlogic/amaudio/amlogic_amaudio.ko
fi

if [ -e $PATH/drivers/amlogic/audiodsp/audiodsp.ko ];then
    /sbin/insmod $PATH/drivers/amlogic/audiodsp/audiodsp.ko
fi

if [ -e $PATH/drivers/amlogic/dvb/demux/dvb_demux.ko ];then
    /sbin/insmod $PATH/drivers/amlogic/dvb/demux/dvb_demux.ko
fi

if [ -e $PATH/media/dovi_tm2_tv_16.ko ];then
    /sbin/insmod $PATH/media/dovi_tm2_tv_16.ko
fi

if [ -e $PATH/drivers/amlogic/sec_mkl/amlsec_mkl.ko ];then
    /sbin/insmod $PATH/drivers/amlogic/sec_mkl/amlsec_mkl.ko
fi

if [ -e $PATH/drivers/amlogic/ca_cert/aml-ca-cert.ko ];then
    /sbin/insmod $PATH/drivers/amlogic/ca_cert/aml-ca-cert.ko
fi
