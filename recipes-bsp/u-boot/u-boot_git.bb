require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''

SRC_URI = "git://git.myamlogic.com/uboot.git;branch=next-2015-dev \
           file://gxb_p200_v1.h.patch \
           file://gxb_p201_v1.h.patch \
           file://gxl_p212_v1.h.patch \
           file://gxm_q200_v1.h.patch"

DEFAULT_PREFERENCE = "-1"
SRCREV="${AUTOREV}"

S = "${WORKDIR}/git"

PR = "r1"
PV = "v2013.01+git${SRCREV}"

