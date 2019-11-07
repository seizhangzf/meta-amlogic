require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.myamlogic.com/firmware/bin/bl2.git;branch=amlogic-dev;destsuffix=uboot-repo/bl2/bin;name=bl2"
SRC_URI_append = " git://git.myamlogic.com/firmware/bin/bl30.git;branch=amlogic-dev;destsuffix=uboot-repo/bl30/bin;name=bl30"
SRC_URI_append = " git://git.myamlogic.com/firmware/bin/bl31.git;branch=amlogic-dev;destsuffix=uboot-repo/bl31/bin;name=bl31"
SRC_URI_append = " git://git.myamlogic.com/firmware/bin/bl31.git;branch=amlogic-dev-1.3;destsuffix=uboot-repo/bl31_1.3/bin;name=bl31-1.3"
SRC_URI_append = " git://git.myamlogic.com/vendor/amlogic/tdk.git;branch=tdk-v2.4;destsuffix=uboot-repo/tdk;name=tdk"
SRC_URI_append = " git://git.myamlogic.com/uboot.git;branch=amlogic-dev;destsuffix=uboot-repo/bl33/v2015;name=bl33"
SRC_URI_append = " git://git.myamlogic.com/amlogic/tools/fip.git;branch=amlogic-dev;destsuffix=uboot-repo/fip;name=fip"

#patches
#SRC_URI_append = " file://0001-disable-dtbo-in-Linux.patch;patchdir=bl33/v2015"
SRC_URI_append = " file://0001-remove-hardcode-path.patch;patchdir=bl33/v2015"

do_configure[noexec] = "1"

MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl2.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl2.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl30.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl30.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl31.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl31.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/uboot.git git://git@openlinux.amlogic.com/yocto/uboot.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/amlogic/tools/fip.git git://git@openlinux.amlogic.com/yocto/amlogic/tools/fip.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/vendor/amlogic/tdk.git git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh; \n"

SRCREV_bl2 ?="${AUTOREV}"
SRCREV_bl30 ?="${AUTOREV}"
SRCREV_bl31 ?="${AUTOREV}"
SRCREV_bl31-1.3 ?="${AUTOREV}"
SRCREV_tdk ?="${AUTOREV}"
SRCREV_bl33 ?="${AUTOREV}"
SRCREV_fip ?="${AUTOREV}"

S = "${WORKDIR}/uboot-repo"
SRCREV_FORMAT = "bl2_bl30_bl31_bl31-1.3_tdk_bl33_fip"
PR = "r1"
PV = "v2015.01+git${SRCPV}"

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

PATH_append = ":${STAGING_DIR_NATIVE}/gcc-linaro-aarch64-none-elf/bin"
PATH_append_tm2 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
PATH_append_g12a = ":${STAGING_DIR_NATIVE}/gcc-arm-none-elf/bin"
DEPENDS_append = "gcc-linaro-aarch64-none-elf-native "
DEPENDS_append_tm2 = " riscv-none-gcc-native "
DEPENDS_append_g12a = " gcc-arm-none-eabi-native"

do_compile () {
    cp fip/mk .
    export BUILD_FOLDER=${S}/build/
    UBOOT_TYPE="${UBOOT_MACHINE}"
    if ${@bb.utils.contains('DISTRO_FEATURES','secure-u-boot','true','false',d)}; then
        mkdir -p ${S}/bl32/bin/
        cp -rf ${S}/tdk/secureos/* ${S}/bl32/bin/
        ./mk ${UBOOT_TYPE%_config} --bl32 bl32/bin/${BL32_SOC_FAMILY}/bl32.img
    else
        ./mk ${UBOOT_TYPE%_config}
    fi
    cp -rf build/* fip/
}

