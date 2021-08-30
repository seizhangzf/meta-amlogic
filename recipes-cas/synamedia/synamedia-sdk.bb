DESCRIPTION = "synamedia-sdk"
SECTION = "synamedia-sdk"
LICENSE = "CLOSE"
PV = "git${SRCPV}"
PR = "r0"

#Only enable it in OpenLinux
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'synamedia', 'git://${AML_GIT_ROOT_OP}/synamedia-sdk.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=master','', d)}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/synamedia/synamedia-sdk')}"

PN = 'synamedia-sdk'
SRCREV ?= "${AUTOREV}"
S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

CHIP_PATH = "s905c2eng"

do_install() {
    install -d -m 0644 ${D}/usr/lib
    install -D -m 0644 ${S}/nsk2hdi/${CHIP_PATH}/lib/*.so ${D}/usr/lib

    install -d -m 0644 ${D}/lib/teetz
    install -D -m 0644 ${S}/nsk2tee/${CHIP_PATH}/nsk_ta/*.ta ${D}/lib/teetz
    install -D -m 0644 ${S}/nsk2tee/${CHIP_PATH}/syna_ta/*.ta ${D}/lib/teetz
}

FILES_${PN} = "${libdir}/* /usr/lib/* /lib/teetz/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
