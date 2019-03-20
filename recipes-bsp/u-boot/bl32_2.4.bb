FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SUMMARY = "Optee binary"
LICENSE = "CLOSED"

SRC_URI = "git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh;branch=tdk-v2.4-RDK-tvp"
#MIRRORS_prepend += "git://git.myamlogic.com/vendor/amlogic/tdk.git git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh; \n"
SRCREV = "ae8f171107a0458a9c01b8f710fc55188e667e9e"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${WORKDIR}/git"

PR = "r1"
PV = "v1.0+git${SRCREV}"

do_install() {
    mkdir -p ${D}${datadir}/bootloader/bl32/bin/
    cp -rf ${S}/secureos/* ${D}${datadir}/bootloader/bl32/bin/
}

FILES_${PN}-dev= "${datadir}/bootloader/bl32/bin/*"

