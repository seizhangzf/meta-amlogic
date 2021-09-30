LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

TBRANCH = "tdk-v2.4.4"
TBRANCH_sc2 = "tdk-v3.8"
TBRANCH_s4 = "tdk-v3.8"

SRC_URI_T24 = " git://${AML_GIT_ROOT}/vendor/amlogic/tdk.git;protocol=${AML_GIT_PROTOCOL};branch=${TBRANCH};destsuffix=tdk;name=tdk"
SRC_URI_T24 += " git://${AML_GIT_ROOT}/vendor/amlogic/tdk_keys.git;protocol=${AML_GIT_PROTOCOL};branch=master;destsuffix=tdk/ta_export/keys;name=tdk-keys"

SRC_URI_T38 = " git://${AML_GIT_ROOT}/vendor/amlogic/tdk_basic.git;protocol=${AML_GIT_PROTOCOL};branch=${TBRANCH};destsuffix=tdk;name=tdk"
SRC_URI_T38 += " git://${AML_GIT_ROOT}/vendor/amlogic/tdk_export.git;protocol=${AML_GIT_PROTOCOL};branch=${TBRANCH};destsuffix=tdk/ta_export;name=tdk-keys"

SRC_URI = "${@bb.utils.contains('TDK_VERSION', 'v2.4', d.getVar('SRC_URI_T24'), d.getVar('SRC_URI_T38'), d)}"
SRC_URI_append = " git://${AML_GIT_ROOT}/vendor/amlogic/tdk_linuxdriver.git;protocol=${AML_GIT_PROTOCOL};branch=tdk-v2.4.4;destsuffix=tdk/linuxdriver;name=tdk-linuxdriver"

PATCHTOOL= "git"
#For common patches
TDIR = "tdk"
TDIR_g12a = "tdk"
TDIR_s4 = "tdk-3.8"
TDIR_sc2 = "tdk-3.8"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/${TDIR}', 'tdk')}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/tdk/linuxdriver', 'tdk/linuxdriver')}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/${TDIR}/ta_export', 'tdk/ta_export')}"

SRCREV ?= "${AUTOREV}"
#SRCREV_FORMAT = "tdk_tdk-linuxdriver_tdk-keys"
SRCREV_FORMAT = "tdk_tdk-keys_tdk-linuxdriver"
#INC_PR = "${TDK_VERSION}"
PV = "git${SRCPV}"
S = "${WORKDIR}/tdk"
