SUMMARY = "Meson init script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
SRC_URI = "file://vendor/"

PR = "r0"

S = "${WORKDIR}"

do_install() {
        install -d ${D}/etc/tvconfig
        cd ${WORKDIR}/vendor/etc/tvconfig
        for file in $(find -type f); do
            install -m 0644 -D ${file} ${D}/etc/tvconfig/${file}
        done
}

FILES_${PN} += " /etc/tvconfig/* "

PACKAGE_ARCH = "${MACHINE_ARCH}"
