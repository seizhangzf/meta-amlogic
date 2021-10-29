DESCRIPTION = "aml mediahal sdk"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/mediahal_sdk;protocol=${AML_GIT_PROTOCOL};branch=linux-master"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/aml-patches/../multimedia/mediahal-sdk')}"

DEPENDS += "aml-audio-hal aml-audio-service libdrm-meson wayland"
RDEPENDS_${PN} += "aml-audio-service libdrm-meson"

#do_compile[noexec] = "1"

SRCREV ?= "${AUTOREV}"

PV = "git${SRCPV}"

S = "${WORKDIR}/git"

ARM_TARGET="arm.aapcs-linux.hard"
TA_TARGET="noarch"

EXTRA_OEMAKE="STAGING_DIR=${STAGING_DIR_TARGET} \
                 TARGET_DIR=${D} \
                 EXTRA_CFLAGS=-I${S}/prebuilt/${TA_TARGET}/include/ \
                 EXTRA_LDFLAGS=-L${S}/prebuilt/${ARM_TARGET}/ \
                               "
do_compile(){
    cd ${S}/example/AmTsPlayerExample
    oe_runmake
    cd ${S}/example/AmTsPlayerMultiExample
    oe_runmake
}

do_install() {
    install -d -m 0755 ${D}/usr/lib
    install -d -m 0755 ${D}/usr/include

    install -D -m 0644 ${S}/prebuilt/${TA_TARGET}/include/*.h ${D}/usr/include
    install -D -m 0644 ${S}/prebuilt/${ARM_TARGET}/libmediahal_resman.so ${D}/usr/lib
    install -D -m 0644 ${S}/prebuilt/${ARM_TARGET}/libmediahal_tsplayer.so ${D}/usr/lib
    install -D -m 0644 ${S}/prebuilt/${ARM_TARGET}/libmediahal_videodec.so ${D}/usr/lib
    install -D -m 0644 ${S}/prebuilt/${ARM_TARGET}/libmediahal_mediasync.so ${D}/usr/lib
    install -d -m 0755 ${D}/usr/bin
    install -D -m 0755 ${S}/example/AmTsPlayerExample/AmTsPlayerExample ${D}/usr/bin
    install -D -m 0755 ${S}/example/AmTsPlayerMultiExample/AmTsPlayerMultiExample ${D}/usr/bin
}

FILES_${PN} = "${libdir}/* ${includedir}/* /usr/bin/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
