DESCRIPTION = "simulate_key for autotest"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${THISDIR}/android-tools-logcat/LICENSE-2.0;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"
PR = "r0"

#SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/aml_commonlib;protocol=${AML_GIT_PROTOCOL};branch=master;"

#For common patches
#SRC_URI_append = " ${@get_patch_list_with_path('${AML_PATCH_PATH}/vendor/amlogic/aml_commonlib')}"

S = "${WORKDIR}/git"

#do_package_qa[noexec] = "1"

IR_REMOTE_DEVICE ?= "/dev/input/event0"

EXTRA_OEMAKE = "IR_REMOTE_DEVICE=${IR_REMOTE_DEVICE}"
do_compile(){
    oe_runmake -C ${S}/utils simulate_key
}

do_install(){
    install -d ${D}${bindir}
    install -m 0755 ${S}/utils/simulate_key ${D}${bindir}
}

FILES_${PN} = "${bindir}/*"
