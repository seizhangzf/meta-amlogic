DESCRIPTION = "optee and tee-supplicant"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
include ../../recipes-shared/optee.inc

inherit native

do_configure[noexec] = "1"
do_compile[noexec] = "1"

COMPATIBLE_HOST = "(i.86|x86_64).*-linux"
INHIBIT_SYSROOT_STRIP = "1"

PR = "${INC_PR}.1"
do_install() {
    install -d ${D}${STAGING_DIR_NATIVE}/tdk/scripts
    cp -r ${S}/ta_export/scripts/. ${D}${STAGING_DIR_NATIVE}/tdk/scripts
    cp -r ${S}/ta_export/keys/. ${D}${STAGING_DIR_NATIVE}/tdk/keys
}

FILES_${PN} = "${STAGING_DIR_NATIVE}/tdk/scripts/* ${STAGING_DIR_NATIVE}/tdk/keys/*  "
SYSROOT_DIRS_NATIVE += "${STAGING_DIR_NATIVE}/tdk/scripts/ ${STAGING_DIR_NATIVE}/tdk/keys/  "


