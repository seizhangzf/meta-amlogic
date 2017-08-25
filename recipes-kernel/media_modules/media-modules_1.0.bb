inherit module

SUMMARY = "Amlogic media driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "git://git.myamlogic.com/platform/hardware/amlogic/media_modules.git;nobranch=1"
SRC_URI += "file://COPYING.GPL"
SRC_URI += "file://0001-PD-146152-media_modules-merged-code-from-43177e6a-on.patch"

SRCREV = "8cbee189679f5c755e1e8a1fabd24cd2159cc7ae"

MIRRORS_prepend += "git://git.myamlogic.com/platform/hardware/amlogic/media_modules.git git://git@openlinux.amlogic.com/yocto/platform/hardware/amlogic/media_modules.git;protocol=ssh; \n"

do_configure[noexec] = "1"

MEDIA_MODULES_UCODE_BIN = "${S}/firmware/video_ucode.bin"

do_patch() {
    cd ${S}
    git apply -p1 < ../0001-PD-146152-media_modules-merged-code-from-43177e6a-on.patch
}

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
           CONFIG_AMLOGIC_MEDIA_VDEC_MPEG4=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_MPEG4_MULTI=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_VC1=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_H264=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_H264_MULTI=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_H264_MVC=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_H264_4K2K=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_H264_MVC=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_H265=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_VP9=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_MJPEG=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_MJPEG_MULTI=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_REAL=m \
           CONFIG_AMLOGIC_MEDIA_VDEC_AVS=m \
           CONFIG_AMLOGIC_MEDIA_VENC_H264=m \
           CONFIG_AMLOGIC_MEDIA_VECN_H265=m \
           "

S = "${WORKDIR}/git"
EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${S}/drivers" ${MEDIA_CONFIGS} modules'

KERNEL_MODULE_AUTOLOAD += "vh265"
KERNEL_MODULE_AUTOLOAD += "vvc1"
KERNEL_MODULE_AUTOLOAD += "vh264_multi"
KERNEL_MODULE_AUTOLOAD += "vmjpeg"
KERNEL_MODULE_AUTOLOAD += "stream_input"
KERNEL_MODULE_AUTOLOAD += "vh264_mvc"
KERNEL_MODULE_AUTOLOAD += "vmpeg4_multi"
KERNEL_MODULE_AUTOLOAD += "vmpeg4"
KERNEL_MODULE_AUTOLOAD += "vvp9"
KERNEL_MODULE_AUTOLOAD += "vh264_4k2k"
KERNEL_MODULE_AUTOLOAD += "vh264"
KERNEL_MODULE_AUTOLOAD += "firmware"
KERNEL_MODULE_AUTOLOAD += "vreal"
KERNEL_MODULE_AUTOLOAD += "decoder_common"
KERNEL_MODULE_AUTOLOAD += "media_clock"
KERNEL_MODULE_AUTOLOAD += "encoder"
KERNEL_MODULE_AUTOLOAD += "vmjpeg_multi"
KERNEL_MODULE_AUTOLOAD += "vavs"
KERNEL_MODULE_AUTOLOAD += "vmpeg12"
