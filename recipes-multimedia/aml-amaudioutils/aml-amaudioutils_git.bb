SUMMARY = "aml audio utils"
LICENSE = "CLOSED"

SRC_URI = "git://${AML_GIT_ROOT}/linux/multimedia/amaudioutils;protocol=${AML_GIT_PROTOCOL};branch=master"
SRC_URI_append = " file://0001-compile-fix.patch"
SRC_URI_append = " file://0001-add-liblog-support.patch"

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

do_compile() {
    cd ${B}
    oe_runmake  -C ${S} all
}
do_install() {
    cd ${B}
    oe_runmake  -C ${S} install
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
