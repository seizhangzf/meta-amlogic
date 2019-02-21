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

SRC_URI += "file://0001-volume-patch-from-lianlian.zhu.patch"
#SRC_URI += "file://0002-Fix-dump_backtrace-caused-by-__might_sleep.patch"
#SRC_URI += "file://0003-Fix-amlvideo2-crash.patch"
SRC_URI += "file://0004-dts-enable-meson-drm.patch"
SRC_URI += "file://0005-use-old-partition-on-p230.patch"

DRM_SRC = "file://0028-linux-meson-Modification-for-DRM-backend.patch"
SRC_URI += " ${@bb.utils.contains("DISTRO_FEATURES", "drm", "${DRM_SRC}", " ", d)}"
#SRC_URI += "file://0033-For-abnormal-video-as-playing-4k-video-on-youtube-pc.patch"
SRC_URI += "file://0034-Porting-to-gxl_p212_1g_buildroot.dts.patch"
SRC_URI += "file://0035-tsync-expose-tsync_video_started-to-user-space.patch"

MIRRORS_prepend += "git://git@openlinux.amlogic.com/yocto/kernel/common.git;protocol=ssh git://git.myamlogic.com/kernel/common.git; \n"

LINUX_VERSION ?= "4.9.113"
LINUX_VERSION_EXTENSION ?= "-amlogic"

SRCREV="4e27896d9c550738640fcace31130d58eb97b9a3"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(mesong12a_u200|mesongxl_p230|mesongxl_p212)"

S = "${WORKDIR}/git"
