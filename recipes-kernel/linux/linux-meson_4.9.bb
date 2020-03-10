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

SRC_URI_append = " file://0001-fix-uapi-struct.patch"
SRC_URI_append = " file://0002-increase-codec-memory.patch"
SRC_URI_append = " file://0003-workaround-tee-disable-microCode-loading-by-optee.patch"
SRC_URI_append = " file://0001-drm-fix-compile-error-1-1.patch "
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES','absystem','file://0001-to-support-absystem-and-remove-unnecessary-partition.patch','',d)}"

LINUX_VERSION ?= "4.9.113"
LINUX_VERSION_EXTENSION ?= "-amlogic"

PR = "r2"

SRCREV ?="${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(mesong12b_*|mesong12a_*|mesongxl_*|mesontxlx_*|mesontm2_*)"

S = "${WORKDIR}/git"
