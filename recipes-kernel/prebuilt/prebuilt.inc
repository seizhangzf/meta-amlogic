
SRC_URI = "git://${AML_GIT_ROOT}${AML_GIT_ROOT_YOCTO_SUFFIX}/rdk/prebuilt/target;protocol=${AML_GIT_PROTOCOL};branch=master"
SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"
