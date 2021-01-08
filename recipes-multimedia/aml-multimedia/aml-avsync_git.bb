SUMMARY = "aml avsync library"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-amlogic/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

include aml-multimedia.inc

SRC_URI = "git://${AML_GIT_ROOT}/linux/multimedia/libavsync;protocol=${AML_GIT_PROTOCOL};branch=master;"


do_configure[noexec] = "1"
inherit autotools pkgconfig

S="${WORKDIR}/git/"

EXTRA_OEMAKE="STAGING_DIR=${STAGING_DIR_TARGET} \
                TARGET_DIR=${D} \
             "
do_compile() {
    cd ${S}/src
    oe_runmake  all
}
do_install() {
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cd ${S}/src
    install -m 0644 aml_avsync_log.h ${D}${includedir}
    install -m 0644 aml_avsync.h ${D}${includedir}
    install -m 0644 libamlavsync.so ${D}${libdir}
}


FILES_${PN} = "${bindir}/* ${libdir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "ldflags"
