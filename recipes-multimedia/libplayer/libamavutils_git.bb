SUMMARY = "aml utils"
LICENSE = "LGPL-2.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2f61b7eacf1021ca36600c8932d215b9"

DEPENDS = "bzip2 virtual/gettext libxml2"
PROVIDES = "libamavutils"

SRC_URI = "git://${AML_GIT_ROOT}/platform/packages/amlogic/LibPlayer.git;protocol=${AML_GIT_PROTOCOL};branch=buildroot-libplayer"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${THISDIR}/amlogic')}"

SRCREV ?="${AUTOREV}"
PV = "git${SRCPV}"

S = "${WORKDIR}/git"
do_configure[noexec] = "1"
inherit pkgconfig

EXTRA_OEMAKE = "LIBPLAYER_STAGING_DIR=${STAGING_DIR_TARGET} CROSS=${TARGET_PREFIX} TARGET_DIR=${D} STAGING_DIR=${D} DESTDIR=${D}"
do_compile () {
    cd ${S}/amavutils
	oe_runmake -j1 ${EXTRA_OEMAKE} all
}

do_install () {

	install -d ${D}/usr/lib
	install -d ${D}${includedir}
    install -m 0644 ${S}/amavutils/include/*.h ${D}${includedir}
    install -m 0644 ${S}/amsubdec/amstream.h ${D}${includedir}
    install -m 0644 -D ${S}/amavutils/libamavutils.so ${D}/usr/lib/libamavutils.so
}

FILES_${PN} = " /usr/lib/* "
FILES_${PN}-dev = "/usr/include/*"

INSANE_SKIP_${PN} = "dev-elf dev-so"
INSANE_SKIP_${PN}-dev = "dev-elf dev-so"
