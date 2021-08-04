inherit systemd

SUMMARY = "ZRAM"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM=""

SRC_URI += "file://zram.service"
SRC_URI += "file://zram.sh"

ZRAM_FRACTION ?= "25"

do_install_append(){
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/zram.service ${D}/${systemd_unitdir}/system

    mkdir -p ${D}${bindir}
    install -m 0755 ${WORKDIR}/zram.sh ${D}/${bindir}
    sed -ri 's/(FRACTION=)[^a]*/\1${ZRAM_FRACTION}/' ${D}/${bindir}/zram.sh
}

FILES_${PN} += "${bindir}/*"
FILES_${PN} += "${systemd_unitdir}/system/*"

SYSTEMD_SERVICE_${PN} += "zram.service"
