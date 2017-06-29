inherit module

SUMMARY = "Amlogic media driver"
LICENSE = "GPLv2"

SRC_URI = "git://git.myamlogic.com/platform/hardware/amlogic/media_modules.git;nobranch=1"

SRCREV = "b9164398172ee6bbcdbc70c4ac1d87b450bdf13b"

MIRRORS_prepend += "git://git.myamlogic.com/platform/hardware/amlogic/media_modules.git git://git@openlinux.amlogic.com/yocto/platform/hardware/amlogic/media_modules.git;protocol=ssh; \n"

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

module_autoload_vh265 = "vh265"
module_autoload_vvc1 = "vvc1"
module_autoload_vh264_multi = "vh264_multi"
module_autoload_vmjpeg = "vmjpeg"
module_autoload_stream_input = "stream_input"
module_autoload_vh264_mvc = "vh264_mvc"
module_autoload_vmpeg4_multi = "vmpeg4_multi"
module_autoload_vmpeg4 = "vmpeg4"
module_autoload_vvp9 = "vvp9"
module_autoload_vh264_4k2k = "vh264_4k2k"
module_autoload_vh264 = "vh264"
module_autoload_firmware = "firmware"
module_autoload_vreal = "vreal"
module_autoload_decoder_common = "decoder_common"
module_autoload_media_clock = "media_clock"
module_autoload_encoder = "encoder"
module_autoload_vmjpeg_multi = "vmjpeg_multi"
module_autoload_vavs = "vavs"
module_autoload_vmpeg12 = "vmpeg12"
