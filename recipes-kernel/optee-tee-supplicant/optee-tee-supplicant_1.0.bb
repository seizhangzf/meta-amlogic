DESCRIPTION = "optee tee-supplicant"
LICENSE = "Closed"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRCREV = "de7d380fa527e929df31f75ffffa656673031403"
SRC_URI = "git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh;branch=tdk-v2.4"
SRC_URI += "file://tee-supplicant.service"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

PROVIDES_${PN} += "tee-supplicant"

S = "${WORKDIR}/git"

do_install() {
    mkdir -p ${D}${bindir}
    install -m 0755 ${S}/ca_export_arm/bin/tee-supplicant ${D}${bindir}

    mkdir -p ${D}${libdir}
    install -m 0755 ${S}/ca_export_arm/lib/libteec.so ${D}${libdir}/libteec.so.1.0

    ln -s libteec.so.1 ${D}${libdir}/libteec.so
    ln -s libteec.so.1.0 ${D}${libdir}/libteec.so.1

    # systemd service file
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_unitdir}/system/
}

FILES_${PN} += "${bindir}/* ${libdir}/libteec.so.1.0 ${libdir}/libteec.so.1"
FILES_${PN}-dev += "${libdir}/libteec.so"
INSANE_SKIP_${PN} = "ldflags dev-so"

FILES_${PN} += "${systemd_unitdir}/system/tee-supplicant.service"
SYSTEMD_SERVICE_${PN} = "tee-supplicant.service"
inherit systemd
