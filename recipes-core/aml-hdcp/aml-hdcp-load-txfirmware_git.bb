SUMMARY = "aml hdcp firmware loading service"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-amlogic/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "file://firmware.le \
           file://hdcp_tx22 \
           file://load_hdcp2.2_firmware.service \
           "

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

#SRCREV ?= "${AUTOREV}"
#PV = "${SRCPV}"

S = "${WORKDIR}"

SYSTEMD_AUTO_ENABLE = "disable"

inherit systemd

do_install() {
    # install headers
    install -d -m 0644 ${D}/lib/firmware/hdcp/
    install -d -m 0644 ${D}/usr/bin
    install -d ${D}/${systemd_unitdir}/system
    install -D -m 0755 ${S}/hdcp_tx22 ${D}/usr/bin/
    install -D -m 0644 ${S}/firmware.le ${D}/lib/firmware/hdcp/
    if [ "${@bb.utils.contains("DISTRO_FEATURES", "systemd", "yes", "no", d)}" = "yes"  ]; then
        install -D -m 0644 ${S}/load_hdcp2.2_firmware.service ${D}${systemd_unitdir}/system/load_hdcp2.2_firmware.service
    fi
}

SYSTEMD_SERVICE_${PN} = "load_hdcp2.2_firmware.service "
FILES_${PN} += "/lib/firmware/hdcp/* /usr/bin/*"
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf"
