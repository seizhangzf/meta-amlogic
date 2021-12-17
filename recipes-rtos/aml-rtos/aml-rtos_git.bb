SUMMARY = "aml rtos firmware loading service"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI_append = "file://load_dsp.service "
SRC_URI_append = "file://load_dsp.sh "
#SRC_URI_append = "${@bb.utils.contains("DISTRO_FEATURES", "build-rtos", "", \
#                "file://dspboota.bin \
#                 file://dspbootb.bin \
#                 file://m4a.bin \
#                 file://m4b.bin \", d)}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${WORKDIR}/git"

SYSTEMD_AUTO_ENABLE = "disable"

inherit systemd

do_install() {
    install -d ${D}/lib/firmware/

#    if ${@bb.utils.contains('DISTRO_FEATURES', 'build-rtos', 'true', 'false', d)}; then
#        unset LDFLAGS
#        unset CFLAGS
#        unset CPPFLAGS
#        unset CCACHE_COMPILERCHECK
#        if [ -n "${LM_LICENSE_FILE}" ]; then
#            export LM_LICENSE_FILE=${LM_LICENSE_FILE}
#        fi
#
#        cd ${S}
#        set -e; ./scripts/amlogic/mk.sh p1_hifi5a
#        install -m 0644 ${S}/out_dsp/dspboot.bin ${D}/lib/firmware/dspboota.bin
#
#        set -e; ./scripts/amlogic/mk.sh p1_hifi5b
#        install -m 0644 ${S}/out_dsp/dspboot.bin ${D}/lib/firmware/dspbootb.bin
#
#        set -e; ./scripts/amlogic/mk.sh azp1_m4a
#        install -m 0644 ${S}/out_m4/RTOSDemo.bin ${D}/lib/firmware/m4a.bin
#
#        set -e; ./scripts/amlogic/mk.sh azp1_m4b
#        install -m 0644 ${S}/out_m4/RTOSDemo.bin ${D}/lib/firmware/m4b.bin
#    else
#        install -m 0644 ${WORKDIR}/dspboota.bin ${D}/lib/firmware/
#        install -m 0644 ${WORKDIR}/dspbootb.bin ${D}/lib/firmware/
#
#        install -m 0644 ${WORKDIR}/m4a.bin ${D}/lib/firmware/
#        install -m 0644 ${WORKDIR}/m4b.bin ${D}/lib/firmware/
#    fi

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/load_dsp.sh ${D}/${bindir}

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/load_dsp.service ${D}${systemd_unitdir}/system
}

SYSTEMD_SERVICE_${PN} = "load_dsp.service"
FILES_${PN} += "/lib/firmware/* ${bindir}/* ${systemd_unitdir}/system/*"
