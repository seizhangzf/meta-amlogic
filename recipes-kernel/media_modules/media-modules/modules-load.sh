#!/bin/sh

LINUX_VERSION=`uname -r`
PATH=/lib/modules/$LINUX_VERSION/kernel

insmod $PATH/media/media_clock.ko
insmod $PATH/media/firmware.ko
insmod $PATH/media/decoder_common.ko
insmod $PATH/media/stream_input.ko
insmod $PATH/media/amvdec_avs.ko
insmod $PATH/media/amvdec_h264.ko
insmod $PATH/media/amvdec_mh264.ko
insmod $PATH/media/amvdec_h264mvc.ko
insmod $PATH/media/amvdec_h265.ko
insmod $PATH/media/amvdec_mjpeg.ko
insmod $PATH/media/amvdec_mmjpeg.ko
insmod $PATH/media/amvdec_mpeg12.ko
insmod $PATH/media/amvdec_mmpeg12.ko
insmod $PATH/media/amvdec_mpeg4.ko
insmod $PATH/media/amvdec_mmpeg4.ko
insmod $PATH/media/amvdec_ports.ko
insmod $PATH/media/amvdec_real.ko
insmod $PATH/media/amvdec_vc1.ko
insmod $PATH/media/amvdec_vp9.ko
insmod $PATH/media/amvdec_avs2.ko
