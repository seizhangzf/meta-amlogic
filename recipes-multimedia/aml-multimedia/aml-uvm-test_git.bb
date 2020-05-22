SUMMARY = "aml uvm test"
LICENSE = "CLOSED"

include aml-multimedia.inc

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

DEPENDS += " ffmpeg libdrm zlib"

do_configure[noexec] = "1"
inherit autotools pkgconfig

S="${WORKDIR}/git/v4l2-uvm-test"

EXTRA_OEMAKE="STAGING_DIR=${STAGING_DIR_TARGET} \
                TARGET_DIR=${D} \
             " 
do_compile() {
    cd ${S}/src
    oe_runmake  all
}
do_install() {
   install -d ${D}${bindir}
   install -m 0755 ${S}/src/v4l2-uvm-test ${D}${bindir}/
}

 
FILES_${PN} = "${bindir}/* "
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "ldflags"
