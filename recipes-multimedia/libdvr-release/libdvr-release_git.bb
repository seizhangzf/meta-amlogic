DESCRIPTION = "aml libdvr release"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/libdvr_release;protocol=${AML_GIT_PROTOCOL};branch=linux-buildroot"

DEPENDS += "liblog aml-mediahal-sdk"
RDEPENDS_${PN} += "liblog aml-mediahal-sdk"

do_compile[noexec] = "1"

SRCREV ?= "${AUTOREV}"

PV = "git${SRCPV}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE="STAGING_DIR=${STAGING_DIR_TARGET} \
                 TARGET_DIR=${D} \"
do_install() {
    install -d -m 0644 ${D}/usr/lib
    install -d -m 0644 ${D}/usr/include/libdvr
    install -D -m 0644 ${S}/lib/libamdvr.product.so  ${D}/usr/lib
    install -D -m 0644 ${S}/include/*.h ${D}/usr/include/libdvr
}

FILES_${PN} = "${libdir}/* ${includedir}/*"
FILES_${PN}-dev = "${includedir}/*"
INSANE_SKIP_${PN} = "dev-so ldflags"
INSANE_SKIP_${PN}-dev = "dev-so ldflags"
