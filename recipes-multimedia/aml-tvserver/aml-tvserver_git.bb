SUMMARY = "aml tvserver"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/tvserver.git;protocol=${AML_GIT_PROTOCOL};branch=pure-linux-amlogic"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"
SRC_URI +="file://tvserver.service"

DEPENDS = " libbinder sqlite3 aml-audio-service"
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
        #install -D -m 0644 ${WORKDIR}/hdcprx.service ${D}${systemd_unitdir}/system/hdcprx.service
    fi
    #install ${WORKDIR}/hdcp_rx22 ${D}${bindir}

}
SYSTEMD_SERVICE_${PN} = "tvserver.service "
#SYSTEMD_SERVICE_${PN} = "tvserver.service hdcprx.service"

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
