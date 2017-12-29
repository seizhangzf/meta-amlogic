inherit module

SUMMARY = "Broadcom AP6xxx driver"
LICENSE = "GPLv2"

SRC_URI = "git://git.myamlogic.com/platform/hardware/wifi/broadcom/drivers/ap6xxx.git;nobranch=1"

SRCREV = "a6bb23fd533f71ed2360fc1e38d5a20f172c2b38"

MIRRORS_prepend += "git://git.myamlogic.com/platform/hardware/wifi/broadcom/drivers/ap6xxx.git git://git@openlinux.amlogic.com/yocto/platform/hardware/wifi/broadcom/drivers/ap6xxx.git;protocol=ssh; \n"

AP6XXX_KCONFIGS = "KCPPFLAGS='-DCONFIG_BCMDHD_FW_PATH=\"/etc/wifi/fw_bcmdhd.bin\" -DCONFIG_BCMDHD_NVRAM_PATH=\"/etc/wifi/nvram.txt\"'"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    WIFIDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/broadcom/wifi
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${WIFIDIR}
    install -m 0666 ${S}/bcmdhd.1.363.59.144.x.cn/dhd.ko ${WIFIDIR}
}

do_compile_append() {
    oe_runmake -C ${STAGING_KERNEL_DIR} M="${S}/bcmdhd.1.363.59.144.x.cn" ${AP6XXX_KCONFIGS} modules
}

FILES_${PN} = "dhd.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${S}/bcmdhd.1.363.59.144.x.cn" ${AP6XXX_KCONFIGS} modules'
