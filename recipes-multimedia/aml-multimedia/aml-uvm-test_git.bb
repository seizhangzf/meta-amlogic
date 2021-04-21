SUMMARY = "aml uvm test"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

include aml-multimedia.inc

DEPENDS += " ffmpeg libdrm zlib aml-avsync"
RDEPENDS_${PN} += "aml-avsync"

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
