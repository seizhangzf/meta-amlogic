DESCRIPTION = "optee and tee-supplicant"
LICENSE = "Closed"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
include optee.inc

inherit native

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

COMPATIBLE_HOST = "(i.86|x86_64).*-linux"
INHIBIT_SYSROOT_STRIP = "1"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${STAGING_DIR_NATIVE}/tdk/scripts
    cp -r ${S}/ta_export/scripts/. ${D}${STAGING_DIR_NATIVE}/tdk/scripts
}

FILES_${PN} = "${STAGING_DIR_NATIVE}/tdk/scripts/*"
SYSROOT_DIRS_NATIVE += "${STAGING_DIR_NATIVE}/tdk/scripts/"


