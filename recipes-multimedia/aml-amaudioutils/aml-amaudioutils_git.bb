SUMMARY = "aml audio utils"
LICENSE = "CLOSED"

DEPENDS = "liblog"

SRC_URI = "git://${AML_GIT_ROOT}/linux/multimedia/amaudioutils;protocol=${AML_GIT_PROTOCOL};branch=master"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

do_configure[noexec] = "1"
inherit autotools pkgconfig
S="${WORKDIR}/git"

export AML_AMAUDIOUTILS_BUILD_DIR = "${B}"
export AML_AMAUDIOUTILS_STAGING_DIR = "${D}"
export AML_AMAUDIOUTILS_TARGET_DIR = "${D}"
export AML_AMAUDIOUTILS_BR2_ARCH = "${TARGET_ARCH}"
export TARGET_DIR = "${D}"

EXTRA_OEMAKE="STAGING_DIR=${D} \
                  TARGET_DIR=${D} \
                                "

do_compile() {
    cd ${B}
    oe_runmake -C ${S} all
}

do_install() {
    install -d ${D}/usr/lib
    install -d ${D}/usr/include/audio_utils
    install -d ${D}/usr/include/audio_utils/spdif

  	install -m 644 -D ${S}/libamaudioutils.so ${D}/usr/lib
    install -m 644 -D ${S}/libcutils.so ${D}/usr/lib
	install -m 644 ${S}/include/audio_utils/*.h ${D}/usr/include/audio_utils
	install -m 644 ${S}/include/audio_utils/spdif/*.h ${D}/usr/include/audio_utils/spdif
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
