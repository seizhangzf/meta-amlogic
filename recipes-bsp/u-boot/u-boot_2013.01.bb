require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''

SRC_URI = "git://git.myamlogic.com/uboot.git;nobranch=1"

SRC_URI += "file://gxb_p200_v1.h.patch \
           file://gxb_p201_v1.h.patch \
           file://gxl_p212_v1.h.patch \
           file://gxm_q200_v1.h.patch \
           file://gxm_q201_v1.h.patch \
           file://bl32.img"

MIRRORS_prepend += "git://git.myamlogic.com/uboot.git git://git@openlinux.amlogic.com/yocto/uboot.git;protocol=ssh; \n"
SRCREV="494c3433d324fd694854e291f34406f5bce95f77"

S = "${WORKDIR}/git"

PR = "r1"
PV = "v2013.01+git${SRCREV}"

do_compile_prepend () {
    cp ${WORKDIR}/bl32.img ${S}/fip/gxl/
}
