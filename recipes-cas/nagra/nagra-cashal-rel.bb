DESCRIPTION = "nagra-cashal-rel"
SECTION = "nagra-cashal-rel"
LICENSE = "CLOSE"
PV = "git${SRCPV}"
PR = "r0"

#Only enable it in OpenLinux
#SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'nagra', 'git://${AML_GIT_ROOT_OP}/nagra-cashal-rel.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=projects/openlinux/v3.0-rdk','', d)}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/aml-patches/../vendor/nagra/nagra-cashal-rel')}"

PN = 'nagra-cashal-rel'
SRCREV ?= "${AUTOREV}"
S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

do_install() {
    install -d -m 0644 ${D}/usr/lib
    install -D -m 0644 ${S}/libnv_dvb.so ${D}/usr/lib

    install -d -m 0644 ${D}/etc/cas/nagra
    install -D -m 0644 ${S}/config/nagra_hal.conf ${D}/etc/cas/nagra/
}

FILES_${PN} = "${libdir}/* /usr/lib/* /etc/cas/nagra/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
