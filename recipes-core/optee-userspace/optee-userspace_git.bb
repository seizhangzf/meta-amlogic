DESCRIPTION = "optee "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
include optee.inc

PATCHTOOL= "git"

SRC_URI_append = " ${@get_patch_list('${THISDIR}/${PN}')}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${WORKDIR}/git"
PROVIDES = "optee-userspace-securebl32"
PACKAGES =+ "\
    ${PN}-securebl32 \
    "
PR = "${INC_PR}.1"

do_install() {
    mkdir -p ${D}${bindir}
    mkdir -p ${D}${datadir}/tdk/secureos
    install -m 0755 ${S}/ca_export_arm/bin/tee-supplicant ${D}${bindir}

    mkdir -p ${D}${libdir}
    install -m 0755 ${S}/ca_export_arm/lib/libteec.so ${D}${libdir}/libteec.so.1.0

    ln -s libteec.so.1 ${D}${libdir}/libteec.so
    ln -s libteec.so.1.0 ${D}${libdir}/libteec.so.1
    cp -rf ${S}/secureos/* ${D}${datadir}/tdk/secureos
}

FILES_${PN} += " ${libdir}/* "

FILES_${PN} += "${bindir}/tee-supplicant"

FILES_${PN}-dev = ""
FILES_${PN}-securebl32 += " /usr/share/tdk/secureos/*"

INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"

