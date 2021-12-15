DESCRIPTION = "widevine DRM"

LICENSE = "Proprietary"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

PROVIDES = "widevine"

SRC_URI = "git://${AML_GIT_ROOT_OP}/yocto/vendor/widevine;protocol=${AML_GIT_PROTOCOL};branch=amlogic-linux;destsuffix=git/widevine-bin"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/multimedia/libmediadrm/widevine-bin')}"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

S = "${WORKDIR}/git"
DEPENDS = "optee-userspace aml-secmem aml-mediahal-sdk"
RDEPENDS_${PN} = "libamavutils"
inherit autotools pkgconfig
ARM_TARGET="arm.aapcs-linux.hard"
TA_TARGET="noarch"

def get_widevine_version(datastore):
    if datastore.getVar("WIDEVINE_VERSION", True) == "16":
        return "prebuilt-v16"
    else:
        return "prebuilt-v15"

WIDEVINE_VER = "${@get_widevine_version(d)}"
do_install() {

    install -d -m 0755 ${D}/lib/teetz
    install -d -m 0755 ${D}/usr/lib
    install -d -m 0755 ${D}/usr/bin
    install -d -m 0755 ${D}/usr/include

    install -D -m 0755 ${S}/widevine-bin/${WIDEVINE_VER}/${TA_TARGET}/ta/${TDK_VERSION}/*.ta ${D}/lib/teetz/
    install -D -m 0644 ${S}/widevine-bin/${WIDEVINE_VER}/${TA_TARGET}/include/*.h ${D}${includedir}/

    install -D -m 0644 ${S}/widevine-bin/${WIDEVINE_VER}/${ARM_TARGET}/libwidevine_ce_cdm_shared.so ${D}/usr/lib
    install -D -m 0644 ${S}/widevine-bin/${WIDEVINE_VER}/${ARM_TARGET}/liboemcrypto.so ${D}/usr/lib
    install -D -m 0755 ${S}/widevine-bin/${WIDEVINE_VER}/${ARM_TARGET}/widevine_ce_cdm_unittest ${D}/usr/bin
}
FILES_${PN} += "${libdir}/*.so /lib/teetz/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf already-stripped"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf already-stripped"
