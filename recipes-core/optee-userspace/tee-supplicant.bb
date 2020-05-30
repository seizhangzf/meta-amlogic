SUMMARY = "Startup script and systemd unit file for tee"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

FILESEXTRAPATHS_prepend := "${THISDIR}/optee-userspace:"
SRC_URI  = " file://tee-supplicant.service"

S = "${WORKDIR}"

do_install() {
    # systemd service file
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_unitdir}/system/
}

inherit allarch systemd

RDEPENDS_${PN} = "optee-userspace"
SYSTEMD_SERVICE_${PN} = "tee-supplicant.service"
FILES_${PN} += "${systemd_unitdir}/system/tee-supplicant.service"
