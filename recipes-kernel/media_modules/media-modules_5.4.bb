inherit module

SUMMARY = "Amlogic media driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../license/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

MBRANCH = "amlogic-5.4-dev"
SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/media_modules.git;protocol=${AML_GIT_PROTOCOL};branch=${MBRANCH};"

#For common patches
MDIR = "media_modules"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/hardware/aml-5.4/amlogic/${MDIR}')}"
SRC_URI_append = " file://modules-load.sh"
SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

COMPATIBLE_MACHINE="(mesontm2_5.4*|mesonsc2_5.4*|mesont7_*|mesons4_*)"

do_configure[noexec] = "1"

MEDIA_MODULES_UCODE_BIN = "${S}/firmware/video_ucode.bin"

do_install() {
    MEDIADIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/media
    FIRMWAREDIR=${D}/lib/firmware/video/
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${MEDIADIR} ${FIRMWAREDIR}
    find ${S}/drivers/ -name *.ko | xargs -i install -m 0666 {} ${MEDIADIR}
    install -m 0666 ${MEDIA_MODULES_UCODE_BIN} ${FIRMWAREDIR}
    install -d ${D}/etc
    install -m 0755 ${WORKDIR}/modules-load.sh ${D}/etc
}

do_install_append() {

IS_TV=${@bb.utils.contains('DISTRO_FEATURES','amlogic-tv','ztv','z',d)}

if [ ${IS_TV} == 'ztv' ]; then
cat >> ${D}/etc/modules-load.sh <<EOF
/sbin/insmod /lib/modules/${KERNEL_VERSION}/kernel/tuner/mxl661_fe.ko
/sbin/insmod /lib/modules/${KERNEL_VERSION}/kernel/media/aml_hardware_dmx.ko
if [ -f /lib/modules/${KERNEL_VERSION}/kernel/media/dovi_tm2_tv_16.ko ]
then
    /sbin/insmod /lib/modules/${KERNEL_VERSION}/kernel/media/dovi_tm2_tv_16.ko
    /bin/echo Y > /sys/module/amdolby_vision/parameters/dolby_vision_enable
    /bin/echo 0x100 > /sys/module/amdolby_vision/parameters/debug_dolby
fi
echo 1 > /sys/module/amvdec_ports/parameters/use_di_localbuffer
EOF
else
cat >> ${D}/etc/modules-load.sh <<EOF
echo 0 > /sys/module/amvdec_ports/parameters/enable_nr
echo 0 > /sys/module/amvdec_ports/parameters/use_di_localbuffer
EOF
fi
}

FILES_${PN} = " \
        /lib/firmware/video/video_ucode.bin \
        /etc/modules-load.sh \
        "

# Header file provided by a separate package
DEPENDS += ""

MEDIA_CONFIGS = " \
                 CONFIG_AMLOGIC_MEDIA_VDEC_MPEG12=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_MPEG2_MULTI=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_MPEG4=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_MPEG4_MULTI=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_VC1=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_H264=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_H264_MULTI=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_H264_MVC=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_H265=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_VP9=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_MJPEG=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_MJPEG_MULTI=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_REAL=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_AVS=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_AVS2=m \
                 CONFIG_AMLOGIC_MEDIA_VENC_H264=m \
                 CONFIG_AMLOGIC_MEDIA_VENC_H265=m \
                 CONFIG_AMLOGIC_MEDIA_VDEC_AV1=m \
                 CONFIG_AMLOGIC_MEDIA_ENHANCEMENT_DOLBYVISION=y \
                 CONFIG_AMLOGIC_MEDIA_GE2D=y \
                 "

S = "${WORKDIR}/git"
EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${S}/drivers" EXTRA_CFLAGS="$(cat ${STAGING_KERNEL_DIR}/gki_ext_module_predefine || true)" ${MEDIA_CONFIGS} modules'

KERNEL_MODULE_AUTOLOAD += "amvdec_avs2_v4l"
KERNEL_MODULE_AUTOLOAD += "amvdec_h265_v4l"
KERNEL_MODULE_AUTOLOAD += "amvdec_mh264_v4l"
KERNEL_MODULE_AUTOLOAD += "amvdec_mmjpeg_v4l"
KERNEL_MODULE_AUTOLOAD += "amvdec_mmpeg12_v4l"
KERNEL_MODULE_AUTOLOAD += "amvdec_mmpeg4_v4l"
KERNEL_MODULE_AUTOLOAD += "amvdec_vp9_v4l"
KERNEL_MODULE_AUTOLOAD += "amvdec_av1_v4l"
KERNEL_MODULE_AUTOLOAD += "amvdec_av1"
KERNEL_MODULE_AUTOLOAD += "amvdec_avs"
KERNEL_MODULE_AUTOLOAD += "amvdec_h264"
KERNEL_MODULE_AUTOLOAD += "amvdec_h264mvc"
KERNEL_MODULE_AUTOLOAD += "amvdec_h265"
KERNEL_MODULE_AUTOLOAD += "amvdec_mh264"
KERNEL_MODULE_AUTOLOAD += "amvdec_mjpeg"
KERNEL_MODULE_AUTOLOAD += "amvdec_mmjpeg"
KERNEL_MODULE_AUTOLOAD += "amvdec_mmpeg4"
KERNEL_MODULE_AUTOLOAD += "amvdec_mpeg12"
KERNEL_MODULE_AUTOLOAD += "amvdec_mmpeg12"
KERNEL_MODULE_AUTOLOAD += "amvdec_mpeg4"
KERNEL_MODULE_AUTOLOAD += "amvdec_real"
KERNEL_MODULE_AUTOLOAD += "amvdec_vc1"
KERNEL_MODULE_AUTOLOAD += "amvdec_vp9"
KERNEL_MODULE_AUTOLOAD += "decoder_common"
KERNEL_MODULE_AUTOLOAD += "firmware"
KERNEL_MODULE_AUTOLOAD += "media_clock"
KERNEL_MODULE_AUTOLOAD += "stream_input"
KERNEL_MODULE_AUTOLOAD += "amvdec_ports"
#KERNEL_MODULE_AUTOLOAD += "aml_hardware_dmx"
#KERNEL_MODULE_AUTOLOAD += "vpu"
#KERNEL_MODULE_AUTOLOAD += "encoder"
KERNEL_MODULE_PROBECONF += "amvdec_ports amvdec_mh264"
module_conf_amvdec_ports = "options amvdec_ports multiplanar=1 vp9_need_prefix=1 av1_need_prefix=1"
module_conf_amvdec_mh264 = "options amvdec_mh264 error_proc_policy=4181938"