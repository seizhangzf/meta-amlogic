SUMMARY = "aml software execution safeguard"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/sesg;protocol=${AML_GIT_PROTOCOL};branch=amlogic-dev"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/sesg')}"

do_configure[noexec] = "1"
do_populate_lic[noexec] = "1"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

S = "${WORKDIR}/git"
DEPENDS += "optee-userspace"
ARM_TARGET_aarch64 = "64"

inherit autotools pkgconfig systemd

do_compile() {
    cd ${S}/lib
    oe_runmake TEEC_EXPORT="${STAGING_DIR_TARGET}/usr" O=./
    cd ${S}/ca
    oe_runmake TEEC_EXPORT="${STAGING_DIR_TARGET}/usr" O=./

}

SOC = "TBD"
SOC_ah212 = "S905X4"
SOC_ap222 = "S905Y4"
SOC_aq222 = "S805X2"
#Please keep ah212 before ah232 because ah232 is a special case of ah212.
SOC_ah232 = "S905C2"

do_install() {
    # install headers
    install -d -m 0644 ${D}/lib/teetz
    install -d -m 0644 ${D}/usr/lib
    install -d -m 0644 ${D}/usr/bin

    install -D -m 0755 ${S}/ta/dev/${SOC}/*.ta ${D}/lib/teetz/
    install -D -m 0644 ${S}/lib/*.so ${D}/usr/lib/
    install -D -m 0755 ${S}/ca/tee_sesg_example ${D}/usr/bin/

}

FILES_${PN} += "/lib/teetz/* /usr/bin/*"
FILES_${PN} += "${libdir}/*"
FILES_${PN}-dev = " "
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"
INSANE_SKIP_${PN}-dev = "ldflags dev-so dev-elf"
