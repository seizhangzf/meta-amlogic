SUMMARY = "aml utils"
LICENSE = "CLOSED"

SRC_URI = "git://${AML_GIT_ROOT}/platform/packages/amlogic/aml_libs_nf;protocol=${AML_GIT_PROTOCOL};branch=master"
SRC_URI_append = " file://0001-aml_libs_nf-fix-build-for-library-install-1-1.patch"
SRC_URI_append = " file://0001-compile-fix.patch"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

DEPENDS = " zlib alsa-lib android-tools"
do_configure[noexec] = "1"
inherit autotools pkgconfig
S="${WORKDIR}/git"


export AML_LIBS_NF_BUILD_DIR = "${B}"
export AML_LIBS_NF_STAGING_DIR = "${D}"
export AML_LIBS_NF_TARGET_DIR = "${D}"
export AML_LIBS_NF_BR2_ARCH = "${TARGET_ARCH}"
export TARGET_DIR = "${D}"

do_compile() {
    cd ${B}
    oe_runmake  -C ${S}/src all
}
do_install() {
    cd ${B}
#   install -d ${D}${includedir}

    oe_runmake  -C ${S}/src install
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"
