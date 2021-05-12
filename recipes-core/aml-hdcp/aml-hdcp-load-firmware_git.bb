SUMMARY = "aml hdcp firmware loading service"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

FILESEXTRAPATHS_prepend_tm2 := "${THISDIR}/files/tm2:"

SRC_URI = "file://load_hdcp2.2_firmware.service"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

S = "${WORKDIR}"

SYSTEMD_AUTO_ENABLE = "enable"

inherit systemd

do_install() {
    install -d -m 0644 ${D}/lib/firmware/hdcp/
    install -d ${D}/${systemd_unitdir}/system
    touch ${D}/lib/firmware/hdcp/firmware.le
    if [ "${@bb.utils.contains("DISTRO_FEATURES", "systemd", "yes", "no", d)}" = "yes"  ]; then
        install -D -m 0644 ${S}/load_hdcp2.2_firmware.service ${D}${systemd_unitdir}/system/load_hdcp2.2_firmware.service
    fi
}

SYSTEMD_SERVICE_${PN} = "load_hdcp2.2_firmware.service "
FILES_${PN} += "/lib/firmware/hdcp/* "
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf"
