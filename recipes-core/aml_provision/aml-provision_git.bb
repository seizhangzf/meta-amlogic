SUMMARY = "aml key provision"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/provision;protocol=${AML_GIT_PROTOCOL};branch=projects/buildroot/tdk-v2.4"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/provision')}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

S = "${WORKDIR}/git"
DEPENDS += "optee-userspace"

inherit autotools pkgconfig

do_install() {
    # install headers
    install -d -m 0644 ${D}/lib/teetz
    install -d -m 0644 ${D}/usr/bin
    install -D -m 0755 ${S}/ca/bin/tee_provision ${D}/usr/bin/
    install -D -m 0755 ${S}/ta/${TDK_VERSION}/*.ta ${D}/lib/teetz/
}

FILES_${PN} += "/lib/teetz/* /usr/bin/*"
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf"
