DESCRIPTION = "Meson Display"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRCREV ?= "${AUTOREV}"
#PV = "${SRCPV}"

DEPENDS += " libdrm json-c systemd"

#do_configure[noexec] = "1"
inherit autotools pkgconfig

#SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/meson_display;protocol=${AML_GIT_PROTOCOL};branch=master;"

#For common patches
#SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/meson_display')}"

S = "${WORKDIR}/git/meson-display"

#do_package_qa[noexec] = "1"

EXTRA_OEMAKE = "CROSS=${TARGET_PREFIX} TARGET_DIR=${STAGING_DIR_TARGET} STAGING_DIR=${D} DESTDIR=${D}"
FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN} += "/usr/lib/gstreamer-1.0/*"
FILES_${PN}-dev = "${includedir}/* "

INSANE_SKIP_${PN}-dev = "dev-so"
INSANE_SKIP_${PN} = "ldflags dev-so "

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
