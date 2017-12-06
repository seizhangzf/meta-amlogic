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

DRM_SRC = "file://0012-Revert-android-port-fence-driver-from-kernel-4.4-1-7.patch \
           file://0013-osd-use-new-fence-api.-3-7.patch \
           file://0014-osd-init-osd-output-pt-value-to-1-and-fix-osd-buf-hn.patch \
           file://0015-osd-move-alloc-buffer-to-osd_mmap-for-save-memory-in.patch \
           file://0016-osd-use-the-old-fence-close-way-3-3.patch \
           file://0017-osd-clean-up-deadcode.patch \
           file://0018-osd-fix-move-cursor-caused-android-O-reboot-issue.patch \
           file://0019-osd-merge-3.14-osd-fix-to-4.9.patch \
           file://0020-osd-osd-display-error-when-work-in-1080i-and-cvbs.patch \
           file://0021-osd-null-pointer-risk-protect.patch \
           file://0022-osd-fixed-smem_start-error-3-3.patch \
           file://0023-Revert-osd-fixed-smem_start-error-3-3.patch \
           file://0024-Partial-merge-of-905253988f3cd83d1b956cad8d5e1ca6423.patch \
           file://0025-Partial-merge-of-afcba67e2683cff16cfb45a87f66dc4ef0f.patch \
           file://0026-Merge-PATCH-drm-add-amlogic-KMS-implement.patch \
           file://0027-Merge-64fb25d6ce999bbbb4d4936afd5f3b8ad2fb7966.patch \
           file://0028-Modify-gxl_p230_2g.dts-for-DRM-backend.patch \
           file://0029-osd_drm-fix-osd-drm-blank-function-not-work.patch \
           file://0030-drm-add-fbdev-emulate-implement.patch \
           file://0031-drm-prune-drm-implement-if-using-osd.patch \
           file://0032-for-wpe-video-playing.patch \
           file://0033-For-abnormal-video-as-playing-4k-video-on-youtube-pc.patch \
           "

SRC_URI += " ${@bb.utils.contains("DISTRO_FEATURES", "drm", "${DRM_SRC}", " ", d)}"

MIRRORS_prepend += "git://git@openlinux.amlogic.com/yocto/kernel/common.git;protocol=ssh git://git.myamlogic.com/kernel/common.git; \n"

LINUX_VERSION ?= "4.9.40"
LINUX_VERSION_EXTENSION ?= "-amlogic"

SRCREV="e304d1573e31896d66ec41d89f0bf0a5725a664c"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_meson = "mesongxl_p212 mesongxl_p230"

S = "${WORKDIR}/git"
