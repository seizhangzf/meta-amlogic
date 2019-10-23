SUMMARY = "aml tvserver"
LICENSE = "CLOSED"
SRC_URI = "git://git.myamlogic.com/vendor/amlogic/tvserver.git;protocol=git;branch=pure-linux-amlogic"

SRCREV= "0ccc8639f85e3a33a8a5d851af9b0b94ddc26854"
DEPENDS = " dbus sqlite3 "
do_configure[noexec] = "1"
inherit autotools pkgconfig
S="${WORKDIR}/git"

EXTRA_OEMAKE="STAGING_DIR=${STAGING_DIR_TARGET} \
                TARGET_DIR=${D} \
             " 
do_compile() {
    cd ${S}
    oe_runmake  all
}
do_install() {
   install -d ${D}${libdir} 
   install -d ${D}${bindir}
   install -d ${D}${includedir}

    cd ${S}
    oe_runmake  install
    install -m 0644 ${S}/client/include/*.h ${D}${includedir}
}

do_clean() {
    cd ${S}
    oe_runmake clean
}
FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
