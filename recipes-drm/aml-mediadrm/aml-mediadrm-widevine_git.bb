DESCRIPTION = "widevine DRM"

LICENSE = "Proprietary"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

PROVIDES = "widevine"


SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/prebuilt/libmediadrm;protocol=${AML_GIT_PROTOCOL};branch=linux-buildroot"
SRC_URI_append = " file://0001-add-headers.patch "
SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

S = "${WORKDIR}/git"
DEPENDS += "optee-userspace"

inherit autotools pkgconfig
ARM_TARGET="arm.aapcs-linux.hard"
TA_TARGET="noarch"
WIDEVINE_VER="prebuilt-v15"
do_install() {

    install -d -m 0644 ${D}/lib/teetz
    install -d -m 0644 ${D}/usr/lib
    install -d -m 0644 ${D}/usr/bin
    install -d -m 0644 ${D}/usr/include

    install -D -m 0755 ${S}/libsecmem-bin/prebuilt/${TA_TARGET}/ta/2c1a33c0-44cc-11e5-bc3b0002a5d5c51b.ta ${D}/lib/teetz/
    install -D -m 0644 ${S}/libsecmem-bin/prebuilt/${TA_TARGET}/include/*.h ${D}/usr/include
    install -D -m 0755 ${S}/libsecmem-bin/prebuilt/${ARM_TARGET}/secmem_test ${D}/usr/bin
    install -D -m 0644 ${S}/libsecmem-bin/prebuilt/${ARM_TARGET}/libsecmem.so ${D}/usr/lib

    install -D -m 0755 ${S}/widevine-bin/${WIDEVINE_VER}/${TA_TARGET}/ta/e043cde0-61d0-11e5-9c260002a5d5c51b.ta ${D}/lib/teetz/
    install -D -m 0644 ${S}/widevine-bin/${WIDEVINE_VER}/${TA_TARGET}/include/*.h ${D}${includedir}/

    install -D -m 0644 ${S}/widevine-bin/${WIDEVINE_VER}/${ARM_TARGET}/libwidevine_ce_cdm_shared.so ${D}/usr/lib
    install -D -m 0644 ${S}/widevine-bin/${WIDEVINE_VER}/${ARM_TARGET}/liboemcrypto.so ${D}/usr/lib
    install -D -m 0755 ${S}/widevine-bin/${WIDEVINE_VER}/${ARM_TARGET}/widevine_ce_cdm_unittest ${D}/usr/bin
}
FILES_${PN} += "${libdir}/*.so /lib/teetz/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf already-stripped"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf already-stripped"
