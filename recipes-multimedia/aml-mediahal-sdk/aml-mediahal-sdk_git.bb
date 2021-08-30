DESCRIPTION = "aml mediahal sdk"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/mediahal_sdk;protocol=${AML_GIT_PROTOCOL};branch=linux-master"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/multimedia/mediahal-sdk')}"
SRC_URI_append = " ${@get_patch_list_with_path('${THISDIR}/amlogic')}"

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'amlogic-dvb', 'aml-audio-hal libevent aml-audio-service', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'amlogic-dvb', 'libevent', '', d)}"
RDEPENDS_${PN} += "aml-audio-service"

#do_compile[noexec] = "1"

SRCREV ?= "${AUTOREV}"

PATCHTOOL = "git"

PV = "git${SRCPV}"

S = "${WORKDIR}/git"

PATCHTOOL = "git"

ARM_TARGET="arm.aapcs-linux.hard"
TA_TARGET="noarch"

EXTRA_OEMAKE="STAGING_DIR=${STAGING_DIR_TARGET} \
                 TARGET_DIR=${D} \
                 EXTRA_CFLAGS=-I${S}/prebuilt/${TA_TARGET}/include/ \
                 EXTRA_LDFLAGS=-L${S}/prebuilt/${ARM_TARGET}/ \
                               "
do_compile(){
    if [ "${@bb.utils.contains("DISTRO_FEATURES", "amlogic-dvb", "yes", "no", d)}" = "yes"   ]; then
        cd ${S}/example/AmTsPlayerExample
        oe_runmake
    fi
}

do_install() {
    install -d -m 0644 ${D}/usr/lib
    install -d -m 0644 ${D}/usr/include

    install -D -m 0644 ${S}/prebuilt/${TA_TARGET}/include/*.h ${D}/usr/include
    install -D -m 0644 ${S}/prebuilt/${ARM_TARGET}/libmediahal_resman.so ${D}/usr/lib

    if [ "${@bb.utils.contains("DISTRO_FEATURES", "amlogic-dvb", "yes", "no", d)}" = "yes"   ]; then
        install -D -m 0644 ${S}/prebuilt/${ARM_TARGET}/libmediahal_tsplayer.so ${D}/usr/lib
        install -D -m 0644 ${S}/prebuilt/${ARM_TARGET}/libmediahal_videodec.so ${D}/usr/lib
        install -d -m 0644 ${D}/usr/bin
        install -D -m 0755 ${S}/example/AmTsPlayerExample/AmTsPlayerExample ${D}/usr/bin
    fi

}

FILES_${PN} = "${libdir}/* ${includedir}/* /usr/bin/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
