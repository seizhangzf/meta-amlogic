require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl33/v2015:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl2/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl30/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl30/src_ao:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl31/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl31_1.3/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/fip:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2019/bl32_3.8/bin:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

EXTRA_OEMAKE = ''
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://${AML_GIT_ROOT}/firmware/bin/bl2.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl2/bin;name=bl2"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl30.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl30/bin;name=bl30"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/aocpu.git;protocol=${AML_GIT_PROTOCOL};branch=projects/amlogic-dev;destsuffix=uboot-repo/bl30/src_ao;name=src-ao"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl31.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl31/bin;name=bl31"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl31.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev-1.3;destsuffix=uboot-repo/bl31_1.3/bin;name=bl31-1.3"
SRC_URI_append = " git://${AML_GIT_ROOT}/uboot.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl33/v2015;name=bl33"
SRC_URI_append = " git://${AML_GIT_ROOT}/amlogic/tools/fip.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/fip;name=fip"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl32.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev-3.8.0;destsuffix=uboot-repo/bl32_3.8/bin;name=bl32-3.8"

PATCHTOOL="git"

#For common patch
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/uboot/bl33/v2015', 'bl33/v2015')}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/uboot/fip', 'fip')}"
#can not patch bl binaries due to permission issue bl binary repos

do_configure[noexec] = "1"

SRCREV_pn-u-boot ?="${AUTOREV}"
SRCREV_bl2 ?="${AUTOREV}"
SRCREV_bl30 ?="${AUTOREV}"
SRCREV_src-ao ?="${AUTOREV}"
SRCREV_bl31 ?="${AUTOREV}"
SRCREV_bl31-1.3 ?="${AUTOREV}"
SRCREV_bl33 ?="${AUTOREV}"
SRCREV_fip ?="${AUTOREV}"
SRCREV_bl32-3.8 ?="${AUTOREV}"

S = "${WORKDIR}/uboot-repo"
SRCREV_FORMAT = "bl2_bl30_src-ao_bl31_bl31-1.3_bl33_fip_bl32-3.8"
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
BL32_SOC_FAMILY_tm2 = "tm2"
BL32_SOC_FAMILY_t5d = "t5d"

PATH_append = ":${STAGING_DIR_NATIVE}/gcc-linaro-aarch64-elf/bin"
PATH_append = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
DEPENDS_append = "gcc-linaro-aarch64-elf-native "
DEPENDS_append = "optee-scripts-native optee-userspace-securebl32"
DEPENDS_append = " riscv-none-gcc-native "

DEPENDS_append = " coreutils-native python-native python-pycrypto-native "
#override this in customer layer bbappend for customer specific bootloader binaries
export BL30_ARG = ""
export BL2_ARG = ""

do_compile () {
    cd ${S}
    cp fip/mk .
    export BUILD_FOLDER=${S}/build/
    export PYTHONPATH="${STAGING_DIR_NATIVE}/usr/lib/python2.7/site-packages/"
    export CROSS_COMPILE=aarch64-elf-
    unset SOURCE_DATE_EPOCH
    UBOOT_TYPE="${UBOOT_MACHINE}"
    if ${@bb.utils.contains('DISTRO_FEATURES','secure-u-boot','true','false',d)}; then
        mkdir -p ${S}/bl32/bin/${BL32_SOC_FAMILY}/
        ${STAGING_DIR_NATIVE}/tdk/scripts/pack_kpub.py \
            --rsk=${STAGING_DIR_NATIVE}/tdk/keys/root_rsa_pub_key.pem \
            --rek=${STAGING_DIR_NATIVE}/tdk/keys/root_aes_key.bin \
            --in=${STAGING_DIR_TARGET}/usr/share/tdk/secureos/${BL32_SOC_FAMILY}/bl32.img \
            --out=${S}/bl32/bin/${BL32_SOC_FAMILY}/bl32.img

        LDFLAGS= ./mk ${UBOOT_TYPE%_config} --bl32 bl32/bin/${BL32_SOC_FAMILY}/bl32.img ${BL30_ARG} ${BL2_ARG}
    else
        LDFLAGS= ./mk ${UBOOT_TYPE%_config} ${BL30_ARG} ${BL2_ARG}
    fi
    cp -rf build/* fip/
}

do_compile_g12a () {
    cd ${S}
    cp fip/mk .
    export BUILD_FOLDER=${S}/build/
    export PYTHONPATH="${STAGING_DIR_NATIVE}/usr/lib/python2.7/site-packages/"
    export CROSS_COMPILE=aarch64-elf-
    unset SOURCE_DATE_EPOCH
    UBOOT_TYPE="${UBOOT_MACHINE}"
    if ${@bb.utils.contains('DISTRO_FEATURES','secure-u-boot','true','false',d)}; then
        LDFLAGS= ./mk ${UBOOT_TYPE%_config} --bl32 bl32_3.8/bin/${BL32_SOC_FAMILY}/bl32.img ${BL30_ARG} ${BL2_ARG}
    else
        LDFLAGS= ./mk ${UBOOT_TYPE%_config} ${BL30_ARG} ${BL2_ARG}
    fi
    cp -rf build/* fip/
}

