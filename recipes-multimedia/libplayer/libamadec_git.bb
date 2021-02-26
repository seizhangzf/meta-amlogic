SUMMARY = "aml amadec"
LICENSE = "LGPL-2.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2f61b7eacf1021ca36600c8932d215b9"

DEPENDS = "bzip2 virtual/gettext libxml2 libamavutils"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'amlogic-dvb', 'alsa-lib aml-dvbaudioutils', '', d)}"
SRC_URI = "git://${AML_GIT_ROOT}/platform/packages/amlogic/LibPlayer.git;protocol=${AML_GIT_PROTOCOL};branch=buildroot-libplayer"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/multimedia/libplayer/src')}"

SRCREV ?="${AUTOREV}"
PV = "git${SRCPV}"

S = "${WORKDIR}/git"
do_configure[noexec] = "1"
inherit pkgconfig

EXTRA_OEMAKE = "LIBPLAYER_STAGING_DIR=${STAGING_DIR_TARGET} CROSS=${TARGET_PREFIX} GCC='${CC}' G++='${CXX}' LD='${CCLD}' TARGET_DIR=${D} STAGING_DIR=${D} DESTDIR=${D}"
LD_FLAGS = "TARGET_LDFLAGS=''"
LD_FLAGS_sc2 = "TARGET_LDFLAGS='-DDVB_AUDIO_SC2'"

do_compile () {
    if [ "${@bb.utils.contains("DISTRO_FEATURES", "amlogic-dvb", "yes", "no", d)}" = "yes"   ]; then
        cd ${S}/amadec
	    oe_runmake -j1 ${EXTRA_OEMAKE} ${LD_FLAGS} all
        #cd ${S}/amcodec
	    #oe_runmake -j1 ${EXTRA_OEMAKE} all
        #cd ${S}/amvdec
	    #oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libmad
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libadpcm
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libamr
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libape
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libcook
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libfaad
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libflac
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/liblpcm
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libraac
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libpcm
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        cd ${S}/audio_codec/libopus
	    oe_runmake -j1 ${EXTRA_OEMAKE} all
        fi
}

do_install () {

	install -d ${D}/usr/lib
	install -d ${D}${includedir}
	install -d ${D}${includedir}/amports
    if [ "${@bb.utils.contains("DISTRO_FEATURES", "amlogic-dvb", "yes", "no", d)}" = "yes"   ]; then
        install -m 0644 ${S}/amadec/include/*.h ${D}${includedir}
	install -m 0644 ${S}/amcodec/include/amports/*.h ${D}${includedir}/amports
        install -m 0644 -D ${S}/amadec/libamadec_hal.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libmad/libammad-aml.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libadpcm/libadpcm-aml.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libamr/libamr-aml.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libape/libape-aml.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libcook/libcook-aml.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libfaad/libfaad-aml.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libflac/libflac-aml.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/liblpcm/liblibpcm_wfd.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libraac/libraac-aml.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libpcm/libpcm-aml.so ${D}/usr/lib/
        install -m 0644 -D ${S}/audio_codec/libopus/libopus-aml.so ${D}/usr/lib/
    fi
}

FILES_${PN} = " /usr/lib/* "
FILES_${PN}-dev = "/usr/include/*"

INSANE_SKIP_${PN} = "dev-elf dev-so"
INSANE_SKIP_${PN}-dev = "dev-elf dev-so"
