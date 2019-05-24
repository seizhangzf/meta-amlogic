inherit kernel
require linux-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/4.9:"

KBRANCH = ""
SRC_URI = "git://git.myamlogic.com/kernel/common.git;branch=${KBRANCH};nobranch=1"

SRC_URI += "file://defconfig"
SRC_URI += "file://tm2_t962e2_ab311_drm.dts"
SRC_URI += "file://tm2_t962e2_sky_drm.dts"

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
SRC_URI += "file://0006-dts-include-meson_drm.dtsi-in-962e-r321-dts.patch"

DRM_SRC = "file://0028-linux-meson-Modification-for-DRM-backend.patch \
           file://0029-dts-Add-drm-backend-for-txlx-t962e-r321.patch \
           file://0038-linux-meson-Enable-drm-backend-for-gxl_p212_1g_build.patch \
           file://0039-drm-workaround-for-1080p-scale-to-4k-1-1.patch \
           "
SRC_URI += " ${@bb.utils.contains("DISTRO_FEATURES", "drm", "${DRM_SRC}", " ", d)}"
#SRC_URI += "file://0033-For-abnormal-video-as-playing-4k-video-on-youtube-pc.patch"
SRC_URI += "file://0034-Porting-to-gxl_p212_1g_buildroot.dts.patch"
SRC_URI += "file://0035-tsync-expose-tsync_video_started-to-user-space.patch"

MIRRORS_prepend += "git://git.myamlogic.com/kernel/common.git git://git@openlinux.amlogic.com/yocto/kernel/common.git;protocol=ssh; \n"

LINUX_VERSION ?= "4.9.113"
LINUX_VERSION_EXTENSION ?= "-amlogic"

SRCREV="2d4cc31d6f8e8923d9f9a0997dc0260927bdb503"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(mesong12b_*|mesong12a_*|mesongxl_*|mesontxlx_*|mesontm2_*)"

do_compile_prepend() {
    cp ${WORKDIR}/tm2_t962e2_ab311_drm.dts ${S}/arch/arm64/boot/dts/amlogic
    cp ${WORKDIR}/tm2_t962e2_sky_drm.dts ${S}/arch/arm64/boot/dts/amlogic
}

S = "${WORKDIR}/git"
