SUMMARY = "aml key provision"
LICENSE = "CLOSED"

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
    install -D -m 0755 ${S}/ta/e92a43ab-b4c8-4450-aa12b1516259613b.ta ${D}/lib/teetz/
}

FILES_${PN} += "/lib/teetz/* /usr/bin/*"
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf"
