inherit kernel
require linux-meson.inc

LIC_FILES_CHKSUM = "file://${THISDIR}/../../license/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

#We still need patch even in external src mode
SRCTREECOVEREDTASKS_remove = "do_patch"
FILESEXTRAPATHS_prepend := "${THISDIR}/5.4:"

KBRANCH = "amlogic-5.4-dev"
#SRC_URI = "git://${AML_GIT_ROOT}/kernel/common.git;protocol=${AML_GIT_PROTOCOL};branch=${KBRANCH};"
#SRC_URI_append = " file://defconfig"
SRC_URI_append = " file://gki-read_ext_module_config.sh"
SRC_URI_append = " file://gki-read_ext_module_predefine.sh"
SRC_URI_append_s4 = " file://s4.cfg"

#SRC_URI_append = " file://meson.scc \
#            file://meson.cfg \
#            file://meson-user-config.cfg \
#            file://systemd.cfg \
#            file://logcat.cfg \
#            file://meson-user-patches.scc "
#SRC_URI_append = " ${@get_patch_list('${THISDIR}/armv7a')}"

#For common patches
KDIR = "aml-5.4"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/kernel/${KDIR}')}"

DEPENDS += "rsync-native"

LINUX_VERSION ?= "5.4.86"
LINUX_VERSION_EXTENSION ?= "-amlogic"

PR = "r2"
SRCREV ?="${AUTOREV}"
PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(mesontm2_5.4*|mesonsc2_5.4*|mesont5d_5.4*|mesont7_*|mesons4_*)"

KERNEL_IMAGETYPE = "Image"
KCONFIG_MODE = "alldefconfig"

S = "${WORKDIR}/git"
KBUILD_DEFCONFIG_t7 = "meson64_a64_P_defconfig"
KBUILD_DEFCONFIG_sc2-5.4 = "meson64_a64_R_defconfig"
KBUILD_DEFCONFIG_tm2-5.4 = "meson64_a64_R_defconfig"
KBUILD_DEFCONFIG_t5d-5.4 = "meson64_a64_R_defconfig"
KBUILD_DEFCONFIG_s4 = "meson64_a64_R_defconfig"

GKI_DEFCONFIG = "meson64_gki_module_config"
#T7 did not use GKI yet.
GKI_DEFCONFIG_t7 = ""

gki_module_compile () {
  oe_runmake -C ${STAGING_KERNEL_DIR}/${1} CC="${KERNEL_CC}" LD="${KERNEL_LD}" O=${B} M=${1} KERNEL_SRC=${S}
}

gki_module_install () {
  cd ${B}; rsync -R $(find ${1} -name *.ko | xargs) ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/; cd -
}

do_compile_append () {
  if [ -n "${GKI_DEFCONFIG}" ]; then
    #Note, gki_ext_module_config/gki_ext_module_predefine will be used by all kernel module build
    ${WORKDIR}/gki-read_ext_module_config.sh ${S}/arch/arm64/configs/${GKI_DEFCONFIG} > ${STAGING_KERNEL_DIR}/gki_ext_module_config
    ${WORKDIR}/gki-read_ext_module_predefine.sh ${S}/arch/arm64/configs/${GKI_DEFCONFIG} > ${STAGING_KERNEL_DIR}/gki_ext_module_predefine

    export GKI_EXT_MODULE_CONFIG="$(cat ${STAGING_KERNEL_DIR}/gki_ext_module_config)"
    export GKI_EXT_MODULE_PREDEFINE="$(cat ${STAGING_KERNEL_DIR}/gki_ext_module_predefine)"
    gki_module_compile drivers/amlogic
    gki_module_compile sound/soc/amlogic
    gki_module_compile sound/soc/codecs/amlogic
  else
    rm -fr ${STAGING_KERNEL_DIR}/gki_ext_module_config ${STAGING_KERNEL_DIR}/gki_ext_module_predefine
  fi
}

do_install_append () {
  if [ -n "${GKI_DEFCONFIG}" ]; then
    gki_module_install drivers/amlogic
    gki_module_install sound/soc/amlogic
    gki_module_install sound/soc/codecs/amlogic
  fi
}

