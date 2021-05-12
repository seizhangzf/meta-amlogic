SUMMARY = "aml hdcp service"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/hdcp;protocol=${AML_GIT_PROTOCOL};branch=projects/buildroot/tdk-v2.4"
SRC_URI_append = " file://aml_hdcp.service"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/hdcp')}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

S = "${WORKDIR}/git"
DEPENDS += "optee-userspace"

inherit autotools pkgconfig systemd
ARM_TARGET_aarch64 = "64"

do_install() {
    # install headers
    install -d -m 0644 ${D}/lib/teetz
    install -d -m 0644 ${D}/usr/bin
    install -D -m 0755 ${S}/ca/bin${ARM_TARGET}/tee_hdcp ${D}/usr/bin/
    install -D -m 0755 ${S}/ta/${TDK_VERSION}/*.ta ${D}/lib/teetz/
    if [ "${@bb.utils.contains("DISTRO_FEATURES", "systemd", "yes", "no", d)}" = "yes"  ]; then
        install -D -m 0644 ${WORKDIR}/aml_hdcp.service ${D}${systemd_unitdir}/system/aml_hdcp.service
    fi
}

SYSTEMD_SERVICE_${PN} = "aml_hdcp.service "
FILES_${PN} += "/lib/teetz/* /usr/bin/*"
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf"
