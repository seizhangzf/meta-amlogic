inherit module

SUMMARY = "Amlogic media driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/media_modules.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-4.9-dev"
SRC_URI_append = " file://COPYING.GPL"
SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"


do_configure[noexec] = "1"

MEDIA_MODULES_UCODE_BIN = "${S}/firmware/video_ucode.bin"

do_install() {
    MEDIADIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/media
    FIRMWAREDIR=${D}/lib/firmware/video/
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${MEDIADIR} ${FIRMWAREDIR}
    find ${S}/drivers/ -name *.ko | xargs -i install -m 0666 {} ${MEDIADIR}
    install -m 0666 ${MEDIA_MODULES_UCODE_BIN} ${FIRMWAREDIR}
}

FILES_${PN} = " \
        /lib/firmware/video/video_ucode.bin \
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
                 CONFIG_AMLOGIC_MEDIA_ENHANCEMENT_DOLBYVISION=y \
                 CONFIG_AMLOGIC_MEDIA_GE2D=y \
                 "

S = "${WORKDIR}/git"
EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${S}/drivers" ${MEDIA_CONFIGS} modules'

KERNEL_MODULE_AUTOLOAD += "amvdec_avs"
KERNEL_MODULE_AUTOLOAD += "amvdec_h264"
KERNEL_MODULE_AUTOLOAD += "amvdec_h264mvc"
KERNEL_MODULE_AUTOLOAD += "amvdec_h265"
KERNEL_MODULE_AUTOLOAD += "amvdec_mh264"
KERNEL_MODULE_AUTOLOAD += "amvdec_mjpeg"
KERNEL_MODULE_AUTOLOAD += "amvdec_mmjpeg"
KERNEL_MODULE_AUTOLOAD += "amvdec_mmpeg4"
KERNEL_MODULE_AUTOLOAD += "amvdec_mpeg12"
KERNEL_MODULE_AUTOLOAD += "amvdec_mpeg4"
KERNEL_MODULE_AUTOLOAD += "amvdec_real"
KERNEL_MODULE_AUTOLOAD += "amvdec_vc1"
KERNEL_MODULE_AUTOLOAD += "amvdec_vp9"
KERNEL_MODULE_AUTOLOAD += "decoder_common"
KERNEL_MODULE_AUTOLOAD += "firmware"
KERNEL_MODULE_AUTOLOAD += "media_clock"
KERNEL_MODULE_AUTOLOAD += "stream_input"
KERNEL_MODULE_AUTOLOAD += "amvdec_ports"
KERNEL_MODULE_AUTOLOAD += "aml_hardware_dmx"
#KERNEL_MODULE_AUTOLOAD += "vpu"
KERNEL_MODULE_AUTOLOAD += "encoder"
