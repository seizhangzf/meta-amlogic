inherit module

SUMMARY = "Realtek bluetooth driver"
LICENSE = "GPLv2"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/realtek/bluetooth.git;protocol=${AML_GIT_PROTOCOL};branch=master;nobranch=1"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    BT_MODULE_DIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/realtek/bt
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${BT_MODULE_DIR}
    install -m 0666 ${S}/uart_driver/hci_uart.ko ${BT_MODULE_DIR}/rtk_btuart.ko

    mkdir -p ${D}/lib/firmware/rtlbt
    install -D -m 0644 ${S}/fw/* ${D}/lib/firmware/rtlbt
}

FILES_${PN} = "rtk_btusb.ko /lib/firmware/rtlbt/*"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${S}/uart_driver M=${S}/uart_driver KERNEL_SRC=${STAGING_KERNEL_DIR} KERNELPATH=${STAGING_KERNEL_DIR} ARCH=${ARCH} CROSS_COMPILE=${CROSS_COMPILE}'
