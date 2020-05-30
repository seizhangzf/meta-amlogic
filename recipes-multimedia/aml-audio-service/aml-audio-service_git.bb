SUMMARY = "aml audio service"
LICENSE = "CLOSED"

SRC_URI = "git://${AML_GIT_ROOT}/linux/multimedia/audio_server;protocol=${AML_GIT_PROTOCOL};branch=master"
SRC_URI_append = " file://audioserver.service"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/multimedia/hal_audio_service')}"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

do_configure[noexec] = "1"
inherit autotools pkgconfig systemd
S="${WORKDIR}/git"
DEPENDS += " grpc boost aml-amaudioutils protobuf-native liblog dolby-ms12"
RDEPENDS_${PN} += " aml-amaudioutils liblog"

export TARGET_DIR = "${D}"
export HOST_DIR = "${STAGING_DIR_NATIVE}/usr/"

do_compile() {
    oe_runmake  -C ${S} all
}
do_install() {
        install -d ${D}/usr/lib
        install -d ${D}/usr/bin
        install -d ${D}/usr/include
        install -d ${D}/usr/include/hardware
        install -d ${D}/usr/include/system
        install -m 755 -D ${S}/audio_server -t ${D}/usr/bin/
        install -m 755 -D ${S}/audio_client_test -t ${D}/usr/bin/
        install -m 755 -D ${S}/audio_client_test_ac3 ${D}/usr/bin/
        install -m 644 -D ${S}/libaudio_client.so -t ${D}/usr/lib/
        install -m 644 -D ${S}/include/audio_if_client.h -t ${D}/usr/include
        install -m 644 -D ${S}/include/audio_if.h -t ${D}/usr/include
        for f in ${S}/include/hardware/*.h; do \
            install -m 644 -D ${f} -t ${D}/usr/include/hardware; \
        done
        for f in ${S}/include/system/*.h; do \
            install -m 644 -D ${f} -t ${D}/usr/include/system; \
        done
    if [ "${@bb.utils.contains("DISTRO_FEATURES", "systemd", "yes", "no", d)}" = "yes"  ]; then
        install -D -m 0644 ${WORKDIR}/audioserver.service ${D}${systemd_unitdir}/system/audioserver.service
    fi

}

SYSTEMD_SERVICE_${PN} = "audioserver.service "
FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
