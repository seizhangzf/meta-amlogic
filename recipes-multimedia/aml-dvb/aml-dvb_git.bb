SUMMARY = "aml dvb samples"
LICENSE = "LGPL-2.0+"
LIC_FILES_CHKSUM = "file://Doxyfile;md5=c771730fa57fc498cd9dc7d74b84934d"

SRC_URI = "git://${AML_GIT_ROOT}/dvb.git;protocol=${AML_GIT_PROTOCOL};branch=tv-kernel-4.9"

SRC_URI_append = " file://0001-fix-yocto-build.patch"
SRC_URI_append = " file://0001-fix-linux-dvb-adapter-name.patch"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

DEPENDS = " aml-zvbi libplayer sqlite"
do_configure[noexec] = "1"
inherit autotools pkgconfig
S="${WORKDIR}/git"

EXTRA_OEMAKE="TARGET_DIR=${S} \ 
              TARGET_LDFLAGS=' -lstdc++ -lpthread -lrt -lm -ldl -lc -lgcc -L${STAGING_DIR_TARGET}/usr/lib' \
             " 
do_compile() {
    cd ${S}
    mkdir -p ${S}/usr/lib
    mkdir -p ${S}/usr/bin
    oe_runmake  all
}
do_install() {
   install -d ${D}${libdir} 
   install -d ${D}${bindir}
   install -d ${D}${includedir}

    cd ${S}
    oe_runmake  install
    install -D -m 0644 ${S}/usr/lib/libam_adp.so ${D}/usr/lib
    install -D -m 0644 ${S}/usr/lib/libam_mw.so ${D}/usr/lib
    install -m 0644 ${S}/include/am_adp/*.h ${D}${includedir}
    install -m 0644 ${S}/include/am_mw/*.h ${D}${includedir}
    install -m 0755 ${S}/usr/bin/* ${D}${bindir}
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
