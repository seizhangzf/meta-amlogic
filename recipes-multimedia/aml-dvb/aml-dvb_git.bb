SUMMARY = "aml dvb samples"
LICENSE = "LGPL-2.0+"
LIC_FILES_CHKSUM = "file://Doxyfile;md5=c771730fa57fc498cd9dc7d74b84934d"

SRC_URI = "git://${AML_GIT_ROOT}/dvb.git;protocol=${AML_GIT_PROTOCOL};branch=tv-kernel-4.9"


SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${THISDIR}/amlogic')}"
DEPENDS = " aml-zvbi sqlite libamavutils"
RDEPENDS_${PN} = "libamavutils"
PROVIDES = "libam_adp"

do_configure[noexec] = "1"
inherit autotools pkgconfig
S="${WORKDIR}/git"

EXTRA_OEMAKE="TARGET_DIR=${S} \ 
              TARGET_LDFLAGS=' -lstdc++ -lpthread -lrt -lm -ldl -lc -lgcc -L${STAGING_DIR_TARGET}/usr/lib' \
              STAGING_DIR='${STAGING_DIR_TARGET}' \
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
    install -d -m 0644 ${D}/usr/include/am_adp/
    install -D -m 0644 ${S}/include/am_adp/*.h ${D}/usr/include
    install -D -m 0644 ${S}/include/am_adp/*.h ${D}/usr/include/am_adp
    install -D -m 0644 ${S}/include/am_adp/libdvbsi/*.h ${D}/usr/include/libdvbsi
    install -d ${D}${includedir}/am_adp
    install -m 0755 ${S}/include/am_adp/am_evt.h ${D}${includedir}/am_adp
    install -m 0755 ${S}/include/am_adp/am_types.h ${D}${includedir}/am_adp
    install -m 0755 ${S}/include/am_adp/am_userdata.h ${D}${includedir}/am_adp
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
