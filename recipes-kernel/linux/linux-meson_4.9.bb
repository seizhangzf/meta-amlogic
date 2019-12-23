inherit kernel
require linux-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/4.9:"
FILESEXTRAPATHS_prepend_aarch64 := "${THISDIR}/aarch64:"
FILESEXTRAPATHS_prepend_armv7a := "${THISDIR}/armv7a:"

KBRANCH = "amlogic-4.9-dev"
SRC_URI = "git://${AML_GIT_ROOT}/kernel/common.git;protocol=${AML_GIT_PROTOCOL};branch=${KBRANCH};"

SRC_URI_append = " file://defconfig"
#SRC_URI_append = " file://tm2_t962e2_ab311_drm.dts"
#SRC_URI_append = " file://tm2_t962x3_ab301_drm.dts"
#SRC_URI_append = " file://mesontm2_drm.dtsi"

SRC_URI_append = " file://meson.scc \
            file://meson.cfg \
            file://meson-user-config.cfg \
            file://systemd.cfg \
            file://meson-user-patches.scc "

#SRC_URI_append = " file://0001-volume-patch-from-lianlian.zhu.patch"
#SRC_URI_append = " file://0001-drm-add-4k-scaler-support-in-osd-1-1.patch"


LINUX_VERSION ?= "4.9.113"
LINUX_VERSION_EXTENSION ?= "-amlogic"

SRCREV ?="${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(mesong12b_*|mesong12a_*|mesongxl_*|mesontxlx_*|mesontm2_*)"

#do_compile_prepend() {
#    cp ${WORKDIR}/tm2_t962e2_ab311_drm.dts ${S}/arch/${ARCH}/boot/dts/amlogic
#    cp ${WORKDIR}/tm2_t962x3_ab301_drm.dts ${S}/arch/${ARCH}/boot/dts/amlogic
#    cp ${WORKDIR}/mesontm2_drm.dtsi ${S}/arch/${ARCH}/boot/dts/amlogic
#}

S = "${WORKDIR}/git"
