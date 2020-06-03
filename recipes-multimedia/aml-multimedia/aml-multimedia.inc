SRC_URI = "git://${AML_GIT_ROOT}/linux/multimedia;protocol=${AML_GIT_PROTOCOL};branch=master"
SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"
INC_PR="1"
