require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl33/v2015:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl2/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl30/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl31/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/bl31_1.3/bin:"
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015/fip:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

EXTRA_OEMAKE = ''
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://${AML_GIT_ROOT}/firmware/bin/bl2.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl2/bin;name=bl2"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl30.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl30/bin;name=bl30"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl31.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl31/bin;name=bl31"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl31_1.3.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev-1.3;destsuffix=uboot-repo/bl31_1.3/bin;name=bl31-1.3"
SRC_URI_append = " git://${AML_GIT_ROOT}/uboot.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl33/v2015;name=bl33"
SRC_URI_append = " git://${AML_GIT_ROOT}/amlogic/tools/fip.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/fip;name=fip"

PATCHTOOL="git"

#For common patch
SRC_URI_append = " ${@get_patch_list_with_path('${THISDIR}/amlogic/v2015', 'bl33/v2015')}"
SRC_URI_append = " ${@get_patch_list_with_path('${THISDIR}/amlogic/fip', 'fip')}"
#can not patch bl binaries due to permission issue bl binary repos

do_configure[noexec] = "1"

SRCREV_bl2 ?="${AUTOREV}"
SRCREV_bl30 ?="${AUTOREV}"
SRCREV_bl31 ?="${AUTOREV}"
SRCREV_bl31-1.3 ?="${AUTOREV}"
SRCREV_bl33 ?="${AUTOREV}"
SRCREV_fip ?="${AUTOREV}"

S = "${WORKDIR}/uboot-repo"
SRCREV_FORMAT = "bl2_bl30_bl31_bl31-1.3_bl33_fip"
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

PATH_append = ":${STAGING_DIR_NATIVE}/gcc-linaro-aarch64-none-elf/bin"
PATH_append_tm2 = ":${STAGING_DIR_NATIVE}/riscv-none-gcc/bin"
PATH_append_g12a = ":${STAGING_DIR_NATIVE}/gcc-arm-none-elf/bin"
DEPENDS_append = "gcc-linaro-aarch64-none-elf-native optee-scripts-native optee-userspace-securebl32"
DEPENDS_append_tm2 = " riscv-none-gcc-native "
DEPENDS_append_g12a = " gcc-arm-none-eabi-native"

DEPENDS_append = " coreutils-native python-native python-pycrypto-native "
#override this in customer layer bbappend for customer specific bootloader binaries
export BL30_ARG = ""
export BL2_ARG = ""

do_compile () {
    cd ${S}
    cp fip/mk .
    export BUILD_FOLDER=${S}/build/
    export PYTHONPATH="${STAGING_DIR_NATIVE}/usr/lib/python2.7/site-packages/"
    UBOOT_TYPE="${UBOOT_MACHINE}"
    if ${@bb.utils.contains('DISTRO_FEATURES','secure-u-boot','true','false',d)}; then
        mkdir -p ${S}/bl32/bin/${BL32_SOC_FAMILY}/
        ${STAGING_DIR_NATIVE}/tdk/scripts/pack_kpub.py \
            --rsk=${STAGING_DIR_NATIVE}/tdk/keys/root_rsa_pub_key.pem \
            --rek=${STAGING_DIR_NATIVE}/tdk/keys/root_aes_key.bin \
            --in=${STAGING_DIR_TARGET}/usr/share/tdk/secureos/${BL32_SOC_FAMILY}/bl32.img \
            --out=${S}/bl32/bin/${BL32_SOC_FAMILY}/bl32.img

        ./mk ${UBOOT_TYPE%_config} --bl32 bl32/bin/${BL32_SOC_FAMILY}/bl32.img ${BL30_ARG} ${BL2_ARG}
    else
        ./mk ${UBOOT_TYPE%_config} ${BL30_ARG} ${BL2_ARG}
    fi
    cp -rf build/* fip/
}

