inherit module

SUMMARY = "Qualcomm 6174 driver"
LICENSE = "GPLv2"

SRC_URI = "git://git.myamlogic.com/platform/hardware/wifi/qualcomm/drivers/qca6174.git;nobranch=1"
SRC_URI += "file://0001-fix-firmware-path.patch"

SRCREV ?= "${AUTOREV}"

MIRRORS_prepend += "git://git.myamlogic.com/platform/hardware/wifi/qualcomm/drivers/qca6174.git git://git@openlinux.amlogic.com/yocto/platform/hardware/wifi/qualcomm/drivers/qca6174.git;protocol=ssh; \n"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    WIFIDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/qca/wifi
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${WIFIDIR}
    install ${S}/AIO/rootfs-x86-android.build/lib/modules/wlan.ko ${WIFIDIR}
}

FILES_${PN} = "wlan.ko"
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${S}/AIO/build KERNELPATH=${STAGING_KERNEL_DIR} ARCH=${ARCH} CROSS_COMPILE=${CROSS_COMPILE}'
