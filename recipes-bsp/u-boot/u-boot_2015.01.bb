require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''

SRC_URI = "git://git.myamlogic.com/firmware/bin/bl2.git;nobranch=1;destsuffix=uboot-repo/bl2/bin;name=bl2"
SRC_URI += "git://git.myamlogic.com/firmware/bin/bl30.git;nobranch=1;destsuffix=uboot-repo/bl30/bin;name=bl30"
SRC_URI += "git://git.myamlogic.com/firmware/bin/bl31.git;nobranch=1;destsuffix=uboot-repo/bl31/bin;name=bl31"
SRC_URI += "git://git.myamlogic.com/firmware/bin/bl31.git;nobranch=1;destsuffix=uboot-repo/bl31_1.3/bin;name=bl31-1.3"
SRC_URI += "git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh;branch=tdk-v2.4-RDK-tvp-20190423;destsuffix=uboot-repo/tdk;name=tdk"
SRC_URI += "git://git.myamlogic.com/uboot.git;nobranch=1;destsuffix=uboot-repo/bl33;name=bl33"
SRC_URI += "git://git.myamlogic.com/amlogic/tools/fip.git;nobranch=1;destsuffix=uboot-repo/fip;name=fip"
SRC_URI += "file://0001-disable-dtbo-in-Linux.patch;patchdir=bl33"

do_configure[noexec] = "1"

MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl2.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl2.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl30.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl30.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl31.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl31.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/uboot.git git://git@openlinux.amlogic.com/yocto/uboot.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/amlogic/tools/fip.git git://git@openlinux.amlogic.com/yocto/amlogic/tools/fip.git;protocol=ssh; \n"

SRCREV_bl2="11a14e0870c0800d7fa1e3af4f5a64214482708d"
SRCREV_bl30="637ff11f916216ece0c9f0264db6cf4599cea41b"
SRCREV_bl31="2160d7d872c9f3a4fea99260fb43bb2fd5b56581"
SRCREV_bl31-1.3="14e3e1dc56713b37b1d08503d83e47afec156007"
SRCREV_tdk = "63ac65d94f5aa6926f01f547a0c2fb74b2bcb023"
SRCREV_bl33="fd437675f1badaec2688651644bbdca5e8b9266a"
SRCREV_fip="ba7d2d547cd16d20b7d5c3b31899170164fe6850"

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
BL32_SOC_FAMILY_sm2 = "g12a"
BL32_SOC_FAMILY_tm2 = "tl1"

do_compile () {
    cp fip/mk .
    export BUILD_FOLDER=${S}/build/
    mkdir -p ${S}/bl32/bin/
    cp -rf ${S}/tdk/secureos/* ${S}/bl32/bin/
    UBOOT_TYPE="${UBOOT_MACHINE}"
    ./mk ${UBOOT_TYPE%_config} --bl32 bl32/bin/${BL32_SOC_FAMILY}/bl32.img
    cp -rf build/* fip/
}

