SUMMARY = "aml tvserver"
LICENSE = "CLOSED"
SRC_URI = "git://git.myamlogic.com/vendor/amlogic/tvserver.git;protocol=git;branch=pure-linux-amlogic"
MIRRORS_prepend += "git://git.myamlogic.com/vendor/amlogic/tvserver.git git://git@openlinux.amlogic.com/yocto/vendor/amlogic/tvserver.git;protocol=ssh; \n"

SRCREV ?= "${AUTOREV}"
SRC_URI +="file://tvserver.service"
SRC_URI += "file://0001-fix-wrong-path.patch"
DEPENDS = " dbus sqlite3 "
do_configure[noexec] = "1"
inherit autotools pkgconfig systemd
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
    if [ "${@bb.utils.contains("DISTRO_FEATURES", "systemd", "yes", "no", d)}" = "yes"  ]; then
        install -D -m 0644 ${WORKDIR}/tvserver.service ${D}${systemd_unitdir}/system/tvserver.service
    fi

}
SYSTEMD_SERVICE_${PN} = "tvserver.service"

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
