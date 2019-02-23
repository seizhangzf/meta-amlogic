inherit module

SUMMARY = "Realtek 8189es driver"
LICENSE = "GPLv2"

SRC_URI = "git://git.myamlogic.com/platform/hardware/wifi/realtek/drivers/8189es.git;branch=m-amlogic-openlinux-20160907;nobranch=1"

SRCREV = "5a59235d2b69e774a58e0139dfd558170b866f9a"

MIRRORS_prepend += "git://git.myamlogic.com/platform/hardware/wifi/realtek/drivers/8189es.git git://git@openlinux.amlogic.com/yocto/platform/hardware/wifi/realtek/drivers/8189es.git;protocol=ssh; \n"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    WIFIDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/realtek/wifi
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${WIFIDIR}
    install -m 0666 ${WORKDIR}/git/rtl8189ES/8189es.ko ${WIFIDIR}
}

FILES_${PN} = "8189es.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${WORKDIR}/git/rtl8189ES" modules'
