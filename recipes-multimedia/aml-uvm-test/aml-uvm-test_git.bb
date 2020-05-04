SUMMARY = "aml uvm test"
LICENSE = "CLOSED"

SRC_URI = "git://${AML_GIT_ROOT}/linux/multimedia;protocol=${AML_GIT_PROTOCOL};branch=master"
#SRC_URI_append = " file://0001-v4l2-uvm-test-support-SVP-1-1.patch"


SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

DEPENDS += " ffmpeg libdrm "

do_configure[noexec] = "1"
inherit autotools pkgconfig

S="${WORKDIR}/git/"

EXTRA_OEMAKE="STAGING_DIR=${STAGING_DIR_TARGET} \
                TARGET_DIR=${D} \
             " 
do_compile() {
    cd ${S}/v4l2-uvm-test/src
    oe_runmake  all
}
do_install() {
   install -d ${D}${bindir}
   install -m 0755 ${S}/v4l2-uvm-test/src/v4l2-uvm-test ${D}${bindir}/
}

 
FILES_${PN} = "${bindir}/* "
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "ldflags"
