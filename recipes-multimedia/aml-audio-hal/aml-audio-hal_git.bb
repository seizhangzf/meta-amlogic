SUMMARY = "aml audio utils"
LICENSE = "CLOSED"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/audio;protocol=${AML_GIT_PROTOCOL};branch=linux-buildroot"
SRC_URI_append = " file://crash_fix.diff"
SRC_URI_append = " file://0005-remove-dtv.patch"
SRC_URI_append = " file://0001-audio-hal-Merge-MS12-output-mask-config-1-1.patch"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

DEPENDS += "aml-amaudioutils expat tinyalsa "
RDEPENDS_${PN} += "aml-amaudioutils"

inherit cmake pkgconfig
S="${WORKDIR}/git"
TARGET_CFLAGS += "-fPIC"

FILES_${PN} = "${libdir}/* ${bindir}/* ${sysconfdir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
