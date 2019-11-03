inherit kernel
require linux-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/4.9:"
FILESEXTRAPATHS_prepend_aarch64 := "${THISDIR}/aarch64:"
FILESEXTRAPATHS_prepend_armv7a := "${THISDIR}/armv7a:"

KBRANCH = ""
SRC_URI = "git://git.myamlogic.com/kernel/common.git;branch=${KBRANCH};nobranch=1"

SRC_URI_append = " file://defconfig"
SRC_URI_append = " file://tm2_t962e2_ab311_drm.dts"
SRC_URI_append_llama = " file://tm2_t962e2_llama_drm.dts"

SRC_URI_append = " file://meson.scc \
            file://meson.cfg \
            file://meson-user-config.cfg \
            file://systemd.cfg \
            file://meson-user-patches.scc "

SRC_URI_append = " file://0001-volume-patch-from-lianlian.zhu.patch"
SRC_URI_append = " file://tm2_t962x3_ab301_drm.dts"
SRC_URI_append_llama = " file://mesontm2_llama.dtsi"
SRC_URI_append_llama = " file://mesontm2_t962x3_llama-panel.dtsi"
SRC_URI_append_llama = " file://tm2_t962x3_llama_drm.dts"
#SRC_URI_append = " file://0001-workaround-tee-disable-microCode-loading-by-optee.patch"



#v4l patches

#SRC_URI_append = " file://0001-increase-v4l-memory.patch"
SRC_URI_append_llama = " file://bluetooth_qca.cfg"
SRC_URI_append_llama = " file://0002-Add-support-for-audio-amp-tas5825-5766.patch"
SRC_URI_append_llama = " file://llama.cfg"

MIRRORS_prepend += "git://git.myamlogic.com/kernel/common.git git://git@openlinux.amlogic.com/yocto/kernel/common.git;protocol=ssh; \n"

LINUX_VERSION ?= "4.9.113"
LINUX_VERSION_EXTENSION ?= "-amlogic"

SRCREV ?="${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(mesong12b_*|mesong12a_*|mesongxl_*|mesontxlx_*|mesontm2_*)"

do_compile_prepend() {
    cp ${WORKDIR}/tm2_t962e2_ab311_drm.dts ${S}/arch/${ARCH}/boot/dts/amlogic
    cp ${WORKDIR}/tm2_t962x3_ab301_drm.dts ${S}/arch/${ARCH}/boot/dts/amlogic
}

do_compile_prepend_armv7a_llama() {
    cp ${WORKDIR}/tm2_t962x3_llama_drm.dts ${S}/arch/${ARCH}/boot/dts/amlogic
    cp ${WORKDIR}/mesontm2_llama.dtsi ${S}/arch/${ARCH}/boot/dts/amlogic
    cp ${WORKDIR}/mesontm2_t962x3_llama-panel.dtsi ${S}/arch/${ARCH}/boot/dts/amlogic
    cp ${WORKDIR}/tm2_t962e2_llama_drm.dts ${S}/arch/${ARCH}/boot/dts/amlogic
}
S = "${WORKDIR}/git"
