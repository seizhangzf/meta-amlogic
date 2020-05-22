DESCRIPTION = "Dolby MS12 decryption utility"
LICENSE = "CLOSED"

include aml-multimedia.inc

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

inherit autotools pkgconfig
SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

S = "${WORKDIR}/git/dolby_ms12_release/src"

do_install() {
    install -d -m 0644 ${D}${bindir}
    install -d -m 0644 ${D}${libdir}

    install -m 0755 ${S}/dolby_fw_dms12_32bits ${D}${bindir}
    install -m 0644 ${S}/libdolbyms12.so ${D}${libdir}

}

FILES_${PN} += "${libdir}/*.so ${bindir}/*" 
#FILES_${PN}-dev = "${includedir} ${libdir}/pkgconfig/*"
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"
