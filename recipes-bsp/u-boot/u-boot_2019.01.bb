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

#Only enable this in openlinux
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'nagra', 'git://${AML_GIT_ROOT_OP}/nagra-sdk-nocs.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=projects/openlinux/v3.2-rdk;destsuffix=uboot-repo/nagra-sdk;name=nagra', '', d)}"

VMX_BRANCH = "TBD"
VMX_BRANCH_sc2 = "m9x4-rel-linux"
VMX_BRANCH_s4 = "m9y4-rel-linux"
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'verimatrix', 'git://${AML_GIT_ROOT_OP}/vendor/vmx/bootloader.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=${VMX_BRANCH};destsuffix=uboot-repo/vmx-sdk/bootloader;name=vmx', '', d)}"

IRDETO_BRANCH = "TBD"
IRDETO_BRANCH_sc2 = "openlinux/sc2-msr4-linux"
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'irdeto', 'git://${AML_GIT_ROOT_OP}/irdeto-sdk.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=${IRDETO_BRANCH};destsuffix=uboot-repo/irdeto-sdk;name=irdeto', '', d)}"

SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'synamedia', 'git://${AML_GIT_ROOT_OP}/synamedia/synamedia-sdk.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=master;destsuffix=uboot-repo/synamedia-sdk;name=synamedia', '', d)}"

PATCHTOOL="git"

#For common patch
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/uboot/bl33/v2019', 'bl33/v2019')}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/uboot/fip', 'fip')}"
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

PATH_append = ":${STAGING_DIR_NATIVE}/gcc-linaro-aarch64-elf/bin"
PATH_append_tm2 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
PATH_append_g12a = ":${STAGING_DIR_NATIVE}/gcc-arm-none-elf/bin"
PATH_append_sc2 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
PATH_append_s4 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
PATH_append_t7 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
DEPENDS_append = "gcc-linaro-aarch64-elf-native optee-scripts-native optee-userspace-securebl32"
DEPENDS_append_tm2 = " riscv-none-gcc-native "
DEPENDS_append_g12a = " gcc-arm-none-eabi-native"
DEPENDS_append_sc2 = " riscv-none-gcc-native "
DEPENDS_append_s4 = " riscv-none-gcc-native "
DEPENDS_append_t7 = " riscv-none-gcc-native "

DEPENDS_append = " bison-native coreutils-native python-native python-pycrypto-native "
#override this in customer layer bbappend for customer specific bootloader binaries
export BL30_ARG = ""
export BL2_ARG = ""

BL33_ARG = "${@bb.utils.contains('DISTRO_FEATURES','AVB','--avb2','',d)}"

#NAGRA UBOOT PATH depends on SoC
NAGRA_UBOOT_PATH = "TBD"
NAGRA_UBOOT_PATH_sc2 = "sc2"
NAGRA_UBOOT_ARG = " ${@bb.utils.contains('DISTRO_FEATURES', 'nagra', '--chip-varient nocs-jts-ap --bl32 nagra-sdk/bootloader/${NAGRA_UBOOT_PATH}/bl32/blob-bl32.bin.signed --bl31 nagra-sdk/bootloader/${NAGRA_UBOOT_PATH}/bl31/blob-bl31.bin.signed', '', d)}"

#VMX UBOOT PATH depends on SoC
VMX_UBOOT_PATH = "TBD"
VMX_UBOOT_PATH_s4 = "s905y4"
VMX_UBOOT_PATH_sc2 = "sc2"
VMX_UBOOT_ARG = " ${@bb.utils.contains('DISTRO_FEATURES', 'verimatrix', '--bl32 vmx-sdk/bootloader/${VMX_UBOOT_PATH}/bl32/blob-bl32.bin.signed', '', d)}"

#IRDETO UBOOT PATH depends on SoC
IRDETO_UBOOT_PATH = "TBD"
IRDETO_UBOOT_PATH_sc2 = "sc2"
IRDETO_UBOOT_PATH_s4 = "s4d"
IRDETO_BL2x_ARG="--bl2x irdeto-sdk/bootloader/${IRDETO_UBOOT_PATH}/bl2/blob-bl2x.bin.signed"
IRDETO_BL2e_ARG="--bl2e irdeto-sdk/bootloader/${IRDETO_UBOOT_PATH}/bl2/blob-bl2e.sto.bin.signed"
IRDETO_BL32_ARG="--bl32 irdeto-sdk/bootloader/${IRDETO_UBOOT_PATH}/bl32/blob-bl32.bin.signed"
IRDETO_BL40_ARG="--bl40 irdeto-sdk/bootloader/${IRDETO_UBOOT_PATH}/bl40/blob-bl40.bin.signed"
IRDETO_UBOOT_ARG = " ${@bb.utils.contains('DISTRO_FEATURES', 'irdeto', '${IRDETO_BL2x_ARG} ${IRDETO_BL2e_ARG} ${IRDETO_BL32_ARG} ${IRDETO_BL40_ARG}', '', d)}"

#SYNAMEDIA UBOOT PATH depends on SoC
SYNAMEDIA_UBOOT_PATH = "TBD"
SYNAMEDIA_UBOOT_PATH_sc2 = "s905c2eng"
SYNAMEDIA_BL2_ARG="--bl2 synamedia-sdk/bootloader/${SYNAMEDIA_UBOOT_PATH}/bb1st.sto.bin.signed"
SYNAMEDIA_BL2e_ARG="--bl2e synamedia-sdk/bootloader/${SYNAMEDIA_UBOOT_PATH}/blob-bl2e.sto.bin.signed"
SYNAMEDIA_BL2x_ARG="--bl2x synamedia-sdk/bootloader/${SYNAMEDIA_UBOOT_PATH}/blob-bl2x.bin.signed"
SYNAMEDIA_BL31_ARG="--bl31 synamedia-sdk/bootloader/${SYNAMEDIA_UBOOT_PATH}/blob-bl31.bin.signed"
SYNAMEDIA_BL32_ARG="--bl32 synamedia-sdk/bootloader/${SYNAMEDIA_UBOOT_PATH}/blob-bl32.bin.signed"
SYNAMEDIA_DDR_FIP_ARG="--ddr-fip synamedia-sdk/bootloader/${SYNAMEDIA_UBOOT_PATH}/blob-ddr-fip.bin.signed"
SYNAMEDIA_UBOOT_ARG = " ${@bb.utils.contains('DISTRO_FEATURES', 'synamedia', ' ${SYNAMEDIA_BL2e_ARG} ${SYNAMEDIA_BL2x_ARG} ${SYNAMEDIA_BL2_ARG} ${SYNAMEDIA_BL31_ARG} ${SYNAMEDIA_BL32_ARG}', '', d)}"

DEBUG_PREFIX_MAP = ""
do_compile () {
    cd ${S}
    cp -f fip/mk .
    export BUILD_FOLDER=${S}/build/
    export PYTHONPATH="${STAGING_DIR_NATIVE}/usr/lib/python2.7/site-packages/"
    export CROSS_COMPILE=aarch64-elf-
    unset SOURCE_DATE_EPOCH
    UBOOT_TYPE="${UBOOT_MACHINE}"
    if [ "${@bb.utils.contains('DISTRO_FEATURES', 'synamedia', 'true', 'false', d)}" = "true" ] ; then
        sed -i 's/CONFIG_CHIPSET_NAME="s905c2"/CONFIG_CHIPSET_NAME="s905c2eng"/g' ${S}/bl33/v2019/board/amlogic/defconfigs/sc2_ah232_defconfig
    fi
    LDFLAGS= ./mk ${UBOOT_TYPE%_config} ${BL30_ARG} ${BL2_ARG} ${BL33_ARG} ${NAGRA_UBOOT_ARG} ${VMX_UBOOT_ARG} ${IRDETO_UBOOT_ARG} ${SYNAMEDIA_UBOOT_ARG}
    cp -rf build/* fip/
}

