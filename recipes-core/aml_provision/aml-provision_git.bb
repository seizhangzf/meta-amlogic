SUMMARY = "aml key provision"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/provision;protocol=${AML_GIT_PROTOCOL};branch=projects/buildroot/tdk-v2.4"
SRC_URI_append = " file://aml_key_inject.service"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/provision')}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

S = "${WORKDIR}/git"
DEPENDS += "optee-userspace"
ARM_TARGET_aarch64 = "64"

inherit autotools pkgconfig systemd

do_install() {
    # install headers
    install -d -m 0644 ${D}/lib/teetz
    install -d -m 0644 ${D}/usr/lib
    install -d -m 0644 ${D}/usr/bin

    install -D -m 0755 ${S}/ca/bin${ARM_TARGET}/tee_provision ${D}/usr/bin/
    install -D -m 0755 ${S}/ca/bin${ARM_TARGET}/tee_key_inject ${D}/usr/bin/
    install -D -m 0755 ${S}/ca/lib${ARM_TARGET}/libprovision.so ${D}/usr/lib/
    install -D -m 0755 ${S}/ta/${TDK_VERSION}/*.ta ${D}/lib/teetz/

    install -D -m 0644 ${WORKDIR}/aml_key_inject.service ${D}${systemd_unitdir}/system/aml_key_inject.service
}

SYSTEMD_SERVICE_${PN} = "aml_key_inject.service "
FILES_${PN} += "/lib/teetz/* /usr/bin/*"
FILES_${PN} += "${libdir}/*"
FILES_${PN}-dev = " "
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf"
