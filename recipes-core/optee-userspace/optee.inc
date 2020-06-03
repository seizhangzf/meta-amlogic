LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"
SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/tdk.git;protocol=${AML_GIT_PROTOCOL};branch=tdk-v2.4.4"

PATCHTOOL= "git"
#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/tdk')}"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"
INC_PR = "2.4.4"
