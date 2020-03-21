SUMMARY = "aml audio utils"
LICENSE = "CLOSED"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/audio;protocol=${AML_GIT_PROTOCOL};branch=linux-buildroot"
SRC_URI_append = " file://0001-audio-hal-Add-separate-hal_data_processing-with-ms12.patch"
SRC_URI_append = " file://0002-audio-hal-Fix-dap_tuning-run-time-parameter-settings.patch"
SRC_URI_append = " file://0003-audio-hal-Add-ms12-run-time-parameter-set_param-supp.patch"
SRC_URI_append = " file://0004-audio_hal-enable-direct-PCM-output.patch"
SRC_URI_append = " file://0005-remove-dtv.patch"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

DEPENDS += "aml-amaudioutils expat tinyalsa "
RDEPENDS_${PN} += "aml-amaudioutils"

inherit cmake pkgconfig
S="${WORKDIR}/git"

FILES_${PN} = "${libdir}/* ${bindir}/* ${sysconfdir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
