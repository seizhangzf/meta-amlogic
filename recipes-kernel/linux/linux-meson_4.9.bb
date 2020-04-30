inherit kernel
require linux-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/4.9:"
FILESEXTRAPATHS_prepend_aarch64 := "${THISDIR}/aarch64:"
FILESEXTRAPATHS_prepend_armv7a := "${THISDIR}/armv7a:"

KBRANCH = "amlogic-4.9-dev"
SRC_URI = "git://${AML_GIT_ROOT}/kernel/common.git;protocol=${AML_GIT_PROTOCOL};branch=${KBRANCH};"

SRC_URI_append = " file://defconfig"

SRC_URI_append = " file://meson.scc \
            file://meson.cfg \
            file://meson-user-config.cfg \
            file://systemd.cfg \
            file://logcat.cfg \
            file://meson-user-patches.scc "

SRC_URI_append = " file://0001-Revert-cvbsout-add-cvbsout-DRM-support-1-1.patch"
SRC_URI_append = " file://0001-fix-uapi-struct.patch"
SRC_URI_append = " file://0002-increase-codec-memory.patch"
SRC_URI_append = " file://0003-drm-Buildroot-TM2-Suspend-WestonOS-can-t-work-after-.patch"
SRC_URI_append = " file://0004-codec_mm-fix-codec_mm-range-1-1.patch"
SRC_URI_append = " file://0005-secmem-add-secmem-driver-1-1.patch"
SRC_URI_append = " file://0006-uvm-add-dmabuf-wrap-vframe-mechanism-1-1.patch"
SRC_URI_append = " file://0007-drm-add-uvm-dmabuf-support-in-meson-gem-1-1.patch"
SRC_URI_append = " file://0011-drm-sync-vout-mode-with-meson_hdmi-1-1.patch "
SRC_URI_append = " file://0014-uvm-fix-uvm-secure-buf-map-crash-issue-1-1.patch "
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES','absystem','file://0013-support-AB-partition.patch','',d)}"
#This patch cant work with alsa
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES','alsa','','file://0015-audio-hal-support-for-g12a.patch ',d)}" 
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES','absystem','file://0014-AB-tv-partition.patch','',d)}"
SRC_URI_append = " file://0015-drm-add-second-video-overlay-plane-1-1.patch"
SRC_URI_append = " file://0016-drm-add-hdr-dv-support-1-1.patch"
SRC_URI_append = " file://0017-drm-add-video-overlay-fence-support-1-1.patch"
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES','absystem','file://0019-ab311-ab-partition.patch','',d)}"
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES','absystem','file://0020-ab301-ab-partition.patch','',d)}"
SRC_URI_append = " file://0020-sha-add-scpi-for-sha-check-during-suspend-2-2.patch"
SRC_URI_append = " file://0021-audio-set-arc-source-from-spdifA-when-earc-is-disabl.patch"


LINUX_VERSION ?= "4.9.113"
LINUX_VERSION_EXTENSION ?= "-amlogic"

PR = "r2"

SRCREV ?="${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(mesong12b_*|mesong12a_*|mesongxl_*|mesontxlx_*|mesontm2_*)"

S = "${WORKDIR}/git"
