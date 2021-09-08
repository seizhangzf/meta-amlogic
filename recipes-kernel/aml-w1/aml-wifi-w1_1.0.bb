inherit module

SUMMARY = "amlogic aml_wifi driver"
LICENSE = "GPLv2"

#SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/wifi/amlogic/drivers/w1.git;protocol=${AML_GIT_PROTOCOL};branch=r-amlogic"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    WIFIDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/amlogic/wifi
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${WIFIDIR}
    install -m 0666 ${S}/project_w1/vmac/vlsicomm.ko ${WIFIDIR}
    install -m 0666 ${S}/project_w1/vmac/aml_sdio.ko ${WIFIDIR}

    mkdir -p ${D}/etc/wifi/w1/
    install -m 0644 ${S}/project_w1/vmac/aml_wifi_rf*.txt ${D}/etc/wifi/w1/
}

FILES_${PN} = "vlsicomm.ko aml_sdio.ko /etc/wifi/w1/*"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${S}/project_w1/vmac M=${S}/project_w1/vmac KERNELDIR=${STAGING_KERNEL_DIR} ARCH=${ARCH} CROSS_COMPILE=${CROSS_COMPILE}'
