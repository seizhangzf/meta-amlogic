inherit kernel
require linux-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/4.9:"

KBRANCH = ""
SRC_URI = "git://git@openlinux.amlogic.com/yocto/kernel/common.git;protocol=ssh;branch=${KBRANCH};nobranch=1"

SRC_URI += "file://defconfig"

SRC_URI += "file://meson.scc \
            file://meson.cfg \
            file://meson-user-config.cfg \
            file://systemd.cfg \
            file://meson-user-patches.scc"

SRC_URI += "file://0004-volume-patch-from-lianlian.zhu.patch"
SRC_URI += "file://0005-Fix-dump_backtrace-caused-by-__might_sleep.patch"
SRC_URI += "file://0009-media-merged-code-from-43177e6a-on-the-amlogic-3.14-.patch"
SRC_URI += "file://0010-Fix-amlvideo2-crash.patch"
SRC_URI += "file://0011-dts-enable-meson-drm.patch"

MIRRORS_prepend += "git://git@openlinux.amlogic.com/yocto/kernel/common.git;protocol=ssh git://git.myamlogic.com/kernel/common.git; \n"

LINUX_VERSION ?= "4.9.40"
LINUX_VERSION_EXTENSION ?= "-amlogic"

SRCREV="e304d1573e31896d66ec41d89f0bf0a5725a664c"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_meson = "mesongxl_p212 mesongxl_p230"

S = "${WORKDIR}/git"
