DESCRIPTION = "optee and tee-supplicant"
LICENSE = "Closed"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
include optee.inc

SRC_URI += "file://tee-supplicant.service"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

S = "${WORKDIR}/git"
PROVIDES = "optee-userspace-securebl32"
PACKAGES =+ "\
    ${PN}-securebl32 \
    "

do_install() {
    mkdir -p ${D}${bindir}
    mkdir -p ${D}${datadir}/tdk/secureos
    install -m 0755 ${S}/ca_export_arm/bin/tee-supplicant ${D}${bindir}

    mkdir -p ${D}${libdir}
    install -m 0755 ${S}/ca_export_arm/lib/libteec.so ${D}${libdir}/libteec.so.1.0

    ln -s libteec.so.1 ${D}${libdir}/libteec.so
    ln -s libteec.so.1.0 ${D}${libdir}/libteec.so.1

    # systemd service file
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_unitdir}/system/
    cp -rf ${S}/secureos/* ${D}${datadir}/tdk/secureos

}

FILES_${PN} += " ${libdir}/libteec.so.1.0 \
                ${libdir}/libteec.so.1"

FILES_${PN} += "${bindir}/tee-supplicant"

FILES_${PN}-dev += "${libdir}/libteec.so "
FILES_${PN}-securebl32 += " /usr/share/tdk/secureos/*"

INSANE_SKIP_${PN} = "ldflags dev-so"

FILES_${PN} += "${systemd_unitdir}/system/tee-supplicant.service"
SYSTEMD_SERVICE_${PN} = "tee-supplicant.service"
inherit systemd
