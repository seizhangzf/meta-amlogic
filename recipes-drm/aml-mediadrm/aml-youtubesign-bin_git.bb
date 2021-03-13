DESCRIPTION = "youtube sign bin"

LICENSE = "Proprietary"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

PROVIDES = "youtubesign-bin"

SRCREV ?= "${AUTOREV}"
SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/prebuilt/libmediadrm;protocol=${AML_GIT_ROOT_PROTOCOL};branch=linux-buildroot"

S = "${WORKDIR}/git"

#inherit autotools pkgconfig
ARM_TARGET = "arm.aapcs-linux.hard"
ARM_TARGET_aarch64 = "aarch64.lp64."
TA_TARGET="noarch"

do_install_append() {
    install -d -m 0644 ${D}/usr/lib
    install -d -m 0644 ${D}/usr/include
    install -d -m 0644 ${D}/lib/teetz
    install -D -m 0755 ${S}/youtubesign-bin/prebuilt/${TA_TARGET}/ta/${TDK_VERSION}/*.ta ${D}/lib/teetz/
    install -D -m 0644 ${S}/youtubesign-bin/prebuilt/${ARM_TARGET}/*.so ${D}//usr/lib/
    install -D -m 0644 ${S}/youtubesign-bin/prebuilt/noarch/include/* ${D}/usr/include/
}

FILES_${PN} += "${libdir}/*.so /lib/teetz/* ${includedir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf already-stripped"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf already-stripped"
