LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

TBRANCH = "tdk-v2.4.4"
TBRANCH_sc2 = "tdk-v3.8.0"
SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/tdk.git;protocol=${AML_GIT_PROTOCOL};branch=${TBRANCH};destsuffix=tdk;name=tdk"
SRC_URI_append = " git://${AML_GIT_ROOT}/vendor/amlogic/tdk_linuxdriver.git;protocol=${AML_GIT_PROTOCOL};branch=tdk-v2.4.4;destsuffix=tdk/linuxdriver;name=tdk_linuxdriver"
SRC_URI_append = " git://${AML_GIT_ROOT}/vendor/amlogic/tdk_keys.git;protocol=${AML_GIT_PROTOCOL};branch=master;destsuffix=tdk/ta_export/keys;name=tdk_keys"

PATCHTOOL= "git"
#For common patches
TDIR = "tdk"
TDIR_sc2 = "tdk-3.8"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/${TDIR}', '${TDIR}')}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/tdk/linuxdriver', 'tdk/linuxdriver')}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/tdk/ta_export/keys', 'tdk/ta_export/keys')}"

TDK_VERSION = "2.4.4"
TDK_VERSION_sc2 = "3.8.0"

SRCREV ?= "${AUTOREV}"
SRCREV_FORMAT = "tdk_tdk_linuxdriver_tdk_keys"
INC_PR = "${TDK_VERSION}"
PV = "git${SRCPV}"
S = "${WORKDIR}/tdk"
