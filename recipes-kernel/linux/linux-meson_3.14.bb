inherit kernel
require linux-meson.inc

KBRANCH = ""
SRC_URI = "git://git.myamlogic.com/kernel/common.git;branch=${KBRANCH};nobranch=1"

SRC_URI += "file://defconfig"

SRC_URI += "file://meson.scc \
            file://meson.cfg \
            file://meson-user-config.cfg \
            file://systemd.cfg \
            file://meson-user-patches.scc"

SRC_URI += "file://0001-Remove-git-hook-from-Makefile.patch  \
	    file://0002-fix-sdcard-to-dev-mmcblk0.patch     \
	    file://0003-fix-compilation-error-for-gcc6.patch.patch  \
	    file://0004-Enable-H264-CC-parser-on-905D.patch"

MIRRORS_prepend += "git://git.myamlogic.com/kernel/common.git git://git@openlinux.amlogic.com/yocto/kernel/common.git;protocol=ssh; \n"

LINUX_VERSION ?= "3.14.29"
LINUX_VERSION_EXTENSION ?= "-amlogic"

#SRCPV = "2016-08-18-26e194264c"
SRCREV="7d719f731967df130414f93c1cec77dab38c770a"

#PV = "${LINUX_VERSION}+git${SRCPV}"
PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_meson = "mesongxl_p212 mesongxl_p230"

#B = "${STAGING_KERNEL_BUILDDIR}"

S = "${WORKDIR}/git"
#S = "${WORKDIR}/arm-src-kernel-${SRCPV}"

