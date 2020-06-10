SUMMARY = "aml host tools"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

inherit native
include hosttools.inc

do_configure[noexec] = "1"
do_compile[noexec] = "1"

PR = "r1"

S= "${WORKDIR}/git"

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/kernel/dtbTool ${D}${bindir}
    install -m 0755 ${S}/kernel/mkbootimg.py ${D}${bindir}
}
FILES_${PN} = "${bindir}/*"
