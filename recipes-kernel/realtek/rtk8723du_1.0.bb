inherit module

SUMMARY = "Realtek 8723du driver"
LICENSE = "GPLv2"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/wifi/realtek/drivers/8723du.git;protocol=${AML_GIT_PROTOCOL};branch=r-amlogic;nobranch=1"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    WIFIDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/realtek/wifi
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${WIFIDIR}
    install -m 0666 ${S}/rtl8723DU/8723du.ko ${WIFIDIR}
}

FILES_${PN} = "8723du.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${S}/rtl8723DU M=${S}/rtl8723DU KERNEL_SRC=${STAGING_KERNEL_DIR} KERNELPATH=${STAGING_KERNEL_DIR} ARCH=${ARCH} CROSS_COMPILE=${CROSS_COMPILE}'
