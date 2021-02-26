inherit module

SUMMARY = "Realtek 8723bu driver"
LICENSE = "GPLv2"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/wifi/realtek/drivers/8723bu.git;protocol=${AML_GIT_PROTOCOL};nobranch=1"

SRCREV = "48364a70b65daff53ca5600e5b1ffdf90a3d9aad"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    WIFIDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/realtek/wifi
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${WIFIDIR}
    install -m 0666 ${WORKDIR}/git/rtl8723BU/8723bu.ko ${WIFIDIR}
}

FILES_${PN} = "8723bu.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${WORKDIR}/git/rtl8723BU" modules'
