SUMMARY = "aml audio utils"
LICENSE = "CLOSED"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/audio;protocol=${AML_GIT_PROTOCOL};branch=linux-buildroot"
SRC_URI_append = " file://0001-remove-dtv.patch"
SRC_URI_append = " file://0001-audio-hal-fix-audioserver-crash.patch"
SRC_URI_append = " file://0001-audio-hal-Set-default-HDMI-and-SPEAKER-sink-gain-for.patch"
SRC_URI_append = " file://0001-audio-hal-Add-separate-hal_data_processing-for-ms12-.patch"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

DEPENDS += "aml-amaudioutils expat tinyalsa libplayer aml-dvb"
RDEPENDS_${PN} += "aml-amaudioutils"

inherit cmake pkgconfig
S="${WORKDIR}/git"

FILES_${PN} = "${libdir}/* ${bindir}/* ${sysconfdir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"