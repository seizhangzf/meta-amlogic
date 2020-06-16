SUMMARY = "Meson init script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

SRC_URI = "git://${AML_GIT_ROOT}${AML_GIT_ROOT_YOCTO_SUFFIX}/rdk/prebuilt/vendor;protocol=${AML_GIT_PROTOCOL};branch=master"
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
        install -d ${D}/lib
        ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/lib/libdolbyms12.so
}

FILES_${PN} = " /etc/tvconfig/* /lib/*"
FILES_${PN}-dev = " "
PACKAGE_ARCH = "${MACHINE_ARCH}"
INSANE_SKIP_${PN} = "dev-so dev-elf"
