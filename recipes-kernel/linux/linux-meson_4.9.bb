inherit kernel
require linux-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/4.9:"
FILESEXTRAPATHS_prepend_aarch64 := "${THISDIR}/aarch64:"
FILESEXTRAPATHS_prepend_armv7a := "${THISDIR}/armv7a:"
FILESEXTRAPATHS_prepend_sc2 := "${THISDIR}/sc2:"
FILESEXTRAPATHS_prepend_t5d := "${THISDIR}/t5d:"

KBRANCH = "amlogic-4.9-dev"
KBRANCH_sc2 = "amlogic-4.9-dev-q"
SRC_URI = "git://${AML_GIT_ROOT}/kernel/common.git;protocol=${AML_GIT_PROTOCOL};branch=${KBRANCH};"
SRC_URI_append = " file://defconfig"

SRC_URI_append = " file://meson.scc \
            file://meson.cfg \
            file://meson-user-config.cfg \
            file://systemd.cfg \
            file://logcat.cfg \
            file://meson-user-patches.scc "
#SRC_URI_append = " ${@get_patch_list('${THISDIR}/armv7a')}"

#For common patches
KDIR = "aml-4.9"
KDIR_sc2 = "aml-4.9-q"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/kernel/${KDIR}')}"

LINUX_VERSION ?= "4.9.113"
LINUX_VERSION_sc2 = "4.9.180"
LINUX_VERSION_EXTENSION ?= "-amlogic"

PR = "r2"

SRCREV ?="${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(mesong12b_*|mesong12a_*|mesongxl_*|mesontxlx_*|mesontm2_*|mesonsc2_*|mesont5d_*)"

S = "${WORKDIR}/git"
