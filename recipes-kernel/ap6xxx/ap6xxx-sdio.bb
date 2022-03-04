inherit module

SUMMARY = "Broadcom AP6xxx driver"
LICENSE = "GPLv2"
FILESEXTRAPATHS_prepend := "${THISDIR}/file:"

# Source
SRC_URI = "git://git.myamlogic.com/platform/hardware/wifi/broadcom/drivers/ap6xxx.git;protocol=git;branch=r-amlogic"

# Patch
SRC_URI += "file://0001-Enable-SDIO.patch"

#SRCREV = "e0599cf99ebac232e73d4c76aa7022f0db21921e"

SRCREV ?= "${AUTOREV}"

AP6XXX_KCONFIGS = "KCPPFLAGS='-DCONFIG_BCMDHD_FW_PATH=\"/etc/wifi/bcm/fw_bcmdhd.bin\" -DCONFIG_BCMDHD_NVRAM_PATH=\"/etc/wifi/bcm/nvram.txt\"'"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    WIFIDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/broadcom/wifi
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${WIFIDIR}
    install -m 0666 ${S}/bcmdhd.101.10.361.x/dhd.ko ${WIFIDIR}
#    FWDIR=${D}/etc/wifi/bcm/
#    mkdir -p ${FWDIR}
#    install -m 0666 ${WORKDIR}/clm_bcm43752a2_pcie_ag.blob ${FWDIR}
#    install -m 0666 ${WORKDIR}/fw_bcm43752a2_pcie_ag.bin ${FWDIR}
#    install -m 0666 ${WORKDIR}/fw_bcm43752a2_pcie_ag_apsta.bin ${FWDIR}
#    install -m 0666 ${WORKDIR}/nvram.txt ${FWDIR}
#    install -m 0666 ${WORKDIR}/BCM4362A2.hcd ${FWDIR}
}

do_compile_append() {
    oe_runmake -C ${STAGING_KERNEL_DIR} M="${S}/bcmdhd.101.10.361.x" ${AP6XXX_KCONFIGS} modules
}

FILES_${PN} = "dhd.ko"
# Header file provided by a separate package
DEPENDS += ""
FILES_${PN} += "/lib/firmware"
FILES_${PN} += "${sysconfdir}/wifi/*"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${S}/bcmdhd.101.10.361.x" ${AP6XXX_KCONFIGS} modules'