SUMMARY = "amlogic optee video firmware"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

DEPENDS = "bzip2 libxml2"
RDEPENDS_${PN} = "libbz2 optee-userspace"
include aml-multimedia.inc

SRC_URI += "file://videoFirmwarePreload.service"
PR = "${INC_PR}.${TDK_VERSION}"

S = "${WORKDIR}/git/secfirmload/secloadbin"
TA_ARCH = "noarch"
TAR_ARCH = "arm.aapcs-linux.hard"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
	mkdir -p ${D}/usr/bin
	mkdir -p ${D}/usr/lib
	mkdir -p ${D}/lib/teetz

    install -m 0644 ${S}/${TA_ARCH}/ta/${TDK_VERSION}/*.ta ${D}/lib/teetz
    install -m 0644 ${S}/${TAR_ARCH}/libtee_preload_fw.so  ${D}/usr/lib
    install -m 0755 ${S}/${TAR_ARCH}/tee_preload_fw  ${D}/usr/bin

    # systemd service file
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/videoFirmwarePreload.service ${D}${systemd_unitdir}/system/
}

FILES_${PN} = "/lib/teetz/* /usr/lib/* /usr/bin/*"
FILES_${PN}-dev = ""
INSANE_SKIP_${PN} = "ldflags dev-elf already-stripped"

FILES_${PN} += "${systemd_unitdir}/system/videoFirmwarePreload.service"
SYSTEMD_SERVICE_${PN} = "videoFirmwarePreload.service"
inherit systemd
