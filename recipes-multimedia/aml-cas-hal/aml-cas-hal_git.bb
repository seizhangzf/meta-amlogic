SUMMARY = "aml cas hal"
LICENSE = "AMLOGIC"
LIC_FILES_CHECKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('$(AML_PATCH_PATH)/multimedia/cas-hal')}"
DEPENDS += "aml-audio-service aml-mediahal-sdk aml-libdvr liblog"

#do_configure[noexec] = "1"
#inherit autotools pkgconfig
S="${WORKDIR}/git"
RDEPENDS_${PN} += "aml-audio-service aml-mediahal-sdk aml-libdvr liblog"
EXTRA_OEMAKE="STAGING_DIR=${STAGING_DIR_TARGET}\
	      TARGET_DIR=${D} \
	      "

ARM_TARGET = "arm.aapcs-linux.hard"
ARM_TARGET_aarch64 = "aarch64.lp64."

do_compile() {
    cd ${S}
    oe_runmake  all
}
do_install() {
    install -d ${D}${libdir}
    install -d ${D}${bindir}
    install -m 0755 -d ${D}${includedir}/libamcas
    install -m 0755 ${S}/libamcas.a ${D}${libdir}
    install -m 0644 ${S}/libamcas/include/* ${D}${includedir}/libamcas/
    install -m 0755 ${S}/cas_hal_test_bin ${D}${bindir}
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
