inherit kernel
require linux-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/4.9:"

KBRANCH = ""
SRC_URI = "git://git.myamlogic.com/kernel/common.git;branch=${KBRANCH};nobranch=1"

SRC_URI += "file://defconfig"

SRC_URI += "file://meson.scc \
            file://meson.cfg \
            file://meson-user-config.cfg \
            file://systemd.cfg \
            file://meson-user-patches.scc"

SRC_URI += "\
            file://0001-dts-add-p230-config.patch \
            file://0002-amlvideo2-fix-screen-capture-funciton.patch \
            file://0003-amvideo-crash.patch \
"

SRC_URI += "file://0004-volume-patch-from-lianlian.zhu.patch"
SRC_URI += "file://0005-Fix-dump_backtrace-caused-by-__might_sleep.patch"
SRC_URI += "file://0006-audio-audio-info-for-audio-license-query.patch"
SRC_URI += "file://0007-Add-audio-info-into-dts.patch"

MIRRORS_prepend += "git://git.myamlogic.com/kernel/common.git git://git@openlinux.amlogic.com/yocto/kernel/common.git;protocol=ssh; \n"

LINUX_VERSION ?= "4.9.30"
LINUX_VERSION_EXTENSION ?= "-amlogic"

SRCREV="a0c464d95c00e0601a30595dbf014cb38e82356d"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_meson = "mesongxl_p212 mesongxl_p230"

S = "${WORKDIR}/git"
