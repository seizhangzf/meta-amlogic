SUMMARY = "aml volume control service"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/rdk/volumectl;protocol=${AML_GIT_PROTOCOL};branch=master"
SRC_URI += "file://volumeoverlay.service"
SRC_URI += "file://startvol.sh"


#For common patches
DEPENDS += " curl virtual/egl wayland freetype fontconfig cairo"
SRCREV = "${AUTOREV}"
PV = "${SRCPV}"
S = "${WORKDIR}/git"
EXTRA_OEMAKE="DESTDIR=${S}"

inherit systemd


do_compile() {
    cd ${S}
    oe_runmake
}
do_install() {
    install -d -m 0644 ${D}/usr/bin
    install -d -m 0644 ${D}/usr/lib
    install -d -m 0644 ${D}/${includedir}

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/volumeoverlay.service ${D}${systemd_unitdir}/system
    install -m 0755 ${WORKDIR}/startvol.sh ${D}/usr/bin

    install -D -m 0755 ${S}/volume-ctl-server ${D}/usr/bin
    install -D -m 0644 ${S}/libvolume-ctl-clnt.so ${D}/usr/lib
    install -D -m 0644 ${S}/volume-ctl-clnt.h ${D}/${includedir}
}
do_clean() {
    cd ${S}
    oe_runmake clean
}
FILES_${PN} = "${bindir}/* ${libdir}/*"
FILES_${PN}-dev = "${includedir}/* "
SYSTEMD_SERVICE_${PN} = "volumeoverlay.service"
