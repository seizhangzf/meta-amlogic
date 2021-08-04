require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019/bl33/v2019:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019/bl2/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019/bl30/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019/bl30/src_ao:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019/bl31/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019/bl31_1.3/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019/bl32_3.8/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019/fip:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

EXTRA_OEMAKE = ''
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://${AML_GIT_ROOT}/firmware/bin/bl2.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl2/bin;name=bl2"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl30.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl30/bin;name=bl30"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/aocpu.git;protocol=${AML_GIT_PROTOCOL};branch=projects/amlogic-dev;destsuffix=uboot-repo/bl30/src_ao;name=src-ao"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl31.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl31/bin;name=bl31"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl31.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev-1.3;destsuffix=uboot-repo/bl31_1.3/bin;name=bl31-1.3"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl32.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev-3.8.0;destsuffix=uboot-repo/bl32_3.8/bin;name=bl32-3.8"
SRC_URI_append = " git://${AML_GIT_ROOT}/uboot.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev-2019;destsuffix=uboot-repo/bl33/v2019;name=bl33"
SRC_URI_append = " git://${AML_GIT_ROOT}/amlogic/tools/fip.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/fip;name=fip"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl40/dummy.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl40/bin;name=bl40"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/templates.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/soc/templates;name=soc-templates"

PATCHTOOL="git"

#For common patch
SRC_URI_append = " ${@get_patch_list_with_path('${THISDIR}/amlogic/bl33/v2019', 'bl33/v2019')}"
SRC_URI_append = " ${@get_patch_list_with_path('${THISDIR}/amlogic/fip', 'fip')}"
#can not patch bl binaries due to permission issue bl binary repos

do_configure[noexec] = "1"

SRCREV_pn-u-boot ?="${AUTOREV}"
SRCREV_bl2 ?="${AUTOREV}"
SRCREV_bl30 ?="${AUTOREV}"
SRCREV_src-ao ?="${AUTOREV}"
SRCREV_bl31 ?="${AUTOREV}"
SRCREV_bl31-1.3 ?="${AUTOREV}"
SRCREV_bl32-3.8 ?="${AUTOREV}"
SRCREV_bl33 ?="${AUTOREV}"
SRCREV_fip ?="${AUTOREV}"
SRCREV_bl40 ?="${AUTOREV}"
SRCREV_soc-templates ?="${AUTOREV}"

S = "${WORKDIR}/uboot-repo"
#Below format will cause build failure due to too long filename for mktemp
#SRCREV_FORMAT = "bl2_bl30_src-ao_bl31_bl31-1.3_bl32-3.8_bl33_fip_bl40_soc-templates"
SRCREV_FORMAT = "bl2_bl30_src-ao_bl31_bl31-1.3_bl32-3.8_bl33_fip"
PR = "r1"
PV = "v2019.01+git${SRCPV}"

PATH_append_tm2 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
PATH_append_g12a = ":${STAGING_DIR_NATIVE}/gcc-arm-none-elf/bin"
PATH_append_sc2 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
PATH_append_sc2-5.4 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
PATH_append_s4 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
DEPENDS_append = "optee-scripts-native optee-userspace-securebl32"
DEPENDS_append_tm2 = " riscv-none-gcc-native "
DEPENDS_append_g12a = " gcc-arm-none-eabi-native"
DEPENDS_append_sc2 = " riscv-none-gcc-native "
DEPENDS_append_sc2-5.4 = " riscv-none-gcc-native "
DEPENDS_append_s4 = " riscv-none-gcc-native "

DEPENDS_append = " bison-native coreutils-native python-native python-pycrypto-native "
#override this in customer layer bbappend for customer specific bootloader binaries
export BL30_ARG = ""
export BL2_ARG = ""

BL33_ARG = "${@bb.utils.contains('DISTRO_FEATURES','AVB','--avb2','',d)}"

DEBUG_PREFIX_MAP = ""

do_compile () {
    cd ${S}
    cp fip/mk .
    export BUILD_FOLDER=${S}/build/
    export PYTHONPATH="${STAGING_DIR_NATIVE}/usr/lib/python2.7/site-packages/"
    export CROSS_COMPILE=${TARGET_PREFIX}
    export KCFLAGS="--sysroot=${PKG_CONFIG_SYSROOT_DIR}"
    unset SOURCE_DATE_EPOCH
    UBOOT_TYPE="${UBOOT_MACHINE}"
    LDFLAGS= ./mk ${UBOOT_TYPE%_config} ${BL30_ARG} ${BL2_ARG} ${BL33_ARG}
    cp -rf build/* fip/
}

