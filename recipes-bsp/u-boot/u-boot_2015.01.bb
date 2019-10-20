require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.myamlogic.com/firmware/bin/bl2.git;nobranch=1;destsuffix=uboot-repo/bl2/bin;name=bl2"
SRC_URI_append = " git://git.myamlogic.com/firmware/bin/bl30.git;nobranch=1;destsuffix=uboot-repo/bl30/bin;name=bl30"
SRC_URI_append = " git://git.myamlogic.com/firmware/bin/bl31.git;nobranch=1;destsuffix=uboot-repo/bl31/bin;name=bl31"
SRC_URI_append = " git://git.myamlogic.com/firmware/bin/bl31.git;nobranch=1;destsuffix=uboot-repo/bl31_1.3/bin;name=bl31-1.3"
SRC_URI_append = " git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh;branch=tdk-v2.4-RDK-tvp-20190423;destsuffix=uboot-repo/tdk;name=tdk"
SRC_URI_append = " git://git.myamlogic.com/uboot.git;nobranch=1;destsuffix=uboot-repo/bl33;name=bl33"
SRC_URI_append = " git://git.myamlogic.com/amlogic/tools/fip.git;nobranch=1;destsuffix=uboot-repo/fip;name=fip"
SRC_URI_append = " file://0001-disable-dtbo-in-Linux.patch;patchdir=bl33"
SRC_URI_append = " file://0002-lpddr4-timing.patch;patchdir=bl33"
SRC_URI_append = " file://0001-remove-hardcode-path.patch;patchdir=bl33"
SRC_URI_append_llama = " file://0001-Adjust-DDR-timing-for-LPDDR4.patch;patchdir=bl33"
SRC_URI_append_llama = " file://0002-Pull-high-GPIOAO_6-to-power-up-AMP.patch;patchdir=bl33"
do_configure[noexec] = "1"

MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl2.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl2.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl30.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl30.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl31.git git://git@openlinux.amlogic.com/yocto/firmware/bin/bl31.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/uboot.git git://git@openlinux.amlogic.com/yocto/uboot.git;protocol=ssh; \n"
MIRRORS_prepend += "git://git.myamlogic.com/amlogic/tools/fip.git git://git@openlinux.amlogic.com/yocto/amlogic/tools/fip.git;protocol=ssh; \n"

SRCREV_bl2="9a082fa86ae3c3c13a6dc07e027868caafc3e34a"
SRCREV_bl30="b8055d4fdb9f1b7de872c311564ce48df5468fd7"
SRCREV_bl31="9801bc28415100223fb280469674adc02fe733d5"
SRCREV_bl31-1.3="f60682a0748ae3a05adeec54527f48d2cd499396"
SRCREV_tdk = "f08d734bb2c56c4b8cc00edfb4d6e66fc9efd1fa"
SRCREV_bl33="2fc7a22b503d57f1b347876b8851d26fbbfdd097"
SRCREV_fip="675f6a8a96621d4d4f4ef5193972f3eae6124bfb"

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

PATH_append = ":${STAGING_DIR_NATIVE}/gcc-linaro-aarch64-none-elf/bin"
PATH_append_tm2 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
PATH_append_g12a = ":${STAGING_DIR_NATIVE}/gcc-arm-none-elf/bin"
DEPENDS_append = "gcc-linaro-aarch64-none-elf-native "
DEPENDS_append_tm2 = " riscv-none-gcc-native "
DEPENDS_append_g12a = " gcc-arm-none-eabi-native"

do_compile () {
    cp fip/mk .
    export BUILD_FOLDER=${S}/build/
    mkdir -p ${S}/bl32/bin/
    cp -rf ${S}/tdk/secureos/* ${S}/bl32/bin/
    UBOOT_TYPE="${UBOOT_MACHINE}"
    ./mk ${UBOOT_TYPE%_config} --bl32 bl32/bin/${BL32_SOC_FAMILY}/bl32.img
    cp -rf build/* fip/
}

