SUMMARY = "amlogic dtvkit prebuilt"
LICENSE = "CLOSED"
DEPENDS = "aml-mp-sdk"
RDEPENDS_${PN} = "aml-mp-sdk"

SRC_URI = "git://${AML_GIT_ROOT}/DTVKit/releaseDTVKit;protocol=${AML_GIT_PROTOCOL};branch=linux-rdk"
#use head version, ?= conditonal operator can be control revision in external rdk-next.conf like configuration file
SRCREV ?= "${AUTOREV}"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"
do_install () {
	mkdir -p ${D}/usr/bin
	mkdir -p ${D}/usr/lib
	mkdir -p ${D}/usr/include/dtvkit/inc
	mkdir -p ${D}/usr/include/dtvkit/dvb/inc
	mkdir -p ${D}/usr/include/dtvkit/midware/stb/inc
	mkdir -p ${D}/usr/include/dtvkit/platform/inc
	mkdir -p ${D}/usr/include/dtvkit/hw/inc

	mkdir -p ${D}/etc/

    install -D -m 0644 ${S}/DVBCore/include/dvbcore/dvb/inc/*.h ${D}/usr/include/dtvkit/dvb/inc
    install -D -m 0644 ${S}/DVBCore/include/dvbcore/platform/inc/*.h ${D}/usr/include/dtvkit/platform/inc
    install -D -m 0644 ${S}/DVBCore/include/dvbcore/inc/*.h ${D}/usr/include/dtvkit/inc
    install -D -m 0644 ${S}/DVBCore/include/dvbcore/midware/stb/inc/*.h ${D}/usr/include/dtvkit/midware/stb/inc
    install -D -m 0644 ${S}/DVBCore/lib/libdvbcore.a  ${D}/usr/lib

    install -D -m 0644 ${S}/dtvkit-amlogic/include/dtvkit_platform/hw/inc/*.h ${D}/usr/include/dtvkit/hw/inc
    install -D -m 0644 ${S}/dtvkit-amlogic/lib/libdtvkit_platform.a ${D}/usr/lib


    install -D -m 0644 ${S}/android-rpcservice/dtvkit_demo ${D}/usr/bin
    install -D -m 0644 ${S}/android-rpcservice/config/config.xml ${D}/etc
}

FILES_${PN} += "/usr/bin/* /etc/*"
FILES_${PN}-dev += "/usr/include/* /usr/lib/*"

INSANE_SKIP_${PN} = "ldflags"