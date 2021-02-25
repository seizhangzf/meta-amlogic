inherit module

SUMMARY = "Amlogic aucpu_fw driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../license/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/aucpu_fw;protocol=${AML_GIT_PROTOCOL};branch=master;"
SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

SRC_URI_append = " file://52dvb.rules"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${WORKDIR}/git"

do_install() {
    FIRMWAREDIR=${D}/lib/firmware
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${FIRMWAREDIR}
    install -m 0666 ${S}/aucpu_fw.bin ${FIRMWAREDIR}
    install -d ${D}/etc/udev/rules.d
    install -m 0755 ${WORKDIR}/52dvb.rules ${D}/etc/udev/rules.d
}

FILES_${PN} = " \
        /lib/firmware/aucpu_fw.bin \
        /etc/udev/rules.d/52dvb.rules \
        "
