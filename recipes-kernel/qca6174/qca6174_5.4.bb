inherit module

SUMMARY = "Qualcomm 6174 driver"
LICENSE = "GPLv2"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/wifi/qualcomm/drivers/qca6174.git;protocol=${AML_GIT_PROTOCOL};branch=o-amlogic"
#SRC_URI += "file://0001-fix-firmware-path.patch"
SRC_URI += "file://nvm_tlv_3.2.bin"
SRC_URI += "file://rampatch_tlv_3.2.tlv"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/hardware/aml-4.9/wifi/qualcomm/drivers/qca6174')}"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

COMPATIBLE_MACHINE="(mesonsc2_5.4*|mesons4*)"

do_install() {
    WIFIDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/qca/wifi
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${WIFIDIR}
    install ${S}/AIO/rootfs-x86-android.build/lib/modules/wlan.ko ${WIFIDIR}
    install -d ${D}/lib/firmware/
#install -D -m 0644 ${WORKDIR}/nvm_tlv_3.2.bin ${D}/lib/firmware/
#    install -D -m 0644 ${WORKDIR}/rampatch_tlv_3.2.tlv ${D}/lib/firmware/
}

FILES_${PN} = "wlan.ko"
DEPENDS += ""
FILES_${PN} += "/lib/firmware"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${S}/AIO/build M=${S}/AIO/build KERNEL_SRC=${STAGING_KERNEL_DIR} KERNELPATH=${STAGING_KERNEL_DIR} ARCH=${ARCH} CROSS_COMPILE=${CROSS_COMPILE}'
