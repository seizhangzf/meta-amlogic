require u-boot-meson.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/files_2015:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://${AML_GIT_ROOT}/firmware/bin/bl2.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl2/bin;name=bl2"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl30.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl30/bin;name=bl30"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl31.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl31/bin;name=bl31"
SRC_URI_append = " git://${AML_GIT_ROOT}/firmware/bin/bl31.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev-1.3;destsuffix=uboot-repo/bl31_1.3/bin;name=bl31-1.3"
SRC_URI_append = " git://${AML_GIT_ROOT}/vendor/amlogic/tdk.git;protocol=${AML_GIT_PROTOCOL};branch=tdk-v2.4;destsuffix=uboot-repo/tdk;name=tdk"
SRC_URI_append = " git://${AML_GIT_ROOT}/uboot.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/bl33/v2015;name=bl33"
SRC_URI_append = " git://${AML_GIT_ROOT}/amlogic/tools/fip.git;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev;destsuffix=uboot-repo/fip;name=fip"

#patches
SRC_URI_append = " file://0001-remove-hardcode-path.patch;patchdir=bl33/v2015"

do_configure[noexec] = "1"

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

DEPENDS_append = " coreutils-native python-native python-pycrypto-native "

do_compile () {
    cp fip/mk .
    export BUILD_FOLDER=${S}/build/
    export PYTHONPATH="${STAGING_DIR_NATIVE}/usr/lib/python2.7/site-packages/"
    UBOOT_TYPE="${UBOOT_MACHINE}"
    if ${@bb.utils.contains('DISTRO_FEATURES','secure-u-boot','true','false',d)}; then
        mkdir -p ${S}/bl32/bin/${BL32_SOC_FAMILY}/
        #cp -rf ${S}/tdk/secureos/* ${S}/bl32/bin/
        ${S}/tdk/ta_export/scripts/pack_kpub.py \
            --rsk=${S}/tdk/ta_export/keys/root_rsa_pub_key.pem \
            --rek=${S}/tdk/ta_export/keys/root_aes_key.bin \
            --in=${S}/tdk/secureos/${BL32_SOC_FAMILY}/bl32.img \
            --out=${S}/bl32/bin/${BL32_SOC_FAMILY}/bl32.img

        ./mk ${UBOOT_TYPE%_config} --bl32 bl32/bin/${BL32_SOC_FAMILY}/bl32.img
    else
        ./mk ${UBOOT_TYPE%_config}
    fi
    cp -rf build/* fip/
}

