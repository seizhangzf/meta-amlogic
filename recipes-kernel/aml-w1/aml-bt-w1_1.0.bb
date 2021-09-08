inherit module

SUMMARY = "amlogic aml_bt driver"
LICENSE = "GPLv2"

#SRC_URI = "git://${AML_GIT_ROOT}/hardware/amlogic/bluetooth.git;protocol=${AML_GIT_PROTOCOL};branch=r-amlogic"
SRC_URI_append = " file://a2dp_mode_cfg.txt"
SRC_URI_append = " file://aml_bt_rf.txt"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    BTDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/amlogic/bt
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${BTDIR}
    install -m 0666 ${S}/aml_bt/sdio_driver_bt/sdio_bt.ko ${BTDIR}

    mkdir -p ${D}/etc/bluetooth/w1
    install -m 0644 ${WORKDIR}/aml_bt_rf.txt ${D}/etc/bluetooth/w1/
    install -m 0644 ${WORKDIR}/a2dp_mode_cfg.txt ${D}/etc/bluetooth/w1/
}

FILES_${PN} = "sdio_bt.ko /etc/bluetooth/w1/*"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${S}/aml_bt/sdio_driver_bt M=${S}/aml_bt/sdio_driver_bt KERNEL_SRC=${STAGING_KERNEL_DIR} ARCH=${ARCH} CROSS_COMPILE=${CROSS_COMPILE}'
