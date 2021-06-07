SUMMARY = "aml audio service"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/linux/multimedia/audio_server;protocol=${AML_GIT_PROTOCOL};branch=master"
SRC_URI_append = " file://audioserver.service"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/multimedia/hal_audio_service')}"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

PROVIDES = "${PN}-testapps"
PACKAGES =+ "\
    ${PN}-testapps \
    "

do_configure[noexec] = "1"
inherit autotools pkgconfig systemd
S="${WORKDIR}/git"
DEPENDS += " grpc grpc-native boost aml-amaudioutils protobuf-native liblog dolby-ms12"
RDEPENDS_${PN} += " aml-amaudioutils liblog"
RDEPENDS_${PN}-testapps += " ${PN} liblog"

export TARGET_DIR = "${D}"
export HOST_DIR = "${STAGING_DIR_NATIVE}/usr/"

#To remove --as-needed compile option which is causing issue with linking
#ASNEEDED = ""
#PARALLEL_MAKE = ""
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
        install -m 755 -D ${S}/halplay ${D}/usr/bin/
        install -m 755 -D ${S}/hal_param ${D}/usr/bin/
        install -m 755 -D ${S}/master_vol ${D}/usr/bin/
        install -m 755 -D ${S}/dap_setting ${D}/usr/bin/
        install -m 755 -D ${S}/digital_mode ${D}/usr/bin/
        install -m 755 -D ${S}/speaker_delay ${D}/usr/bin/
        install -m 755 -D ${S}/start_arc ${D}/usr/bin/
        install -m 755 -D ${S}/test_arc ${D}/usr/bin/
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
FILES_${PN} = "${libdir}/* ${bindir}/audio_server"
FILES_${PN}-testapps = "\
                        ${bindir}/audio_client_test \
                        ${bindir}/audio_client_test_ac3 \
                        ${bindir}/halplay \
                        ${bindir}/hal_param \
                        ${bindir}/master_vol \
                        ${bindir}/dap_setting \
                        ${bindir}/digital_mode \
                        ${bindir}/speaker_delay \
                        ${bindir}/start_arc \
                        ${bindir}/test_arc \
                        "
FILES_${PN}-testapps-dev = ""
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-testapps = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
