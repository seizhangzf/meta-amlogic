SUMMARY = "Realtek bluetooth"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=726a766df559f36316aa5261724ee8cd"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/realtek/bluetooth.git;protocol=${AML_GIT_PROTOCOL};branch=master;nobranch=1"

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"

S = "${WORKDIR}/git"

do_compile(){
    ${MAKE} -C ${S}/rtk_hciattach CC='${CC}'
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/rtk_hciattach/rtk_hciattach ${D}${bindir}
}
