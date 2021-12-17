DESCRIPTION = "aml secdmx library"
LICENSE = "AMLOGIC"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

SRC_URI = "git://${AML_GIT_ROOT}/libsecdmx_release;protocol=${AML_GIT_PROTOCOL};branch=master"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

S = "${WORKDIR}/git"

do_compile[noexec] = "1"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

DEPENDS += "optee-userspace liblog"

EXTRA_OEMAKE=" STAGING_DIR=${STAGING_DIR_TARGET} \
                 TARGET_DIR=${D} \
                 "
ARM_TARGET="arm.aapcs-linux.hard"
ARM_TARGET_aarch64 ="aarch64.lp64."
TA_TARGET="noarch"

do_install() {
    case ${MACHINE} in
        mesonsc2-*)
          CHIPFAMILY=S905X4
        ;;
        mesons4-*)
          CHIPFAMILY=S905Y4
        ;;
        *)
          CHIPFAMILY=
        ;;
    esac
    install -d ${D}${libdir}
    install -m 0755 -d ${D}${includedir}
    install ${S}/lib/libdmx_client_linux.so ${D}${libdir}/libdmx_client.so
    install -m 0644 ${S}/include/* ${D}/${includedir}

    install -d ${D}/lib/teetz
    install -m 0644 ${S}/ta/v3/dev/${CHIPFAMILY}/b472711b-3ada-4c37-8c2a-7c64d8af0223.ta ${D}/lib/teetz
}

INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"

FILES_${PN} += " ${bindir}/* ${libdir}/*.so ${libdir}/teetz/*.ta /lib/teetz/*.ta"
FILES_${PN}-dev = "${includedir}/* "

