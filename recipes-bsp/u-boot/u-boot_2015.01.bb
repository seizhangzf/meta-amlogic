require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''

SRC_URI = "git://git.myamlogic.com/firmware/bin/bl2.git;nobranch=1;destsuffix=uboot-repo/bl2/bin;name=bl2"
SRC_URI += "git://git.myamlogic.com/firmware/bin/bl30.git;nobranch=1;destsuffix=uboot-repo/bl30/bin;name=bl30"
SRC_URI += "git://git.myamlogic.com/firmware/bin/bl31.git;nobranch=1;destsuffix=uboot-repo/bl31/bin;name=bl31"
SRC_URI += "git://git.myamlogic.com/firmware/bin/bl31.git;nobranch=1;destsuffix=uboot-repo/bl31_1.3/bin;name=bl31-1.3"
SRC_URI += "git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh;branch=tdk-v2.4-RDK-tvp;destsuffix=uboot-repo/tdk;name=tdk"
SRC_URI += "git://git.myamlogic.com/uboot.git;nobranch=1;destsuffix=uboot-repo/bl33;name=bl33"
SRC_URI += "git://git.myamlogic.com/amlogic/tools/fip.git;nobranch=1;destsuffix=uboot-repo/fip;name=fip"
SRC_URI += "file://0001-disable-dtbo-in-Linux.patch;patchdir=bl33"

do_configure[noexec] = "1"

MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl2.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl2.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl30.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl30.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl31.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl31.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/uboot.git git://git@openlinux.amlogic.com/yocto/uboot.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/amlogic/tools/fip.git git://git@openlinux.amlogic.com/yocto/amlogic/tools/fip.git;protocol=ssh; \n"

SRCREV_bl2="353a31d228dfcb6439304dfaf12ddb2886b602d6"
SRCREV_bl30="a862d0aaf2562a14ff919932942717fd19188942"
SRCREV_bl31="cfcf122480b2e8154df902df4c1844acf9bfe5fd"
SRCREV_bl31-1.3="192987dfaddc779724b3cd40515c29eeb393a81d"
SRCREV_tdk = "ae8f171107a0458a9c01b8f710fc55188e667e9e"
SRCREV_bl33="d8c242475d617be3cfeeda3452c4d2ec5ca6dae2"
SRCREV_fip="3111b84198e37a51135baa6b462805a230b76fee"

S = "${WORKDIR}/uboot-repo"

PR = "r1"
PV = "v2015.01+git${SRCREV_bl33}"

BL32_SOC_FAMILY = "gx"
BL32_SOC_FAMILY_axg = "axg"
BL32_SOC_FAMILY_g12a = "g12a"
BL32_SOC_FAMILY_g12b = "g12a"
BL32_SOC_FAMILY_gxl = "gx"
BL32_SOC_FAMILY_gxtvbb = "gx"
BL32_SOC_FAMILY_tl1 = "tl1"
BL32_SOC_FAMILY_tlhd = "gx"
BL32_SOC_FAMILY_txl = "gx"
BL32_SOC_FAMILY_txlx = "txlx"

do_compile () {
    cp fip/mk .
    export BUILD_FOLDER=${S}/build/
    mkdir -p ${S}/bl32/bin/
    cp -rf ${S}/tdk/secureos/* ${S}/bl32/bin/
    UBOOT_TYPE="${UBOOT_MACHINE}"
    ./mk ${UBOOT_TYPE%_config} --bl32 bl32/bin/${BL32_SOC_FAMILY}/bl32.img
    cp -rf build/* fip/
}

