inherit kernel
require linux-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/4.9:"

KBRANCH = ""
#SRC_URI = "git://git.myamlogic.com/kernel/common.git;branch=${KBRANCH};nobranch=1"
SRC_URI = "git:///home/matthew.shyu/workspace2/rdkv2/common/.git;branch=${KBRANCH};nobranch=1;protocol=file"

SRC_URI += "file://defconfig"

SRC_URI += "file://meson.scc \
            file://meson.cfg \
            file://meson-user-config.cfg \
            file://systemd.cfg \
            file://meson-user-patches.scc"

#SRC_URI += "file://0002-fix-sdcard-to-dev-mmcblk0.patch"

#SRC_URI += "file://0001-volume-patch-from-lianlian.zhu.patch"

MIRRORS_prepend += "git://git.myamlogic.com/kernel/common.git git://git@openlinux.amlogic.com/yocto/kernel/common.git;protocol=ssh; \n"

LINUX_VERSION ?= "4.9.30"
LINUX_VERSION_EXTENSION ?= "-amlogic"

SRCREV="211a0879f867725ad8f923af69374d25e471e14d"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_meson = "mesongxl_p212 mesongxl_p230"

S = "${WORKDIR}/git"
