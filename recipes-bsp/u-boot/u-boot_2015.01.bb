require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''

DEPENDS = "bl2 bl30 bl31 bl31-1.3 fip-native"
#SRC_URI = "git://git.myamlogic.com/uboot.git;nobranch=1"
SRC_URI = "git:///home/matthew.shyu/workspace2/rdkv-morty/amlogic/uboot/.git/;protocol=file;nobranch=1"

do_configure[noexec] = "1"

#MIRRORS_prepend += "git://git.myamlogic.com/uboot.git git://git@openlinux.amlogic.com/yocto/uboot.git;protocol=ssh; \n"
SRCREV="d8c242475d617be3cfeeda3452c4d2ec5ca6dae2"

S = "${WORKDIR}/git"

PR = "r1"
PV = "v2015.01+git${SRCREV}"

SOC_FAMILY = "gxl"
SOC_FAMILY_axg = "axg"
SOC_FAMILY_g12a = "g12a"
SOC_FAMILY_g12b = "g12b"
SOC_FAMILY_gxl = "gxl"
SOC_FAMILY_gxtvbb = "gxtvbb"
SOC_FAMILY_tl1 = "tl1"
SOC_FAMILY_tlhd = "tlhd"
SOC_FAMILY_txl = "txl"
SOC_FAMILY_txlx = "txlx"

UBOOT_TYPE = "gxl_p212_v1"
UBOOT_TYPE_p212 = "gxl_p212_v1"
UBOOT_TYPE_p230 = "gxl_p230_v1"
UBOOT_TYPE_u200 = "g12a_u200_v1"
UBOOT_TYPE_u212 = "g12a_u200_v1"

BL31 = "bl31"
BL31_axg = "bl31_1.3"
BL31_g12a = "bl31_1.3"
BL31_g12b = "bl31_1.3"
BL31_tl1 = "bl31_1.3"
BL31_txhd = "bl31_1.3"
BL31_txlx = "bl31_1.3"

do_compile () {
    export UBOOT_SRC_FOLDER=${S}
    mkdir -p BUILD_FOLDER=${S}/fip/
    export BUILD_FOLDER=${S}/fip/
    mk ${UBOOT_TYPE} --bl2 ${STAGING_DIR_TARGET}${datadir}/bootloader/bl2/bin/${SOC_FAMILY}/bl2.bin  --bl30 ${STAGING_DIR_TARGET}${datadir}/bootloader/bl30/bin/${SOC_FAMILY}/bl30.bin --bl31 ${STAGING_DIR_TARGET}${datadir}/bootloader/${BL31}/bin/${SOC_FAMILY}/bl31.img
#--bl32 ${STAGING_DIR_TARGET}${datadir}/bootloader/bl32/${SOC_FAMILY}/bl32.bin
}

