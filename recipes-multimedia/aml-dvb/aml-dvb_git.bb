SUMMARY = "aml dvb samples"
LICENSE = "LGPL-2.0+"
LIC_FILES_CHKSUM = "file://Doxyfile;md5=c771730fa57fc498cd9dc7d74b84934d"

SRC_URI = "git://${AML_GIT_ROOT}/dvb.git;protocol=${AML_GIT_PROTOCOL};branch=tv-kernel-4.9"


SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/dvb')}"
DEPENDS = " aml-zvbi sqlite libamavutils"
RDEPENDS_${PN} = "libamavutils"
PROVIDES = "libam_adp"

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
   install -d ${D}${includedir}/libdvbsi

    cd ${S}
    install -D -m 0644 ${S}/am_adp/libam_adp.so ${D}/usr/lib
    install -D -m 0644 ${S}/include/am_adp/*.h ${D}/usr/include
    install -D -m 0644 ${S}/include/am_adp/libdvbsi/*.h ${D}/usr/include/libdvbsi
    #install -m 0755 ${S}/test/am_fend_test/am_fend_test ${D}${bindir}
    #install -m 0755 ${S}/test/am_dvr_test/am_dvr_test ${D}${bindir}
    #install -m 0755 ${S}/test/am_dmx_test/am_dmx_test ${D}${bindir}
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
