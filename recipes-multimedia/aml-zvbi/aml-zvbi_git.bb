SUMMARY = "aml customization of zvbi library"
LICENSE = "LGPL-2.0+"
LIC_FILES_CHKSUM = "file://README;md5=91789e3b1cce0c7cd3f26db7a9f9bfac"

inherit autotools pkgconfig
DEPENDS = "libpng"
do_configure[noexec] = "1"

SRC_URI = "git://git.myamlogic.com/platform/external/libzvbi.git;protocol=git;branch=ics-amlogic;name=libzvbi"

MIRRORS_prepend += "git://git.myamlogic.com/external/libzvbi.git git://git@openlinux.amlogic.com/yocto/external/libzvbi.git;protocol=ssh; \n"

SRCREV ?= "${AUTOREV}"

S="${WORKDIR}/git"
ARCH_IS_64_aarch64 = "y"
ARCH_IS_64_armv7a = "n"
EXTRA_OEMAKE="ARCH_IS_64=${ARCH_IS_64}"
S="${WORKDIR}/git"

do_compile() {
    cd ${S}
    oe_runmake  all
}
do_install() {
   install -d ${D}${libdir} 
   install -d ${D}${includedir} 
    install -m 0644 ${S}/libzvbi.so ${D}${libdir} 
    install -m 0644 ${S}/src/libzvbi.h ${D}${includedir} 
    install -m 0644 ${S}/src/dtvcc.h ${D}${includedir} 
}

do_clean() {
    cd ${S}
    oe_runmake clean
}
FILES_${PN} = "${libdir}/*"
FILES_${PN}-dev = "${includedir}/*"
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
