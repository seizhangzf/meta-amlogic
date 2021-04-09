SUMMARY = "aml audio utils"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-amlogic/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/audio;protocol=${AML_GIT_PROTOCOL};branch=linux-buildroot"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/aml-patches/../multimedia/aml_audio_hal')}"

DEPENDS += "aml-amaudioutils expat tinyalsa libamavutils liblog aml-avsync"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'amlogic-dvb', 'aml-dvb libamadec', '', d)}"
RDEPENDS_${PN} += "liblog aml-amaudioutils aml-avsync"
RDDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'amlogic-dvb', 'aml-dvbaudioutils', '', d)}"

inherit cmake pkgconfig

S="${WORKDIR}/git"
TARGET_CFLAGS += "-fPIC"

PACKAGECONFIG = "${@bb.utils.contains('DISTRO_FEATURES', 'amlogic-dvb', 'dtv', '', d)}"

PACKAGECONFIG[dtv] = "-DUSE_DTV=ON,-DUSE_DTV=OFF,"

PACKAGECONFIG += "eqdrc"

PACKAGECONFIG[eqdrc] = "-DUSE_EQ_DRC=ON,-DUSE_EQ_DRC=OFF,"

PACKAGECONFIG_append_sc2 += "sc2-dvb"
PACKAGECONFIG[sc2-dvb] = "-DUSE_SC2=ON,-DUSE_SC2=OFF,"

PACKAGECONFIG += "msync"
PACKAGECONFIG[msync] = "-DUSE_MSYNC=ON,-DUSE_MSYNC=OFF,"

FILES_${PN} = "${libdir}/* ${bindir}/* ${sysconfdir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
