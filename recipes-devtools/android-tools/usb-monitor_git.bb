DESCRIPTION = "usb_monitor for android tools"
PR = "r0"
LICENSE = "Apache-2.0"

FILESEXTRAPATHS_prepend := "${THISDIR}/android-tools-logcat:"

LIC_FILES_CHKSUM = "file://${THISDIR}/android-tools-logcat/LICENSE-2.0;md5=3b83ef96387f14655fc854ddc3c6bd57"

#The version of the recipe
PV = "1.0"

#The revision of the recipe
PR = "r0"

SRCREV = "${AUTOREV}"
#SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/aml_commonlib;protocol=${AML_GIT_PROTOCOL};branch=master;"
#SRC_URI += "file://liblog.patch"
SRC_URI += "file://LICENSE-2.0"

#For common patches
#SRC_URI_append = " ${@get_patch_list_with_path('${AML_PATCH_PATH}/vendor/amlogic/aml_commonlib')}"

S = "${WORKDIR}"
do_configure[noexec] = "1"
do_package_qa[noexec] = "1"

do_compile(){
    ${MAKE} -C ${S}/utils usb_monitor
}

do_install(){
    install -d ${D}${bindir}
    install -m 0755 ${S}/utils/usb_monitor ${D}${bindir}

}
INHIBIT_PACKAGE_STRIP = "1"
FILES_${PN} += "${bindir}/*"
FILES_${PN}-dbg += "${bindir}/.debug"
FILES_SOLIBSDEV = ""
INSANE_SKIP_${PN} = "dev-so"


