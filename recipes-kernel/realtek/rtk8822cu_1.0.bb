inherit module

SUMMARY = "Realtek 88x2cu driver"
LICENSE = "GPLv2"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/wifi/realtek/drivers/8822cu.git;protocol=${AML_GIT_PROTOCOL};branch=r-amlogic"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    WIFIDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/realtek/wifi
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${WIFIDIR}
    install -m 0666 ${S}/rtl88x2CU/88x2cu.ko ${WIFIDIR}
}

FILES_${PN} = "88x2cu.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${S}/rtl88x2CU M=${S}/rtl88x2CU KERNEL_SRC=${STAGING_KERNEL_DIR} KERNELPATH=${STAGING_KERNEL_DIR} ARCH=${ARCH} CROSS_COMPILE=${CROSS_COMPILE}'
