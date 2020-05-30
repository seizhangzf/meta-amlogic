SUMMARY = "aml audio utils"
LICENSE = "CLOSED"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/audio;protocol=${AML_GIT_PROTOCOL};branch=linux-buildroot"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/aml-patches/../multimedia/aml_audio_hal')}"

DEPENDS += "aml-amaudioutils expat tinyalsa libamavutils liblog"
RDEPENDS_${PN} += "liblog aml-amaudioutils"

inherit cmake pkgconfig

S="${WORKDIR}/git"
TARGET_CFLAGS += "-fPIC"

#PACKAGECONFIG = "dtv"

PACKAGECONFIG[dtv] = "-DUSE_DTV=ON,-DUSE_DTV=OFF,"

FILES_${PN} = "${libdir}/* ${bindir}/* ${sysconfdir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
