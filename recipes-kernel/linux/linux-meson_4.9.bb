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
            file://meson-user-patches.scc "

SRC_URI_append = " file://0001-drm-add-osd-mif-config-for-afbc-and-un-afbc-1-1.patch "
SRC_URI_append = " file://0002-drm-Buildroot-TM2-Suspend-WestonOS-can-t-work-after-.patch "
SRC_URI_append = " file://0003-workaround-tee-disable-microCode-loading-by-optee.patch "
SRC_URI_append = " file://0004-vdin-for-support-DV-5-metadata-pkt-1-1.patch "
SRC_URI_append = " file://0005-hdmirx-add-api-for-vsif-packet-1-2.patch "
SRC_URI_append = " file://0006-vdin-transfer-vsif-info-at-event-call-back-2-2.patch "
SRC_URI_append = " file://0007-debug-tv16.patch "
SRC_URI_append = " file://0008-hdmirx-fix-dolbyvisoin-10-can-not-work-issue-1-1.patch "
#SRC_URI_append = " file://0009-drm-add-video-overlay-support-1-1.patch "

LINUX_VERSION ?= "4.9.113"
LINUX_VERSION_EXTENSION ?= "-amlogic"

SRCREV ?="${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(mesong12b_*|mesong12a_*|mesongxl_*|mesontxlx_*|mesontm2_*)"

S = "${WORKDIR}/git"
