SUMMARY = "Meson init script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

SRC_URI = "git://${AML_GIT_ROOT}/yocto/rdk/prebuilt/vendor;protocol=${AML_GIT_PROTOCOL};branch=master"
SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"
PR = "r0"

S = "${WORKDIR}/git/"

do_install() {
        install -d ${D}/etc/tvconfig
        cd ${S}/etc/tvconfig
        for file in $(find -type f); do
            install -m 0644 -D ${file} ${D}/etc/tvconfig/${file}
        done
}

FILES_${PN} += " /etc/tvconfig/* "

PACKAGE_ARCH = "${MACHINE_ARCH}"
