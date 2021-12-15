SUMMARY = "aml audio utils"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/audio;protocol=${AML_GIT_PROTOCOL};branch=r-linux-buildroot"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/aml-patches/../multimedia/aml_audio_hal')}"

DEPENDS += "aml-amaudioutils expat libamavutils liblog aml-avsync"
RDEPENDS_${PN} += "liblog aml-amaudioutils aml-avsync  libamavutils"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'amlogic-dvb', 'aml-dvb aml-dvbaudioutils', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'amlogic-dvb', 'aml-dvbaudioutils aml-dvb', '', d)}"

inherit cmake pkgconfig

S="${WORKDIR}/git"
TARGET_CFLAGS += "-fPIC"

PACKAGECONFIG = "${@bb.utils.contains('DISTRO_FEATURES', 'amlogic-dvb', 'dtv', '', d)}"

PACKAGECONFIG_append_sc2 += "dtv"
PACKAGECONFIG_append_s4 += "dtv"
PACKAGECONFIG_append_t5w += "dtv"
PACKAGECONFIG[dtv] = "-DUSE_DTV=ON,-DUSE_DTV=OFF,"

PACKAGECONFIG += "eqdrc"

PACKAGECONFIG[eqdrc] = "-DUSE_EQ_DRC=ON,-DUSE_EQ_DRC=OFF,"

PACKAGECONFIG_append_sc2 += "sc2-dvb"
PACKAGECONFIG[sc2-dvb] = "-DUSE_SC2=ON,-DUSE_SC2=OFF,"

PACKAGECONFIG += "msync"
PACKAGECONFIG[msync] = "-DUSE_MSYNC=ON,-DUSE_MSYNC=OFF,"

SRC_URI  += "\
  file://aml_audio_config.json \
  file://aml_audio_config.ah212.json \
  file://aml_audio_config.am301.json \
  file://aml_audio_config.at301.json \
  file://aml_audio_config.ap222.json \
  file://aml_audio_config.u212.json \
"

PROPERTY_SET_CONF = "aml_audio_config.json"
PROPERTY_SET_CONF_ah212 = "aml_audio_config.ah212.json"
PROPERTY_SET_CONF_u212 = "aml_audio_config.u212.json"
PROPERTY_SET_CONF_am301 = "aml_audio_config.am301.json"
PROPERTY_SET_CONF_at301 = "aml_audio_config.at301.json"
PROPERTY_SET_CONF_ap222 = "aml_audio_config.ap222.json"

do_install_append() {
    install -d ${D}/${sysconfdir}/halaudio
    install -m 0755 ${WORKDIR}/${PROPERTY_SET_CONF} ${D}/${sysconfdir}/halaudio/aml_audio_config.json
}
FILES_${PN} = "${libdir}/* ${bindir}/* ${sysconfdir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
